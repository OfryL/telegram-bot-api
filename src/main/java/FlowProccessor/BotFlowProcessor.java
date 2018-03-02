package FlowProccessor;

import FlowProccessor.cache.AbstractCacheManager;
import FlowProccessor.controller.BotFlowController;
import FlowProccessor.factory.BotFlowFactory;
import FlowProccessor.locator.EntityLocator;
import FlowProccessor.model.impl.*;
import org.telegram.telegrambots.api.objects.Update;

import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

/**
 * The type Flow processor.
 */
public class BotFlowProcessor implements IBotFlowProcessor {

    private BotFlowController controller;
    private AbstractCacheManager cacheManager;
    private static BotFlowProcessor instance;

    private BotFlowProcessor() {
    }

    /**
     * Get instance flow processor.
     *
     * @return the flow processor
     */
    public static synchronized BotFlowProcessor getInstance() {
        if (instance == null) {
            synchronized (BotFlowProcessor.class) {
                if (instance == null) {
                    instance = new BotFlowProcessor();
                }
            }
        }
        return instance;
    }

    @Override
    public void init(BotFlowController clientController) {

        //Setting controller
        this.controller = clientController;

        //Initializing cache manager
        this.cacheManager = clientController.getCacheManager();
    }

    @Override
    public void processUpdate(Update update) {

        String userIdentifier = String.valueOf(controller.getUserCacheIdentifier(update));

        BotFlow flow = cacheManager.getActiveFlow(userIdentifier);
        BotCommand command = EntityLocator.locateCommand(cacheManager.getCommands(), update);

        //Command handling is prioritized
        if (command != null) {
            processCommand(command, userIdentifier, update);
            return;
        }

        //Then, Flow handling if exists.
        if (flow != null) {
            resumeFlow(flow, userIdentifier, update);
        }
        else {
            //No command / Active cached flow was found, Sending default
            controller.sendDefaultResponse(update);
        }

    }

    private void processCommand(BotCommand command, String userIdentifier, Update update) {

        command.doAction(update, controller);

        command.activate(update, controller);

        String flowId = command.getFlowEntityId();

        if (flowId != null) {

            startFlow(flowId, userIdentifier, update);
        }
    }

    private void startFlow(String flowId, String userIdentifier, Update update) {

        //Getting parent flow if exists
        BotBaseModelEntity parentModel = cacheManager.getActiveFlowModel(userIdentifier);

        //Creating flow with cached factory
        BotFlowFactory factory = cacheManager.getFactory(flowId);

        //Instantiating flow
        BotFlow flow = factory.createFlow(update, parentModel, controller);

        //Caching
        cacheManager.cacheFlow(userIdentifier, flow);

        //Starting Active entity
        beginFlowEntity(
                flow.getActiveEntity(),
                userIdentifier,
                update
        );
    }

    private void resumeFlow(BotFlow flow, String userIdentifier, Update update) {

        //First giving parent (if exists) a chance to intercept message
        if (handleParentFlowChildInterceptors(flow, userIdentifier, update)) return;

        //If was not handled by parent flow interceptor, Continue to normal processing
        BotBaseFlowEntity activeEntity = flow.getActiveEntity();

        if (activeEntity instanceof BotStep) {

            BotStep activeStep = (BotStep) activeEntity;

            BotBaseModelEntity model = flow.getModel();

            boolean processCompleted = false;

            try {

                processCompleted = processStep(
                        activeStep,
                        model,
                        update
                );
            }
            catch (Exception e) {

                activeStep.invalidMessage(update, model, controller);
            }


            //If current execution succeeded
            if (processCompleted) {

                activeStep.complete(update, model, controller);

                //Search for transitions
                Set<BotTransition> possibleTransitions = EntityLocator.locateTransitions(flow, activeStep);
                doNextTransition(userIdentifier, update, flow, possibleTransitions);

            }
        }
        else {

            startFlow(activeEntity.getId(), userIdentifier, update);
        }
    }

