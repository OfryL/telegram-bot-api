package FlowProccessor.model.impl;

import com.google.common.collect.Lists;

import java.util.List;

public class BotTransition<F extends BotBaseFlowEntity, T extends BotBaseFlowEntity> {

    private F from;
    private T to;

    private List<BotCondition> conditions;

    public BotTransition(F from, T to, List<BotCondition> conditions) {
        this.setFrom(from);
        this.setTo(to);
        this.setConditions(conditions);
    }

    public BotTransition(F from, T to, BotCondition condition) {

         this(from, to, Lists.newArrayList(condition));
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

    public List<BotCondition> getConditions() {
        return conditions;
    }

    private void setConditions(List<BotCondition> conditions) {
        this.conditions = conditions;
    }
}
