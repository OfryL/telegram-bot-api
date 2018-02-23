package poc.step;

import FlowProccessor.model.impl.BotBaseModelEntity;
import FlowProccessor.model.impl.BotStep;
import org.telegram.telegrambots.api.methods.BotApiMethod;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;

import java.io.Serializable;

public class BaseStep extends BotStep {

    public BaseStep(String id) {
        super(id);
    }

    @Override
    public BotApiMethod<? extends Serializable> begin(BotBaseModelEntity model) {
        return null;
    }

    @Override
    public boolean isValid(Update update, BotBaseModelEntity model) {
        return false;
    }

    @Override
    public SendMessage loadingMessage() {
        return null;
    }

    @Override
    public boolean process(Update update, BotBaseModelEntity model) {
        return false;
    }

    @Override
    public BotApiMethod<? extends Serializable> complete(Update update, BotBaseModelEntity model) {
        return null;
    }

    @Override
    public SendMessage invalidMessage() {
        return null;
    }
}
