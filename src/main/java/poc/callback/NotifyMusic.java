package poc.callback;

import FlowProccessor.controller.BotFlowController;
import FlowProccessor.model.BotBaseFlowEntity;
import FlowProccessor.model.impl.BotBaseModelEntity;
import FlowProccessor.model.impl.BotFlowCallback;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;

public class NotifyMusic extends BotFlowCallback {

    public NotifyMusic(BotBaseFlowEntity from) {
        super(from);
    }

    @Override
    public void doCallback(Update update, BotBaseModelEntity model, BotFlowController controller) {

        controller.executeOperation(
                update,
                new SendMessage().setText("Hook from music flow complete into parent")
        );

    }
}
