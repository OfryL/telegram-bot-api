package FlowProccessor;

import FlowProccessor.controller.BotFlowController;
import org.telegram.telegrambots.api.objects.Update;

public interface IBotFlowProcessor {

     void init(BotFlowController controller);

     void processUpdate(Update update);

}
