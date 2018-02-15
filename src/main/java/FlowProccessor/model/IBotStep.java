package FlowProccessor.model;

import FlowProccessor.model.impl.BotBaseModelEntity;
import org.json.JSONObject;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;

public interface IBotStep {

    SendMessage begin(BotBaseModelEntity model);

    boolean isValid(Update update);

    boolean process(Update update, BotBaseModelEntity model);

    SendMessage complete(Update update, BotBaseModelEntity model);

    SendMessage invalidMessage();
}
