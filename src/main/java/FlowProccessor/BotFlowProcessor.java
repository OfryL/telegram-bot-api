package FlowProccessor;

import FlowProccessor.cache.CacheManager;
import FlowProccessor.controller.IBotFlowController;
import FlowProccessor.factory.BotFlowFactory;
import FlowProccessor.locator.EntityLocator;
import FlowProccessor.model.impl.*;
import org.json.JSONObject;
import org.telegram.telegrambots.api.objects.Update;

import java.util.List;

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
        } else {
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
        } else {

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

            boolean completed = processStep(activeStep, update);

            //If current execution succeeded
            if (completed) {

                //Search for transitions
                //TODO Change this to return list of transitions
                BotTransition nextTransition = EntityLocator.locateTransition(flow, activeStep);

                //TODO Check possible transitions and set next

                if (nextTransition == null) {

                    completeFlow(flow, userIdentifier, update);
                } else {

                    //Getting next step
                    BotBaseFlowEntity nextEntity = nextTransition.getTo();

                    beginFlowEntity(nextEntity, userIdentifier, update);
                }

            }
        } else {

           startFlow(activeEntity.getId(), userIdentifier, update);

        }

    }

    private boolean processStep(BotStep step, Update update) {

        if (!step.isValid(update)) {

            controller.sendMessage(update, step.getInvalidText());
            return false;
        }

        //Processing
        //TODO - Should inject cached flow json input
        //TODO - Should return the result of the
        step.process(update, new JSONObject());

        return true;
    }

    private void beginFlowEntity(BotBaseFlowEntity entity, String userIdentifier, Update update) {

        if (entity instanceof BotStep) {

            //TODO - Model should be injected to this
            String result = ((BotStep) entity).begin("");

            if(result != null) {

                controller.sendMessage(update, result);
            }
        } else {

            //Start a new flow
            startFlow(entity.getId(), userIdentifier, update);
        }
    }

    private void completeFlow(BotFlow flow, String userIdentifier, Update update) {

        flow.setDone(true);

        //TODO Resolve flow model
        flow.getFlowModel().setFlowInput(new JSONObject());

        flow.complete();

        List<BotFlow> flows = cacheManager.getUserFlows().get(userIdentifier);

        if (flows.size() > 1) {

            //Meaning we have parent flow, getting parent
            BotFlow parentFlow = flows.get(flows.size() - 2);

            //Searching for parent flow transition
            BotTransition nextTransition = EntityLocator.locateTransition(parentFlow, flow);

            if (nextTransition != null) {

                beginFlowEntity(nextTransition.getTo(), userIdentifier, update);
            } else {

                //Completing parent flow
                parentFlow.complete();
            }

        } else {

            cacheManager.clearFlow(userIdentifier, flow);
        }

    }
}
