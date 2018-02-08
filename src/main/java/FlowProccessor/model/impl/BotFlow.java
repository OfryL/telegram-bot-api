package FlowProccessor.model.impl;

import FlowProccessor.model.IBotFlow;
import FlowProccessor.model.IBotFlowModel;

import java.util.List;
import java.util.Set;

/**
 * The type Bot flow.
 */
public abstract class BotFlow extends BotBaseFlowEntity implements IBotFlow {

    private boolean done;

    private Set<BotTransition> transitions;

    private IBotFlowModel flowModel;

    private List<BotBaseFlowEntity> flowEntities;

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

    public IBotFlowModel getFlowModel() {
        return flowModel;
    }

    public void setFlowModel(IBotFlowModel flowModel) {
        this.flowModel = flowModel;
    }

    @Override
    public BotBaseFlowEntity getActiveEntity() {

        if(activeEntity == null) {

            return flowEntities.get(0);
        }

        return activeEntity;
    }

    public void setActiveEntity(BotBaseFlowEntity activeEntity) {
        this.activeEntity = activeEntity;
    }

    @Override
    public List<BotBaseFlowEntity> getFlowEntities() {
        return flowEntities;
    }

    public void setFlowEntities(List<BotBaseFlowEntity> flowEntities) {
        this.flowEntities = flowEntities;
    }

    @Override
    public void complete() {

    }
}
