package poc.command;

import FlowProccessor.model.impl.BotCommand;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;

public class StartCommand extends BotCommand {

    public StartCommand(String flowId) {

        super("/start", flowId);
    }

    @Override
    public SendMessage getMessage( Update update) {

        return new SendMessage().setText("Hello and welcome this shiet nigga");
    }
}
