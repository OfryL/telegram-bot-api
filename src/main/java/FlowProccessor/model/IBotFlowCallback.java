package FlowProccessor.model;

import FlowProccessor.controller.BotFlowController;
import FlowProccessor.model.impl.BotBaseModelEntity;
import org.telegram.telegrambots.api.methods.BotApiMethod;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;

import java.io.Serializable;

public interface IBotFlowCallback {

    void doCallback(Update update, BotBaseModelEntity model, BotFlowController controller);
}
