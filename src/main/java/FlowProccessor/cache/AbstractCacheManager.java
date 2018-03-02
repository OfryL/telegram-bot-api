package FlowProccessor.cache;

import FlowProccessor.factory.BotFlowFactory;
import FlowProccessor.locator.EntityLocator;
import FlowProccessor.model.impl.BotBaseModelEntity;
import FlowProccessor.model.impl.BotCommand;
import FlowProccessor.model.impl.BotFlow;

import java.util.*;
import java.util.stream.Collectors;

public abstract class AbstractCacheManager implements ICacheManager{

    private Set<BotCommand> commands;
    private Set<BotFlowFactory> flowFactories;

    private Map<String, List<BotFlow>> userFlows;

    public AbstractCacheManager(Set<BotFlowFactory> botFlowFactories, Set<BotCommand> commands) {

        this.flowFactories = botFlowFactories;
        this.commands = commands;

        this.userFlows = new HashMap<>();
    }


    @Override
    public BotFlow getActiveFlow(String userIdentifier) {

        List<BotFlow> userCachedFlows =  this.userFlows.get(userIdentifier);

        return userCachedFlows != null ? userCachedFlows.get(userCachedFlows.size()-1) : null;
    }

    @Override
    public Set<BotCommand> getCommands() {
        return commands;
    }

    @Override
    public BotFlowFactory getFactory(String flowId) {

        return EntityLocator.locateFlowFactory(flowFactories, flowId);
    }

    @Override
    public void cacheFlow(String userIdentifier, BotFlow flow) {

        List<BotFlow> currentFlows = this.userFlows.get(userIdentifier);

        if(currentFlows == null) {

            currentFlows = new ArrayList<>();
        }

        currentFlows.add(flow);

        this.userFlows.put(
                userIdentifier,
                currentFlows
        );
    }

    @Override
    public void clearFlow(String userIdentifier) {

        List<BotFlow> currentFlows = this.userFlows.get(userIdentifier);

        if(currentFlows != null) {

            if(currentFlows.size() > 1) {

                currentFlows.remove(currentFlows.size()-1);

            }
            else{

                this.userFlows.remove(userIdentifier);
            }
        }
    }

    @Override
    public BotFlow getParentFlow(String userIdentifier) {

        List<BotFlow> flows = userFlows.get(userIdentifier);

        int size = flows.size();

        return size > 1 ? flows.get(size - 2) : null;
    }

    @Override
    public BotBaseModelEntity getActiveFlowModel(String userIdentifier) {

        List<BotFlow> flows = userFlows.get(userIdentifier);

        return flows != null ? flows.get(flows.size()-1).getModel() : null;
    }


}
