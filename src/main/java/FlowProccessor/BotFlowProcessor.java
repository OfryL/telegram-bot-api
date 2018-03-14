package FlowProccessor;

import FlowProccessor.cache.BotCacheManager;
import FlowProccessor.config.ProcessorConfig;
import FlowProccessor.controller.BotFlowController;
import FlowProccessor.factory.BotFlowFactory;
import FlowProccessor.locator.EntityLocator;
import FlowProccessor.model.BotBaseFlowEntity;
import FlowProccessor.model.impl.BotBaseModelEntity;
import FlowProccessor.model.impl.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.api.objects.Update;
import poc.POCBotController;

import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

/**
 * The type Flow processor.
 */
public class BotFlowProcessor implements IBotFlowProcessor {

    private BotFlowController controller;
    private BotCacheManager cacheManager;
    private static BotFlowProcessor instance;
    private ProcessorConfig config;

    private final Logger logger = LoggerFactory.getLogger(BotFlowProcessor.class);
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

        //Using client config
        config = clientController.getConfig();

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
            processFlow(userIdentifier, update, flow);
        }
        else {
            //No command / Active cached flow was found, Sending default
            doLog(userIdentifier, "No flow NOR command was found for processing, Sending default response");
            controller.sendDefaultResponse(update);
        }

    }

    private void processCommand(BotCommand command, String userIdentifier, Update update) {

        doLog(userIdentifier, "processCommand: Processing command {}", command.getIdentifier());

        command.doAction(update, controller);
        command.activate(update, controller);

        String flowId = command.getFlowEntityId();

        if (flowId != null) {

            startFlow(flowId, userIdentifier, update);
        }
    }

    private void processFlow(String userIdentifier, Update update, BotFlow flow) {

        doLog(userIdentifier, "processFlow: Processing flow {}",  flow.getId());

        //Matching up transition handler by prioritization
        BotTransitionHandler handler = findTransitionHandler(
                userIdentifier,
                update,
                flow
        );

        //The before transition
        boolean shouldPreformTransition = handler.beforeTransition(userIdentifier, update);
        if(shouldPreformTransition) {

            //If current handler has transition to perform
            if(handler.getTransition() != null) {

                doTransition(userIdentifier, update, handler);
            }
            else {

                doLog(userIdentifier, "processFlow: No transition handler was found, Completing flow {}",  flow.getId());
                //No handler was found for current flow, Completing
                completeFlow(flow, userIdentifier, update);
            }
        }
    }

    private boolean processStep(String userIdentifier, BotStep step, BotBaseModelEntity model, Update update) {

        doLog(userIdentifier, "processStep: Process start for step {} ",step.getId());

        //Validating step
        if (!step.isValid(update, model, controller)) {

            doLog(userIdentifier, "processStep: Step {} was found invalid", step.getId());
            step.invalidMessage(update, model, controller);
            return false;
        }

        boolean processCompleted = false;
        try {

            //Processing
            processCompleted = step.process(update, model, controller);

            //Completing
            step.complete(update, model, controller);

            doLog(userIdentifier, "processStep: Step {} was completed!", step.getId());
        }
        catch (Exception e) {

            doLog(userIdentifier, "processStep: Failed on Step {} processing", step.getId());
            e.printStackTrace();
            step.invalidMessage(update, model, controller);
        }

        return processCompleted;
    }

    private void completeFlow(BotFlow flow, String userIdentifier, Update update) {

        doLog(userIdentifier, "completeFlow: completing flow {}", flow.getId());
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

                doLog(userIdentifier, "completeFlow: Doing callback of {}, connect to {} completion", parentFlow.getId(), flow.getId());
                //Doing callback
                callback.doCallback(update, parentFlowModel, controller);
            }

            //Processing parent flow
            processFlow(userIdentifier, update, parentFlow);
        }

    }

    private void beginFlowEntity(BotBaseFlowEntity entity, String userIdentifier, Update update) {

        if (entity instanceof BotStep) {

            BotStep botStep = (BotStep) entity;

            BotBaseModelEntity model = cacheManager.getActiveFlowModel(userIdentifier);
            botStep.begin(update, model, controller);

            doLog(userIdentifier, "beginFlowEntity: {} Step begin ", entity.getId());

        }
        else {

            //Start a new flow
            startFlow(entity.getId(), userIdentifier, update);
        }
    }

    private void startFlow(String flowId, String userIdentifier, Update update) {

        doLog(userIdentifier, "startFlow: {} Flow begin ", flowId);
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

    private BotTransitionHandler findTransitionHandler(String userIdentifier, Update update, BotFlow flow){

        //Prioritizing the proper handler and transition
        //First, Parent interceptor transition
        BotTransition parentInterceptorTransition = getInterceptorTransition(userIdentifier, update, flow);
        if(parentInterceptorTransition != null) {

            doLog(userIdentifier, "findTransitionHandler: Found Parent interceptor transition");
            return new ParentInterceptorTransitionHandler(
                    false,
                    parentInterceptorTransition,
                    cacheManager.getParentFlow(userIdentifier),
                    flow
            );
        }

        //Second, Current flow back transition
        BotTransition stepBackTransition = getStepBackTransition(update, flow);
        if(stepBackTransition != null) {

            doLog(userIdentifier, "findTransitionHandler: Found Step back transition");
            return new StepBackTransitionHandler(
                    false,
                    stepBackTransition,
                    flow
            );
        }

        //If both was not matched, Return the Current step process transition
        //**Note, Actual transition (if matched) will be set by the before transition.
        doLog(userIdentifier, "findTransitionHandler: Found Process step transition");
        return new ProcessStepTransitionHandler(
                true,
                null,
                flow
        );
    }

    private BotTransition getInterceptorTransition(String userIdentifier, Update update, BotFlow flow){

        BotFlow parentFlow = cacheManager.getParentFlow(userIdentifier);

        if (parentFlow != null) {

            Set<BotTransition> possibleTransitions = EntityLocator.locateInterceptorTransition(parentFlow, flow);
            BotBaseModelEntity parentModel = parentFlow.getModel();

            return checkTransitions(update, possibleTransitions, parentModel, false);
        }

        return null;
    }

    private BotTransition getStepBackTransition(Update update, BotFlow flow) {

        BotBaseFlowEntity activeEntity = flow.getActiveEntity();

        if(activeEntity instanceof BotStep) {

            BotStep step = (BotStep) activeEntity;
            Set<BotTransition> possibleBackTransitions = EntityLocator.locateStepBackTransition(step, flow);

            return checkTransitions(update, possibleBackTransitions, flow.getModel(), false);
        }

        return null;

    }

    @SuppressWarnings("unchecked")
    private BotTransition checkTransitions(Update update, Set<BotTransition> transitions, BotBaseModelEntity model, boolean next) {

        for (BotTransition transition : transitions) {

            List<BotCondition> conditions = next ? transition.getNextConditions() : transition.getBackConditions();
            Predicate<BotCondition> predict = c -> c.checkCondition(update, model);
            boolean matched = conditions.stream().allMatch(predict);

            if(matched && conditions.size() > 0){
                return transition;
            }
        }

        return null;
    }

    private void doTransition(String userIdentifier, Update update, BotTransitionHandler handler) {

        //Extracting data from handler
        boolean next = handler.isDirectionNext();
        BotTransition transition = handler.getTransition();
        BotFlow flow = handler.getFlow();

        //Getting the proper entity by direction
        BotBaseFlowEntity flowEntity = next ? transition.getTo() : transition.getFrom();

        doLog(userIdentifier, "doTransition: Performing transition from {}, to {}", flow.getActiveEntity().getId(), flowEntity.getId());

        //Do transition
        flow.setActiveEntity(flowEntity);
        beginFlowEntity(flowEntity, userIdentifier, update);
    }

    // -- Transition handlers impl -- //
    private class ParentInterceptorTransitionHandler extends BotTransitionHandler {

        private BotFlow childFlow;

        ParentInterceptorTransitionHandler(boolean direction, BotTransition transition, BotFlow flow, BotFlow childFlow) {
            super(direction, transition, flow);
            this.childFlow = childFlow;
        }

        @Override
        public boolean beforeTransition(String userIdentifier, Update update) {

            //Exiting current flow
            childFlow.onExist(update, childFlow.getModel(), controller);
            cacheManager.clearFlow(userIdentifier);

            return true;
        }
    }

    private class StepBackTransitionHandler extends BotTransitionHandler {

        StepBackTransitionHandler(boolean direction, BotTransition transition, BotFlow flow) {
            super(direction, transition, flow);
        }

        @Override
        public boolean beforeTransition(String userIdentifier, Update update) {

            BotFlow flow = this.getFlow();
            BotStep activeStep = (BotStep) flow.getActiveEntity();
            activeStep.back(update, flow.getModel(), controller);

            return true;
        }
    }

    private class ProcessStepTransitionHandler extends BotTransitionHandler {

        ProcessStepTransitionHandler(boolean direction, BotTransition transition, BotFlow flow) {
            super(direction, transition, flow);
        }

        @Override
        public boolean beforeTransition(String userIdentifier, Update update) {

            BotFlow flow = this.getFlow();
            BotBaseFlowEntity activeEntity = flow.getActiveEntity();

            //If step, Processing
            if(activeEntity instanceof BotStep) {

                BotStep activeStep = (BotStep) activeEntity;
                boolean processCompleted = processStep(userIdentifier, activeStep, flow.getModel(), update);

                //If step failed, Freezing flow state on current step
                if(!processCompleted) return false;
            }

            //Else, fetching next transition
            Set<BotTransition> possibleNextTransitions = EntityLocator.locateTransitions(flow, activeEntity);
            BotTransition nextTransition = checkTransitions(update, possibleNextTransitions, flow.getModel(), true);

            this.setTransition(
                    nextTransition
            );

            return true;
        }
    }

    private void doLog(String userIdentifier, String message, Object... params) {

        if(config.isDebugMode()){
            logger.info(String.format("[%s] --> %s", userIdentifier, message), params);
        }
    }
}
