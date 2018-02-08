package FlowProccessor.cache;

import FlowProccessor.factory.BotFlowFactory;
import FlowProccessor.locator.EntityLocator;
import FlowProccessor.model.impl.BotCommand;
import FlowProccessor.model.impl.BotFlow;

import javax.swing.text.html.parser.Entity;
import java.util.*;

public class CacheManager {

    private Set<BotCommand> commands;
    private Set<BotFlowFactory> flowFactories;

    private Map<String, List<BotFlow>> userFlows;

    public CacheManager(Set<BotFlowFactory> botFlowFactories, Set<BotCommand> commands) {

        this.setFlowFactories(botFlowFactories);
        this.setCommands(commands);

        this.userFlows = new HashMap<>();
    }

    public Map<String, List<BotFlow>> getUserFlows() {
        return userFlows;
    }

    public BotFlow getActiveFlow(String userIdentifier) {

        List<BotFlow> userCachedFlows =  this.userFlows.get(userIdentifier);

        return userCachedFlows != null ? userCachedFlows.get(userCachedFlows.size()-1) : null;
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

    public void clearFlow(String userIdentifier, BotFlow flow) {

        List<BotFlow> currentFlows = this.userFlows.get(userIdentifier);

        if(currentFlows != null) {

            currentFlows.remove(flow);

            this.userFlows.put(
                    userIdentifier,
                    currentFlows
            );
        }

    }
}
