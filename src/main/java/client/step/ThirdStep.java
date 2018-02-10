package client.step;

import FlowProccessor.model.impl.BotStep;
import org.json.JSONObject;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;

public class ThirdStep extends BotStep {

    public ThirdStep(String id) {
        super(id);
    }

    @Override
    public SendMessage begin(JSONObject flowInput) {

        return this.sendNewMessage(
                "please write down the following: wassap"
        );
    }

    @Override
    public boolean isValid(Update update) {

        String input = update.getMessage().getText();

        return input.equals("wassap");
    }

    @Override
    public boolean process(Update update, JSONObject flowInput) {

        flowInput.put(
                "confirmedText",
                true
        );

        return true;
    }

    @Override
    public SendMessage invalidMessage() {
        return this.sendNewMessage("No what i asked!");
    }
}
