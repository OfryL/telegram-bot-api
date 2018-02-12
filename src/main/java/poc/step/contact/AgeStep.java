package poc.step.contact;

import FlowProccessor.model.impl.BotStep;
import org.json.JSONObject;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;

public class AgeStep extends BotStep {

    public AgeStep(String id) {
        super(id);
    }


    @Override
    public SendMessage begin(JSONObject flowInput) {

        return sendNewMessage("Please fill your age");
    }

    @Override
    public boolean isValid(Update update) {

        String input = update.getMessage().getText();

        try{

            Integer age = Integer.parseInt(input);

            return age > 20;
        }
        catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean process(Update update, JSONObject flowInput) {

        flowInput.put(
                "age",
                Integer.parseInt(update.getMessage().getText())
        );

        return true;
    }

    @Override
    public SendMessage invalidMessage() {

        return this.sendNewMessage("Must be over 20 years old");
    }
}
