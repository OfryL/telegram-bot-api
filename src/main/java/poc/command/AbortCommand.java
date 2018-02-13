package poc.command;

import FlowProccessor.model.impl.BotCommand;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;

public class AbortCommand extends BotCommand {

    public AbortCommand(String flowId) {

        super("/abort", flowId);
    }

    @Override
    public SendMessage getMessage( Update update) {

        return new SendMessage().setText("Process aborted, You main start over /start!");
    }
}