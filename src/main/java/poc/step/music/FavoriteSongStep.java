package poc.step.music;

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
    public SendMessage begin( BotBaseModelEntity model) {

        return sendNewMessage("Please write your favorite song");
    }

    @Override
    public boolean isValid(Update update) {

        //TODO Songs api ? Async ?
        return true;
    }

    @Override
    public boolean process(Update update, BotBaseModelEntity model) {

        model.set(
                "favoriteSong",
                update.getMessage().getText()
        );

        return true;
    }

    @Override
    public SendMessage invalidMessage() {
        return null;
    }
}
