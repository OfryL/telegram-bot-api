package FlowProccessor.factory;

import FlowProccessor.controller.BotFlowController;
import FlowProccessor.model.impl.BotBaseModelEntity;
import FlowProccessor.model.impl.BotFlow;
import org.telegram.telegrambots.api.objects.Update;

/**
 * The interface Flow factory.
 */
public interface IBotFlowFactory {

    BotFlow createFlow(Update update, BotBaseModelEntity model, BotFlowController controller);
}
