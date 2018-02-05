package MessageProcessor.botState;

import MessageProcessor.botState.defaultStates.BotStartState;

public class BotStateDataImp implements BotStateData {
    private BotState nextCommend;

    @Override
    public void setNextBotCommend(BotState nextBotCommend) {
        this.nextCommend = nextBotCommend;
    }

    @Override
    public BotState getNextCommend() {
        return this.nextCommend;
    }

    @Override
    public void resetNextBotCommend() {
        this.nextCommend = null;
    }

    public BotStateDataImp() {
        this.nextCommend = new BotStartState();
    }
}
