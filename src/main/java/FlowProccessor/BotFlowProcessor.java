package FlowProccessor;

import FlowProccessor.cache.AbstractCacheManager;
import FlowProccessor.controller.IBotFlowController;
import FlowProccessor.factory.BotFlowFactory;
import FlowProccessor.locator.EntityLocator;
import FlowProccessor.model.impl.*;
import org.telegram.telegrambots.api.methods.BotApiMethod;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;

import java.io.Serializable;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

/**
 * The type Flow processor.
 */
public class BotFlowProcessor implements IBotFlowProcessor {

    private IBotFlowController controller;
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
    public void init(IBotFlowController clientController) {

        //Setting controller
        this.controller = clientController;

        //Initializing cache manager
        this.cacheManager = clientController.getCacheManager();
    }

    @Override
    public void processUpdate(Update update) {

        String userIdentifier = String.valueOf(controller.getUserIdentityNumber(update));

        BotFlow flow = cacheManager.getActiveFlow(userIdentifier);
        BotCommand command = EntityLocator.locateCommand(cacheManager.getCommands(), update);

        if (command != null) {
            processCommand(command, userIdentifier, update);
            return;
        }

        if (flow != null) {
            resumeFlow(flow, userIdentifier, update);
        }
        else {
            //No command / Active cached flow was found, Sending default
            controller.sendDefaultResponse(update);
        }

    }

    private void processCommand(BotCommand command, String userIdentifier, Update update) {

        command.doAction(update, this.controller);

        execIfNotNull(update, command.getMessage(update));

        String flowId = command.getFlowEntityId();

        if (flowId != null) {

            startFlow(flowId, userIdentifier, update);
        }
    }

    private void startFlow(String flowId, String userIdentifier, Update update) {

        //Creating flow with cached factory
        BotFlowFactory factory = cacheManager.getFactory(flowId);

        //Instantiating flow
        BotFlow flow = factory.createFlow();

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

        BotBaseFlowEntity activeEntity = flow.getActiveEntity();

        if (activeEntity instanceof BotStep) {

            BotStep activeStep = (BotStep) activeEntity;

            BotBaseModelEntity model = cacheManager.getActiveFlowModel(userIdentifier);

            boolean processCompleted = processStep(
                    activeStep,
                    model,
                    update
            );

            //If current execution succeeded
            if (processCompleted) {

                BotApiMethod<? extends Serializable> completeMessage = activeStep.complete(update, model);
                execIfNotNull(update, completeMessage);

                //Search for transitions
                Set<BotTransition> possibleTransitions = EntityLocator.locateTransitions(flow, activeStep);

                BotTransition nextTransition = getNextTransition(possibleTransitions, model);

                doNextTransition(userIdentifier, update, flow, nextTransition);

            }
        }
        else {

            startFlow(activeEntity.getId(), userIdentifier, update);
        }
    }

    private boolean processStep(BotStep step, BotBaseModelEntity model, Update update) {

        //Validating step
        if (!step.isValid(update, model)) {

            execIfNotNull(update, step.invalidMessage());
            return false;
        }

        //Checking if step has loading message
        Message loadingMessage = execIfNotNull(update, step.loadingMessage());

        //Processing
        boolean processResult = step.process(update, model);

        if(loadingMessage != null) {

            //Deleting loading message
            execIfNotNull(update, step.deleteMessage(loadingMessage.getMessageId()));
        }

        //Checking process result
        if(!processResult){
            execIfNotNull(update, step.invalidMessage());
        }

        //Finally, Returning result
        return processResult;
    }

    private void beginFlowEntity(BotBaseFlowEntity entity, String userIdentifier, Update update) {

        if (entity instanceof BotStep) {

            BotApiMethod<? extends Serializable> beginMessage = ((BotStep) entity).begin(
                    cacheManager.getActiveFlowModel(userIdentifier)
            );

            execIfNotNull(update, beginMessage);
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

        SendMessage completeMessage = flow.complete(update, parentFlowModel);
        execIfNotNull(update, completeMessage);

        //Clearing current flow from cache
        cacheManager.clearFlow(userIdentifier);

        //Continue to parent flow itself if exists
        if (parentFlow != null) {

            //Searching for callbacks from current finished flow
            BotFlowCallback callback = EntityLocator.locateFlowCallback(parentFlow, flow);

            if(callback != null) {

                //Doing callback
                callback.doCallback(parentFlowModel);
            }


            //Searching for parent flow transition
            Set<BotTransition> possibleTransitions = EntityLocator.locateTransitions(parentFlow, flow);

            BotTransition nextTransition = getNextTransition(possibleTransitions, parentFlow.getModel());

            doNextTransition(userIdentifier, update, parentFlow, nextTransition);
        }

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

    @SuppressWarnings("unchecked")
    private BotTransition getNextTransition(Set<BotTransition> transitions, BotBaseModelEntity model) {

        BotTransition nextTransition = null;

        for (BotTransition transition : transitions) {

            List<BotCondition> conditions = transition.getConditions();

            Predicate<BotCondition> predict = c -> c.checkCondition(model);

            if (conditions.stream().allMatch(predict)) {

                nextTransition = transition;
                break;
            }
        }

        return nextTransition;
    }

    private <T extends Serializable, Method extends BotApiMethod<T>> T execIfNotNull(Update update, Method botApiMethod) {

        if (botApiMethod == null) return null;

        //Else, Executing
        return controller.executeOperation(update, botApiMethod);

    }
}
