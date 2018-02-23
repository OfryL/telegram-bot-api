package FlowProccessor.model;

import FlowProccessor.model.impl.BotBaseModelEntity;
import org.json.JSONObject;
import org.telegram.telegrambots.api.methods.BotApiMethod;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;

import java.io.Serializable;

public interface IBotStep {

    BotApiMethod<? extends Serializable> begin(BotBaseModelEntity model);

    boolean isValid(Update update);

    BotApiMethod<? extends Serializable> beforeProcess(Update update, BotBaseModelEntity model);

    boolean process(Update update, BotBaseModelEntity model);

    BotApiMethod<? extends Serializable> complete(Update update, BotBaseModelEntity model);

    BotApiMethod<? extends Serializable> invalidMessage();
}
