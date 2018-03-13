package FlowProccessor.model.impl;

import FlowProccessor.model.BotBaseFlowEntity;
import FlowProccessor.model.IBotStep;

/**
 * The type Bot step.
 */
public abstract class BotStep extends BotBaseFlowEntity implements IBotStep {

    /**
     * Instantiates a new Bot step.
     *
     * @param id the id
     */
    public BotStep(String id) {
        super(id);
    }
}
