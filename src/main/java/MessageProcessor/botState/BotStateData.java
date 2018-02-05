package MessageProcessor.botState;

import java.io.Serializable;

public interface BotStateData extends Serializable {
    void setNextBotCommend(BotState nextBotCommend);

    BotState getNextCommend();

    void resetNextBotCommend();
}
