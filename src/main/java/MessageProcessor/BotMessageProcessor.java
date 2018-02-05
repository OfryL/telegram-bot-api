package MessageProcessor;

import MessageProcessor.user.BotUser;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;

public interface BotMessageProcessor {
    void init(BotUser tgsUser, Message message);
    void process();
    SendMessage getResponse();

    SendMessage getDefaultResponse();
}
