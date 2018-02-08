package FlowProccessor.model.impl;

import FlowProccessor.model.IBotStep;

/**
 * The type Bot step.
 */
public abstract class BotStep extends BotBaseFlowEntity implements IBotStep {

    private String invalidText;

    /**
     * Instantiates a new Bot step.
     *
     * @param id the id
     */
    public BotStep(String id) {
        super(id);
    }

    @Override
    public String getInvalidText() {
        return invalidText;
    }

    public void setInvalidText(String invalidText) {
        this.invalidText = invalidText;
    }
}
