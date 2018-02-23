package poc.step.contact;

import FlowProccessor.model.impl.BotBaseModelEntity;
import FlowProccessor.model.impl.BotStep;
import org.json.JSONObject;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import poc.step.BaseStep;

public class AgeStep extends BaseStep {

    public AgeStep(String id) {
        super(id);
    }


    @Override
    public SendMessage begin(BotBaseModelEntity model) {

        return sendNewMessage("Please fill your age");
    }

    @Override
    public boolean isValid(Update update) {

        String input = update.getMessage().getText();

        try {

            Integer age = Integer.parseInt(input);

            return age > 20;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean process(Update update, BotBaseModelEntity model) {

        model.set(
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
