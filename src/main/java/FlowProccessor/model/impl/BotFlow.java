package FlowProccessor.model.impl;

import FlowProccessor.model.IBotFlow;
import FlowProccessor.model.IBotFlowModel;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;

import java.util.List;
import java.util.Set;

/**
 * The type Bot flow.
 */
public abstract class BotFlow extends BotBaseFlowEntity implements IBotFlow {

    private boolean done;

    private Set<BotTransition> transitions;

    private BotBaseFlowEntity activeEntity;

    /**
     * Instantiates a new Bot flow.
     *
     * @param id the id
     */
    public BotFlow(String id) {
        super(id);
    }

    /**
     * Is done boolean.
     *
     * @return the boolean
     */
    public boolean isDone() {
        return done;
    }

    /**
     * Sets done.
     *
     * @param done the done
     */
    public void setDone(boolean done) {
        this.done = done;
    }

    public Set<BotTransition> getTransitions() {
        return transitions;
    }

    public void setTransitions(Set<BotTransition> transitions) {
        this.transitions = transitions;
    }

    @Override
    public BotBaseFlowEntity getActiveEntity() {

        return activeEntity;
    }

    public void setActiveEntity(BotBaseFlowEntity activeEntity) {
        this.activeEntity = activeEntity;
    }

    @Override
    public SendMessage complete(Update update) {
        return null;
    }
}
