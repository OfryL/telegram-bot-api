package FlowProccessor.model;

import FlowProccessor.controller.BotFlowController;
import FlowProccessor.model.impl.BotBaseModelEntity;
import org.telegram.telegrambots.api.objects.Update;

public interface IBotStep {

    void begin(Update update, BotBaseModelEntity model, BotFlowController controller);

    boolean isValid(Update update, BotBaseModelEntity model, BotFlowController controller);

    boolean process(Update update, BotBaseModelEntity model, BotFlowController controller);

    void complete(Update update, BotBaseModelEntity model, BotFlowController controller);

    void back(Update update, BotBaseModelEntity model, BotFlowController controller);

    void invalidMessage(Update update, BotBaseModelEntity model, BotFlowController controller);
}
