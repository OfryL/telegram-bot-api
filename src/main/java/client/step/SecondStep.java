package client.step;

import FlowProccessor.model.impl.BotStep;
import org.json.JSONObject;
import org.telegram.telegrambots.api.objects.Update;

public class SecondStep extends BotStep {

    public SecondStep(String id) {
        super(id);
    }

    @Override
    public String begin(JSONObject flowInput) {

        return String.format(
                "I see your number is %s , Now Please enter any string which length is greater then 5",
                flowInput.getString("userNumber")
        );
    }

    @Override
    public boolean isValid(Update update) {

        String input = update.getMessage().getText();

        return input.length() > 5;
    }

    @Override
    public boolean process(Update update, JSONObject flowInput) {

        flowInput.put(
                "userText",
                update.getMessage().getText()
        );

        return true;
    }

    @Override
    public String getInvalidText() {

        return "No enought chars";
    }
}
