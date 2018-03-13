package FlowProccessor.model;

import FlowProccessor.controller.BotFlowController;
import FlowProccessor.model.impl.BotBaseModelEntity;
import org.telegram.telegrambots.api.objects.Update;

public interface IBotFlowCallback {

    void doCallback(Update update, BotBaseModelEntity model, BotFlowController controller);
}
