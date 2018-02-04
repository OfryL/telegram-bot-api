package MessageProcessor.botState;

import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;

import java.io.Serializable;

public interface BotState extends Serializable {
    boolean isMessageValid(Message message);

    SendMessage getResponse(String chatId);

    BotState getNextCommand();
}
