package FlowProccessor.model.impl;

import FlowProccessor.model.IBotFlowCallback;
import FlowProccessor.model.IBotStep;

/**
 * The type Bot step.
 */
public abstract class BotFlowCallback implements IBotFlowCallback {

    public BotFlowCallback(BotBaseFlowEntity from) {
        this.from = from;
    }

    private BotBaseFlowEntity from;

    public BotBaseFlowEntity getFrom() {
        return from;
    }

    public void setFrom(BotBaseFlowEntity from) {
        this.from = from;
    }
}
