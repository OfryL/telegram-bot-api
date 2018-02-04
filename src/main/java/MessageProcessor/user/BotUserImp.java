package MessageProcessor.user;

import MessageProcessor.botState.BotStateData;
import org.telegram.telegrambots.api.objects.User;

public class BotUserImp implements BotUser {
    private User userData;
    private BotStateData botCommendData;

    @Override
    public User getUserData() {
        return this.userData;
    }

    @Override
    public BotStateData getBotStateData() {
        return this.botCommendData;
    }

    public BotUserImp(User userData, BotStateData botCommendData) {
        this.userData = userData;
        this.botCommendData = botCommendData;
    }
}
