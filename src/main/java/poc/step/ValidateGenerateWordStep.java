package poc.step;

import FlowProccessor.model.impl.BotBaseModelEntity;
import FlowProccessor.model.impl.BotStep;
import org.json.JSONObject;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;

import java.nio.charset.Charset;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

public class ValidateGenerateWordStep extends BaseStep {

    public ValidateGenerateWordStep(String id) {
        super(id);
    }

    private String generated;

    @Override
    public SendMessage begin(BotBaseModelEntity model) {

        byte[] array = new byte[7]; // length is bounded by 7
        new Random().nextBytes(array);
        generated = UUID.randomUUID().toString();
        return sendNewMessage("Hey, Please write down : " + generated);
    }

    @Override
    public boolean isValid(Update update) {

        String message = update.getMessage().getText();

        return message.equals(generated);
    }

    @Override
    public boolean process(Update update, BotBaseModelEntity model) {

        model.set("confirmed", true);


        return true;
    }

    @Override
    public SendMessage invalidMessage() {

        return this.sendNewMessage("Text sent does not match requested string!");
    }
}
