package FlowProccessor.model.impl;

import FlowProccessor.controller.BotFlowController;
import FlowProccessor.model.IBotFlow;
import FlowProccessor.model.IBotFlowModel;
import org.json.JSONObject;
import org.telegram.telegrambots.api.methods.BotApiMethod;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;

import java.io.Serializable;
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

    private Set<BotTransition> childInterceptors;

    /**
     * Instantiates a new Bot flow.
     *
     * @param id the id
     */
    public BotFlow(String id) {

        super(id);
        setTransitions(new HashSet<>());
        setCallbacks(new HashSet<>());
        setChildInterceptors(new HashSet<>());
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

    @Override
    public Set<BotTransition> getChildInterceptors() {
        return childInterceptors;
    }

    public void setChildInterceptors(Set<BotTransition> childInterceptors) {
        this.childInterceptors = childInterceptors;
    }

    public void addTransition(BotTransition transition) {
        this.transitions.add(transition);
    }

    public void addCallback(BotFlowCallback callback) {
        this.callbacks.add(callback);
    }

    public void addChildInterceptor(BotTransition transition) {
        this.childInterceptors.add(transition);
    }
}