    private boolean processStep(BotStep step, BotBaseModelEntity model, Update update) {

        //Validating step
        if (!step.isValid(update, model, controller)) {

            step.invalidMessage(update, model, controller);
            return false;
        }

        //Processing
        return step.process(update, model, controller);
    }

    private void beginFlowEntity(BotBaseFlowEntity entity, String userIdentifier, Update update) {

        if (entity instanceof BotStep) {

            BotStep botStep = (BotStep) entity;

            BotBaseModelEntity model = cacheManager.getActiveFlowModel(userIdentifier);
            botStep.begin(update, model, controller);
        }
        else {

            //Start a new flow
            startFlow(entity.getId(), userIdentifier, update);
        }
    }

    private void completeFlow(BotFlow flow, String userIdentifier, Update update) {

        //First Resolving current flow model
        flow.setDone(true);

        //We complete current flow with inject of the Parent flow model ( if exists
        BotFlow parentFlow = cacheManager.getParentFlow(userIdentifier);
        BotBaseModelEntity parentFlowModel = parentFlow != null ? parentFlow.getModel() : null;

        flow.complete(update, parentFlowModel, controller);

        //Clearing current flow from cache
        cacheManager.clearFlow(userIdentifier);

        //Continue to parent flow itself if exists
        if (parentFlow != null) {

            //Searching for callbacks from current finished flow
            BotFlowCallback callback = EntityLocator.locateFlowCallback(parentFlow, flow);
            if (callback != null) {

                //Doing callback
                callback.doCallback(update, parentFlowModel, controller);
            }

            //Searching for parent flow transition
            Set<BotTransition> possibleTransitions = EntityLocator.locateTransitions(parentFlow, flow);
            doNextTransition(userIdentifier, update, parentFlow, possibleTransitions);
        }

    }

    private boolean handleParentFlowChildInterceptors(BotFlow flow, String userIdentifier, Update update) {

        BotFlow parentFlow = cacheManager.getParentFlow(userIdentifier);
        boolean handledByInterceptor = false;

        if (parentFlow != null) {

            Set<BotTransition> possibleTransitions = EntityLocator.locateInterceptorTransition(parentFlow, flow);
            BotBaseModelEntity parentModel = parentFlow.getModel();

            for (BotTransition interceptorTransition : possibleTransitions) {

                if (checkTransition(update, interceptorTransition, parentModel)) {

                    //Deleting last message
                    flow.onExist(update, flow.getModel(), controller);

                    //Clearing current flow from cache
                    cacheManager.clearFlow(userIdentifier);

                    //Begin back destination
                    doNextTransition(userIdentifier, update, parentFlow, interceptorTransition);
                    handledByInterceptor = true;
                }
            }
        }

        return handledByInterceptor;
    }

    private void doNextTransition(String userIdentifier, Update update, BotFlow flow, Set<BotTransition> possibleTransitions) {

        BotTransition nextTransition = getNextTransition(update, possibleTransitions, flow.getModel());

        doNextTransition(userIdentifier, update, flow, nextTransition);
    }

    private void doNextTransition(String userIdentifier, Update update, BotFlow flow, BotTransition nextTransition) {

        if (nextTransition != null) {

            flow.setActiveEntity(nextTransition.getTo());
            beginFlowEntity(nextTransition.getTo(), userIdentifier, update);
        }
        else {

            //Completing parent flow
            completeFlow(flow, userIdentifier, update);
        }
    }

    private BotTransition getNextTransition(Update update, Set<BotTransition> transitions, BotBaseModelEntity model) {

        BotTransition nextTransition = null;

        for (BotTransition transition : transitions) {

            if (checkTransition(update, transition, model)) {
                nextTransition = transition;
                break;
            }
        }

        return nextTransition;
    }

    @SuppressWarnings("unchecked")
    private boolean checkTransition(Update update, BotTransition transition, BotBaseModelEntity model) {

        List<BotCondition> conditions = transition.getConditions();
        Predicate<BotCondition> predict = c -> c.checkCondition(update, model);
        return conditions.stream().allMatch(predict);
    }

}
