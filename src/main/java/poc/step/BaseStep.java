package poc.step;

import FlowProccessor.controller.BotFlowController;
import FlowProccessor.model.impl.BotBaseModelEntity;
import FlowProccessor.model.impl.BotStep;
import org.telegram.telegrambots.api.objects.Update;

public class BaseStep extends BotStep  {

    public BaseStep(String id) {
        super(id);
    }

    @Override
    public void begin(Update update, BotBaseModelEntity model, BotFlowController controller) {
    }

    @Override
    public boolean isValid(Update update, BotBaseModelEntity model, BotFlowController controller) {
        return false;
    }

    @Override
    public boolean process(Update update, BotBaseModelEntity model, BotFlowController controller) {
        return false;
    }

    @Override
    public void complete(Update update, BotBaseModelEntity model, BotFlowController controller) {

    }

    @Override
    public void back(Update update, BotBaseModelEntity model, BotFlowController controller) {

        if(update.hasCallbackQuery()) {

            controller.executeOperation(
                    update,
                    deleteFromCallback(update)
            );
        }
    }

    @Override
    public void invalidMessage(Update update, BotBaseModelEntity model, BotFlowController controller) {
    }
}
