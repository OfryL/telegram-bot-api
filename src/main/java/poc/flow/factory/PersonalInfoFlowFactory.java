package poc.flow.factory;

import FlowProccessor.factory.BotConditionFactory;
import FlowProccessor.factory.BotFlowFactory;
import FlowProccessor.model.impl.BotBaseModelEntity;
import FlowProccessor.model.impl.BotCondition;
import FlowProccessor.model.impl.BotFlowCallback;
import FlowProccessor.model.impl.BotTransition;
import org.json.JSONObject;
import org.telegram.telegrambots.api.objects.Update;
import poc.callback.NotifyMusic;
import poc.flow.ContactFlow;
import poc.flow.MusicInfoFlow;
import poc.flow.PersonalInfoFlow;
import poc.step.ChooseInfoTypeStep;
import poc.step.ValidateGenerateWordStep;

import java.util.HashSet;
import java.util.Set;

public class PersonalInfoFlowFactory extends BotFlowFactory {

    public PersonalInfoFlowFactory(String id) {
        super(id);
    }

    @Override
    public PersonalInfoFlow createFlow(Update update) {

        PersonalInfoFlow flow = new PersonalInfoFlow(this.getId());

        //Conditions
        BotCondition isMusic = BotConditionFactory.getInstance().equalsCondition(
                "music",
                "infoType"
        );

        BotCondition always = BotConditionFactory.getInstance().always();

        ChooseInfoTypeStep chooseInfoTypeStep= new ChooseInfoTypeStep("infoType");
        ValidateGenerateWordStep validateStep = new ValidateGenerateWordStep("validateStep");

        ContactFlow contactFlow = new ContactFlow("contactFlow");
        MusicInfoFlow musicInfoFlow = new MusicInfoFlow("musicFlow");

        BotTransition toMusic = new BotTransition<>(chooseInfoTypeStep, musicInfoFlow, isMusic);
        BotTransition toContact = new BotTransition<>(chooseInfoTypeStep, contactFlow, BotConditionFactory.getInstance().oppositeOf(isMusic));
        BotTransition fromContact = new BotTransition<>(contactFlow, validateStep, always);
        BotTransition fromMusic = new BotTransition<>(musicInfoFlow, validateStep, always);

        flow.addTransition(toMusic);
        flow.addTransition(toContact);
        flow.addTransition(fromContact);
        flow.addTransition(fromMusic);

        flow.addCallback(new NotifyMusic(musicInfoFlow));

        //Define back
        BotCondition backToChoose = BotConditionFactory.getInstance().callbackDataEqualsCondition("back");
        flow.setBackTransition(
                new BotTransition<>(chooseInfoTypeStep, backToChoose)
        );

        flow.setActiveEntity(chooseInfoTypeStep);

        return flow;
    }
}
