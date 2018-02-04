package MessageProcessor.botState.defaultStates;

import MessageProcessor.botState.BotState;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;

public class BotAbortState extends BaseBotState implements BotState {

    public static final String ABORT_COMMAND = "/abort";
    private static final String ABORTING_MSG = "Ok ok, aborting process.";

    @Override
    public boolean isMessageValid(Message message) {
        return checkMessageForCommand(message, ABORT_COMMAND);
    }

    @Override
    public SendMessage getResponse(String chatId){
        return sendNewMessage(chatId, ABORTING_MSG);
    }

    @Override
    public BotState getNextCommand(){
        return new BotStartState();
    }
}
