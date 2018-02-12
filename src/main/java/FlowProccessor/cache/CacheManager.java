package FlowProccessor.cache;

import FlowProccessor.factory.BotFlowFactory;
import FlowProccessor.locator.EntityLocator;
import FlowProccessor.model.impl.BotCommand;
import FlowProccessor.model.impl.BotFlow;
import org.json.JSONObject;

import javax.swing.text.html.parser.Entity;
import java.util.*;
import java.util.stream.Collectors;

public class CacheManager {

    private Set<BotCommand> commands;
    private Set<BotFlowFactory> flowFactories;

    private Map<String, List<BotFlowCacheWrapper>> userFlows;

    public CacheManager(Set<BotFlowFactory> botFlowFactories, Set<BotCommand> commands) {

        this.setFlowFactories(botFlowFactories);
        this.setCommands(commands);

        this.userFlows = new HashMap<>();
    }

    public Map<String, List<BotFlowCacheWrapper>> getUserFlows() {
        return userFlows;
    }

    public BotFlow getActiveFlow(String userIdentifier) {

        List<BotFlowCacheWrapper> userCachedFlows =  this.userFlows.get(userIdentifier);

        return userCachedFlows != null ? userCachedFlows.get(userCachedFlows.size()-1).getFlow() : null;
    }

    public Set<BotCommand> getCommands() {
        return commands;
    }

    private void setCommands(Set<BotCommand> commands) {
        this.commands = commands;
    }

    private void setFlowFactories(Set<BotFlowFactory> flowFactories) {
        this.flowFactories = flowFactories;
    }

    public BotFlowFactory getFactory(String flowId) {

        return EntityLocator.locateFlowFactory(flowFactories, flowId);
    }

    public void cacheFlow(String userIdentifier, BotFlow flow) {

        List<BotFlowCacheWrapper> currentFlows = this.userFlows.get(userIdentifier);

        if(currentFlows == null) {

            currentFlows = new ArrayList<>();
        }

        currentFlows.add(new BotFlowCacheWrapper(flow, new JSONObject()));

        this.userFlows.put(
                userIdentifier,
                currentFlows
        );
    }

    public void clearFlow(String userIdentifier, BotFlow flow) {

        List<BotFlowCacheWrapper> currentFlows = this.userFlows.get(userIdentifier);

        if(currentFlows != null) {

            currentFlows = currentFlows.stream()
                    .filter(flowWrapper -> !flowWrapper.getFlow().getId().equals(flow.getId()))
                    .collect(Collectors.toList());

            if(currentFlows.size() > 0) {

                this.userFlows.put(
                        userIdentifier,
                        currentFlows
                );
            }
            else{

                this.userFlows.remove(userIdentifier);
            }

        }

    }

    public BotFlowCacheWrapper getParentFlow(String userIdentifier) {

        List<BotFlowCacheWrapper> flowWrappers = userFlows.get(userIdentifier);

        int size = flowWrappers.size();

        return size > 1 ? flowWrappers.get(size - 2) : new BotFlowCacheWrapper();
    }

    public JSONObject getFlowCachedInput(String userIdentifier) {

        List<BotFlowCacheWrapper> flowWrappers = userFlows.get(userIdentifier);

        return flowWrappers.get(flowWrappers.size()-1).getFlowInput();
    }

}
