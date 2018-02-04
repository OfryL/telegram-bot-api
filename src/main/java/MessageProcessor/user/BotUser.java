package MessageProcessor.user;

import MessageProcessor.botState.BotStateData;
import org.telegram.telegrambots.api.interfaces.BotApiObject;
import org.telegram.telegrambots.api.objects.User;

public interface BotUser extends BotApiObject {

    User getUserData();

    BotStateData getBotStateData();
}
