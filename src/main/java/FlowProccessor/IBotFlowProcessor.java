package FlowProccessor;

import FlowProccessor.controller.BotFlowController;
import FlowProccessor.controller.IBotFlowController;
import FlowProccessor.model.impl.BotBaseFlowEntity;
import FlowProccessor.model.impl.BotCommand;
import FlowProccessor.model.impl.BotFlow;
import FlowProccessor.model.impl.BotStep;
import org.telegram.telegrambots.api.objects.Update;

public interface IBotFlowProcessor {

     void init(BotFlowController controller);

     void processUpdate(Update update);

}
