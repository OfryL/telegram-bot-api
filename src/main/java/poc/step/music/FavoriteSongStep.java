package poc.step.music;

import FlowProccessor.controller.BotFlowController;
import FlowProccessor.model.impl.BotBaseModelEntity;
import FlowProccessor.model.impl.BotStep;
import org.json.JSONObject;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import poc.step.BaseStep;

import java.nio.charset.Charset;
import java.util.Random;

public class FavoriteSongStep extends BaseStep {

    public FavoriteSongStep(String id) {
        super(id);
    }

    @Override
    public void begin(Update update, BotBaseModelEntity model, BotFlowController controller) {

        controller.executeOperation(
                update,
                sendNewMessage("Please write your favorite song")
        );
    }

    @Override
    public boolean isValid(Update update, BotBaseModelEntity model, BotFlowController controller) {

        //TODO Songs api ? Async ?
        return true;
    }

    @Override
    public boolean process(Update update, BotBaseModelEntity model, BotFlowController controller) {

        model.set(
                "favoriteSong",
                update.getMessage().getText()
        );

        return true;
    }

}
