package poc.flow.factory;

import FlowProccessor.controller.BotFlowController;
import FlowProccessor.factory.BotConditionFactory;
import FlowProccessor.factory.BotFlowFactory;
import FlowProccessor.model.impl.BotBaseModelEntity;
import FlowProccessor.model.impl.BotCondition;
import FlowProccessor.model.impl.BotTransition;
import org.telegram.telegrambots.api.objects.Update;
import poc.flow.MusicInfoFlow;
import poc.step.music.FavoriteMusicTypeStep;
import poc.step.music.FavoriteSongStep;

public class MusicInfoFlowFactory extends BotFlowFactory {

    public MusicInfoFlowFactory(String id) {
        super(id);
    }

    @Override
    public MusicInfoFlow createFlow(Update update, BotBaseModelEntity model, BotFlowController controller) {

        MusicInfoFlow flow = new MusicInfoFlow(this.getId());

        BotCondition always = BotConditionFactory.getInstance().always();
        BotCondition backToChooseType = BotConditionFactory.getInstance().callbackDataEqualsCondition("BACK_TO_CHOOSE_MUSIC");

        FavoriteSongStep favoriteSongStep = new FavoriteSongStep("favoriteSongStep");
        FavoriteMusicTypeStep favoriteMusicTypeStep = new FavoriteMusicTypeStep("favoriteMusicTypeStep");

        BotTransition favoriteTypeAndSongTransition = new BotTransition<>(favoriteMusicTypeStep, favoriteSongStep, always, backToChooseType);

        flow.addTransition(favoriteTypeAndSongTransition);
        flow.setActiveEntity(favoriteMusicTypeStep);

        return flow;
    }
}
