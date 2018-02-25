package FlowProccessor.model;

import FlowProccessor.controller.BotFlowController;
import FlowProccessor.controller.IBotFlowController;
import FlowProccessor.model.impl.BotBaseModelEntity;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;

public interface IBotCommand {

    String getIdentifier();

    String getFlowEntityId();

    void activate(Update update, BotFlowController controller);

    void doAction(Update update, BotFlowController controller);
}
