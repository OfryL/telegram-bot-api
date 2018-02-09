package FlowProccessor;

import FlowProccessor.cache.BotFlowCacheWrapper;
import FlowProccessor.cache.CacheManager;
import FlowProccessor.controller.IBotFlowController;
import FlowProccessor.factory.BotFlowFactory;
import FlowProccessor.locator.EntityLocator;
import FlowProccessor.model.impl.*;
import org.json.JSONObject;
import org.telegram.telegrambots.api.objects.Update;

import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

/**
 * The type Flow processor.
 */
public class BotFlowProcessor implements IBotFlowProcessor {

    private IBotFlowController controller;
    private CacheManager cacheManager;
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
        this.cacheManager = new CacheManager(
                clientController.getFlowFactories(),
                clientController.getCommands()
        );
    }

    @Override
    public void processUpdate(Update update) {

        String userIdentifier = String.valueOf(controller.getUserIdentityNumber(update));

        BotFlow flow = cacheManager.getActiveFlow(userIdentifier);
        String message = update.getMessage().getText();
        BotCommand command = EntityLocator.locateCommand(cacheManager.getCommands(), message);

        if (command != null) {
            processCommand(command, userIdentifier, update);
            return;
        }

        if (flow == null) {
            //No command / Active cached flow was found, Sending default
            controller.getDefaultResponse(update);
        }
        else {
            resumeFlow(flow, userIdentifier, update);
        }

    }

    private void processCommand(BotCommand command, String userIdentifier, Update update) {

        if (command.getMessage() != null) {

            controller.sendMessage(update, command.getMessage());
        }

        String flowId = command.getFlowEntityId();

        if (flowId != null) {

            startFlow(flowId, userIdentifier, update);
        }
        else {

            controller.getDefaultResponse(update);
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

            JSONObject flowCachedInput = cacheManager.getFlowCachedInput(userIdentifier);

            boolean completed = processStep(
                    activeStep,
                    flowCachedInput,
                    update
            );

            //If current execution succeeded
            if (completed) {

                //Search for transitions
                Set<BotTransition> possibleTransitions = EntityLocator.locateTransitions(flow, activeStep);

                BotTransition nextTransition = getNextTransition(possibleTransitions, flowCachedInput);

                if (nextTransition != null) {

                    //Getting next step
                    BotBaseFlowEntity nextEntity = nextTransition.getTo();

                    flow.setActiveEntity(nextEntity);

                    beginFlowEntity(nextEntity, userIdentifier, update);
                }
                else {

                    completeFlow(flow, userIdentifier, update);
                }
            }
        }
        else {

            startFlow(activeEntity.getId(), userIdentifier, update);
        }
    }

    private boolean processStep(BotStep step, JSONObject flowInput, Update update) {

        if (!step.isValid(update)) {

            controller.sendMessage(update, step.getInvalidText());
            return false;
        }

        //Processing
        return step.process(update, flowInput);
    }

    private void beginFlowEntity(BotBaseFlowEntity entity, String userIdentifier, Update update) {

        if (entity instanceof BotStep) {

            String result = ((BotStep) entity).begin(
                    cacheManager.getFlowCachedInput(userIdentifier)
            );

            if (result != null) {

                controller.sendMessage(update, result);
            }
        }
        else {

            //Start a new flow
            startFlow(entity.getId(), userIdentifier, update);
        }
    }

    private void completeFlow(BotFlow flow, String userIdentifier, Update update) {

        flow.setDone(true);

        JSONObject flowCachedInput = cacheManager.getFlowCachedInput(userIdentifier);
        flow.getModel().setFlowInput(flowCachedInput);

        flow.complete();

        BotFlowCacheWrapper parentFlowWrapper = cacheManager.getParentFlow(userIdentifier);

        cacheManager.clearFlow(userIdentifier, flow);

        if (parentFlowWrapper != null) {

            BotFlow parentFlow = parentFlowWrapper.getFlow();

            //Searching for parent flow transition
            Set<BotTransition> possibleTransitions = EntityLocator.locateTransitions(parentFlow, flow);

            BotTransition nextTransition = getNextTransition(possibleTransitions, parentFlowWrapper.getFlowInput());

            if (nextTransition != null) {

                parentFlow.setActiveEntity(nextTransition.getTo());
                beginFlowEntity(nextTransition.getTo(), userIdentifier, update);
            }
            else {

                //Completing parent flow
                completeFlow(parentFlow, userIdentifier, update);
            }
        }
    }

    @SuppressWarnings("unchecked")
    private BotTransition getNextTransition(Set<BotTransition> transitions, JSONObject flowCachedInput) {

        BotTransition nextTransition = null;

        for(BotTransition transition : transitions) {

            List<BotCondition> conditions = transition.getConditions();

            Predicate<BotCondition> predict = c -> c.checkCondition(flowCachedInput);

            if(conditions.stream().allMatch(predict)){

                nextTransition = transition;
                break;
            }
        }

        return nextTransition;
    }
}
