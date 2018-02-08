package client.step;

import FlowProccessor.model.impl.BotStep;
import org.json.JSONObject;
import org.telegram.telegrambots.api.objects.Update;

public class FirstStep extends BotStep {

    public FirstStep(String id) {
        super(id);
    }

    @Override
    public String begin(String lastResult) {

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
    public void process(Update update, JSONObject flowInput) {

        //Do something by services
    }

    @Override
    public String getInvalidText() {
        return "Not a valid number!";
    }
}
