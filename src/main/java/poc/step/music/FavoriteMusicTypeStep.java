package poc.step.music;

import FlowProccessor.model.impl.BotStep;
import org.json.JSONObject;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;

import java.nio.charset.Charset;
import java.util.Random;

public class FavoriteMusicTypeStep extends BotStep {

    public FavoriteMusicTypeStep(String id) {
        super(id);
    }


    @Override
    public SendMessage begin(JSONObject flowInput) {

        //TODO Display buttons
        return sendNewMessage("Please select your favorite music type");
    }

    @Override
    public boolean isValid(Update update) {

        //TODO Cgheck options
        String message = update.getMessage().getText();

        return true;
    }

    @Override
    public boolean process(Update update, JSONObject flowInput) {

        flowInput.put(
                "favoriteType",
                "test"
        );

        return true;
    }

    @Override
    public SendMessage invalidMessage() {

        return this.sendNewMessage("You Must select one of the options");
    }
}
