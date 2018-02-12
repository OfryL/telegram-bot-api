package poc.step.music;

import FlowProccessor.model.impl.BotStep;
import org.json.JSONObject;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;

import java.nio.charset.Charset;
import java.util.Random;

public class FavoriteSongStep extends BotStep {

    public FavoriteSongStep(String id) {
        super(id);
    }

    @Override
    public SendMessage begin(JSONObject flowInput) {

        return sendNewMessage("Please write your favorite song");
    }

    @Override
    public boolean isValid(Update update) {

        //TODO Songs api ? Async ?
        return true;
    }

    @Override
    public boolean process(Update update, JSONObject flowInput) {

        flowInput.put(
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
