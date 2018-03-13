package FlowProccessor.model.impl;

import FlowProccessor.controller.BotFlowController;
import FlowProccessor.model.BotBaseFlowEntity;
import FlowProccessor.model.IBotFlow;
import org.telegram.telegrambots.api.objects.Update;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * The type Bot flow.
 */
public abstract class BotFlow extends BotBaseFlowEntity implements IBotFlow {

    private boolean done;

    private Set<BotTransition> transitions;

    private BotBaseFlowEntity activeEntity;

    private Set<BotFlowCallback> callbacks;

    /**
     * Instantiates a new Bot flow.
     *
     * @param id the id
     */
    public BotFlow(String id) {

        super(id);
        setTransitions(new HashSet<>());
        setCallbacks(new HashSet<>());
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
    public void complete(Update update, BotBaseModelEntity model, BotFlowController controller) {
    }

    public Set<BotFlowCallback> getCallbacks() {
        return callbacks;
    }

    public void setCallbacks(Set<BotFlowCallback> callbacks) {
        this.callbacks = callbacks;
    }

    @Override
    public void onExist(Update update, BotBaseModelEntity model, BotFlowController controller) {
    }

    public void addTransition(BotTransition transition) {
        this.transitions.add(transition);
    }

    public void addTransitions(List<BotTransition> transitions) {

        this.transitions.addAll(transitions);
    }

    public void addCallback(BotFlowCallback callback) {
        this.callbacks.add(callback);
    }
}
