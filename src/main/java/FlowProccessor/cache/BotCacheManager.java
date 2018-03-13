package FlowProccessor.cache;

import FlowProccessor.factory.BotFlowFactory;
import FlowProccessor.locator.EntityLocator;
import FlowProccessor.model.impl.BotBaseModelEntity;
import FlowProccessor.model.impl.BotCommand;
import FlowProccessor.model.impl.BotFlow;

import java.util.*;

public class BotCacheManager implements ICacheManager {

    private Set<BotCommand> commands;
    private Set<BotFlowFactory> flowFactories;

    private ICacheHolder cache;

    public BotCacheManager(Set<BotFlowFactory> botFlowFactories, Set<BotCommand> commands) {

        this.flowFactories = botFlowFactories;
        this.commands = commands;
        this.cache = new BotDefaultCache();
    }

    public BotCacheManager(ICacheHolder cache, Set<BotFlowFactory> botFlowFactories, Set<BotCommand> commands) {

        this.flowFactories = botFlowFactories;
        this.commands = commands;
        this.cache = cache;
    }


    @Override
    public BotFlow getActiveFlow(String userIdentifier) {

        List<BotFlow> userCachedFlows = cache.get(userIdentifier);

        return userCachedFlows.size() > 0 ? userCachedFlows.get(userCachedFlows.size() - 1) : null;
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

        List<BotFlow> currentFlows = cache.get(userIdentifier);

        currentFlows.add(flow);

        cache.put(
                userIdentifier,
                currentFlows
        );
    }

    @Override
    public void clearFlow(String userIdentifier) {

        List<BotFlow> currentFlows = cache.get(userIdentifier);
        if (currentFlows.size() > 1) {

            currentFlows.remove(currentFlows.size() - 1);
            cache.put(userIdentifier, currentFlows);
        }
        else {
            cache.remove(userIdentifier);
        }

    }

    @Override
    public BotFlow getParentFlow(String userIdentifier) {

        List<BotFlow> flows = cache.get(userIdentifier);
        int size = flows.size();

        return size > 1 ? flows.get(size - 2) : null;
    }

    @Override
    public BotBaseModelEntity getActiveFlowModel(String userIdentifier) {

        List<BotFlow> flows = cache.get(userIdentifier);

        return flows.size() > 0 ? flows.get(flows.size() - 1).getModel() : null;
    }


}
