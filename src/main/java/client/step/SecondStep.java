package client.step;

import FlowProccessor.model.impl.BotStep;
import org.json.JSONObject;
import org.telegram.telegrambots.api.objects.Update;

public class SecondStep extends BotStep {

    public SecondStep(String id) {
        super(id);
    }

    @Override
    public String begin(String lastResult) {

        return "Hey, Please enter any string which length is greater then 5";
    }

    @Override
    public boolean isValid(Update update) {

        String input = update.getMessage().getText();

        return input.length() > 5;
    }

    @Override
    public void process(Update update, JSONObject flowInput) {

        //Do something by services
    }

    @Override
    public String getInvalidText() {

        return "No enought chars";
    }
}
