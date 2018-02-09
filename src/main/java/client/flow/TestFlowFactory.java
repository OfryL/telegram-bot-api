package client.flow;

import FlowProccessor.factory.BotConditionFactory;
import FlowProccessor.factory.BotFlowFactory;
import FlowProccessor.model.impl.BotCondition;
import FlowProccessor.model.impl.BotFlow;
import FlowProccessor.model.impl.BotStep;
import FlowProccessor.model.impl.BotTransition;
import client.step.FirstStep;
import client.step.SecondStep;
import com.google.common.collect.Lists;

import java.util.HashSet;
import java.util.Set;

public class TestFlowFactory extends BotFlowFactory {

    public TestFlowFactory(String id) {
        super(id);
    }

    @Override
    public TestFlow createFlow() {

        TestFlow flow = new TestFlow(this.getId());

        //Conditions
        BotCondition always = BotConditionFactory.getInstance().always();

        FirstStep step1 = new FirstStep("first");
        SecondStep step2 = new SecondStep("second");

        Set<BotTransition> transitions = new HashSet<>();
        BotTransition transition = new BotTransition<BotStep, BotStep>(step1, step2, Lists.newArrayList(always));

        transitions.add(transition);

        flow.setTransitions(transitions);
        flow.setActiveEntity(step1);

        return flow;
    }
}
