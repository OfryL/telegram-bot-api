package client.flow;

import FlowProccessor.factory.BotConditionFactory;
import FlowProccessor.factory.BotFlowFactory;
import FlowProccessor.model.impl.BotCondition;
import FlowProccessor.model.impl.BotFlow;
import FlowProccessor.model.impl.BotStep;
import FlowProccessor.model.impl.BotTransition;
import client.step.FirstStep;
import client.step.SecondStep;
import client.step.ThirdStep;
import com.google.common.collect.Lists;
import org.json.JSONObject;

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
        BotCondition numberIsGreaterThen5 = new BotCondition() {
            @Override
            public boolean checkCondition(JSONObject flowInput) {
                return flowInput.getInt("userNumber") > 5;
            }
        };

        FirstStep step1 = new FirstStep("first");
        SecondStep step2 = new SecondStep("second");
        ThirdStep step3 = new ThirdStep("Third");

        Set<BotTransition> transitions = new HashSet<>();
        BotTransition transition = new BotTransition<>(step1, step2, numberIsGreaterThen5);
        BotTransition transition1 = new BotTransition<>(step1, step3, BotConditionFactory.getInstance().oppositeOf(numberIsGreaterThen5));

        transitions.add(transition);
        transitions.add(transition1);

        flow.setTransitions(transitions);
        flow.setActiveEntity(step1);

        return flow;
    }
}
