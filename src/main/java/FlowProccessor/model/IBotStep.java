package FlowProccessor.model;

import FlowProccessor.controller.BotFlowController;
import FlowProccessor.model.impl.BotBaseModelEntity;
import org.json.JSONObject;
import org.telegram.telegrambots.api.methods.BotApiMethod;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;

import java.io.Serializable;

public interface IBotStep {

    void begin(Update update, BotBaseModelEntity model, BotFlowController controller);

    boolean isValid(Update update, BotBaseModelEntity model, BotFlowController controller);

    boolean process(Update update, BotBaseModelEntity model, BotFlowController controller);

    void complete(Update update, BotBaseModelEntity model, BotFlowController controller);

    void invalidMessage(Update update, BotBaseModelEntity model, BotFlowController controller);
}
