package poc.command;

import FlowProccessor.controller.BotFlowController;
import FlowProccessor.model.impl.BotCommand;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;

public class StartCommand extends BotCommand {

    public StartCommand(String flowId) {

        super("/start", flowId);
    }

    @Override
    public void activate(Update update, BotFlowController controller) {

        controller.executeOperation(
                update,
                new SendMessage().setText("Hello and welcome this shiet nigga")
        );
    }
}
