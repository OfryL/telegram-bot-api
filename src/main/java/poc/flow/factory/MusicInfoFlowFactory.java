package poc.flow.factory;

import FlowProccessor.factory.BotConditionFactory;
import FlowProccessor.factory.BotFlowFactory;
import FlowProccessor.model.impl.BotCondition;
import FlowProccessor.model.impl.BotTransition;
import org.telegram.telegrambots.api.objects.Update;
import poc.flow.MusicInfoFlow;
import poc.flow.PersonalInfoFlow;
import poc.step.contact.AgeStep;
import poc.step.contact.ContactStep;
import poc.step.music.FavoriteMusicTypeStep;
import poc.step.music.FavoriteSongStep;

import java.util.HashSet;
import java.util.Set;

public class MusicInfoFlowFactory extends BotFlowFactory {

    public MusicInfoFlowFactory(String id) {
        super(id);
    }

    @Override
    public MusicInfoFlow createFlow(Update update) {

        MusicInfoFlow flow = new MusicInfoFlow(this.getId());

        BotCondition always = BotConditionFactory.getInstance().always();

        FavoriteSongStep favoriteSongStep = new FavoriteSongStep("favoriteSongStep");
        FavoriteMusicTypeStep favoriteMusicTypeStep = new FavoriteMusicTypeStep("favoriteMusicTypeStep");

        BotTransition toFavSong = new BotTransition<>(favoriteMusicTypeStep, favoriteSongStep, always);

        flow.addTransition(toFavSong);
        flow.setActiveEntity(favoriteMusicTypeStep);

        return flow;
    }
}
