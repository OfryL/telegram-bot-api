package poc.flow.factory;

import FlowProccessor.factory.BotConditionFactory;
import FlowProccessor.factory.BotFlowFactory;
import FlowProccessor.model.impl.BotCondition;
import FlowProccessor.model.impl.BotTransition;
import poc.flow.ContactFlow;
import poc.flow.MusicInfoFlow;
import poc.step.contact.AgeStep;
import poc.step.contact.ContactStep;

import java.util.HashSet;
import java.util.Set;

public class ContactInfoFlowFactory extends BotFlowFactory {

    public ContactInfoFlowFactory(String id) {
        super(id);
    }

    @Override
    public ContactFlow createFlow() {

        ContactFlow flow = new ContactFlow(this.getId());

        BotCondition always = BotConditionFactory.getInstance().always();

        AgeStep ageStep = new AgeStep("ageStep");
        ContactStep contactStep = new ContactStep("contactStep");

        Set<BotTransition> transitions = new HashSet<>();
        BotTransition toContact = new BotTransition<>(ageStep, contactStep, always);

        transitions.add(toContact);

        flow.setTransitions(transitions);
        flow.setActiveEntity(ageStep);

        return flow;
    }
}
