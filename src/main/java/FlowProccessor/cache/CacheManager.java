package FlowProccessor.cache;

import FlowProccessor.factory.BotFlowFactory;
import FlowProccessor.locator.EntityLocator;
import FlowProccessor.model.impl.BotBaseModelEntity;
import FlowProccessor.model.impl.BotCommand;
import FlowProccessor.model.impl.BotFlow;
import org.json.JSONObject;

import javax.swing.text.html.parser.Entity;
import java.util.*;
import java.util.stream.Collectors;

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

            currentFlows = currentFlows.stream()
                    .filter(flowWrapper -> !flowWrapper.getId().equals(flow.getId()))
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

    public BotFlow getParentFlow(String userIdentifier) {

        List<BotFlow> flows = userFlows.get(userIdentifier);

        int size = flows.size();

        return size > 1 ? flows.get(size - 2) : null;
    }

    public BotBaseModelEntity getActiveFlowModel(String userIdentifier) {

        List<BotFlow> flows = userFlows.get(userIdentifier);

        return flows.get(flows.size()-1).getModel();
    }

}
