package client.step;

import FlowProccessor.model.impl.BotStep;
import org.json.JSONObject;
import org.telegram.telegrambots.api.objects.Update;

public class FirstStep extends BotStep {

    public FirstStep(String id) {
        super(id);
    }

    @Override
    public String begin(JSONObject flowInput) {

        return "Hey, Please enter any number";
    }

    @Override
    public boolean isValid(Update update) {

        String input = update.getMessage().getText();

        boolean valid = true;
        try{
            Integer.parseInt(input);
        }
        catch (Exception e) {
            valid = false;
        }

        return valid;
    }

    @Override
    public boolean process(Update update, JSONObject flowInput) {

        flowInput.put(
                "userNumber",
                update.getMessage().getText()
        );

        return true;
    }

    @Override
    public String getInvalidText() {
        return "Not a valid number!";
    }
}
