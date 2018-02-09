package FlowProccessor.cache;

import FlowProccessor.model.impl.BotFlow;
import org.json.JSONObject;

public class BotFlowCacheWrapper {

    private BotFlow flow;

    public BotFlowCacheWrapper(BotFlow flow, JSONObject flowInput) {
        this.flow = flow;
        this.flowInput = flowInput;
    }

    private JSONObject flowInput;

    public BotFlow getFlow() {
        return flow;
    }

    public void setFlow(BotFlow flow) {
        this.flow = flow;
    }

    public JSONObject getFlowInput() {
        return flowInput;
    }

    public void setFlowInput(JSONObject flowInput) {
        this.flowInput = flowInput;
    }
}
