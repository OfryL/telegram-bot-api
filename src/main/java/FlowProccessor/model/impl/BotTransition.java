package FlowProccessor.model.impl;

import FlowProccessor.model.BotBaseFlowEntity;
import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.List;

public class BotTransition<F extends BotBaseFlowEntity, T extends BotBaseFlowEntity> {

    private F from;
    private T to;

    private List<BotCondition> nextConditions;
    private List<BotCondition> backConditions;

    public BotTransition(F from, T to, List<BotCondition> nextConditions, List<BotCondition> backConditions) {
        this.setFrom(from);
        this.setTo(to);
        this.setNextConditions(nextConditions);
        this.setBackConditions(backConditions);
    }

    public BotTransition(F from, T to, BotCondition condition) {

         this(from, to, Lists.newArrayList(condition), new ArrayList<>());
    }

    public BotTransition(F from, T to, BotCondition condition, BotCondition backCondition) {

        this(from, to, Lists.newArrayList(condition), Lists.newArrayList(backCondition));
    }

    public F getFrom() {
        return from;
    }

    private void setFrom(F from) {
        this.from = from;
    }

    public T getTo() {
        return to;
    }

    private void setTo(T to) {
        this.to = to;
    }

    public List<BotCondition> getNextConditions() {
        return nextConditions;
    }

    private void setNextConditions(List<BotCondition> nextConditions) {
        this.nextConditions = nextConditions;
    }

    public List<BotCondition> getBackConditions() {
        return backConditions;
    }

    private void setBackConditions(List<BotCondition> backConditions) {
        this.backConditions = backConditions;
    }
}
