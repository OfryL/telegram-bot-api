package MessageProcessor;

import MessageProcessor.botState.BotState;
import MessageProcessor.botState.defaultStates.BotAbortState;
import MessageProcessor.user.BotUser;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;

import java.util.logging.Logger;

import static MessageProcessor.botState.defaultStates.BotAbortState.ABORT_COMMAND;


public class BotMessageProcessorImp implements BotMessageProcessor {
    private static final String LOG_COMMAND_IS_VALID = "%s is valid, processing new command";
    private static final String ERROR_PROCESS_REPLAY_MSG = "I can`t process this message right now. You can %s the process instead.";

    private static String getClassType(){
        return BotMessageProcessorImp.class.getName();
    }

    private Logger logger = Logger.getLogger(getClassType());

    private BotUser botUser;
    private Message message;
    private SendMessage response;

    private String getUserIdentityNumber() {
        return String.valueOf(message.getChatId());
    }

    @Override
    public SendMessage getResponse() {
        if (response != null) {
            return response;
        }
        return this.getDefaultResponse();
    }

    @Override
    public SendMessage getDefaultResponse() {
        return new SendMessage().setChatId(this.getUserIdentityNumber()).setText(String.format(ERROR_PROCESS_REPLAY_MSG, ABORT_COMMAND));
    }

    @Override
    public void init(BotUser botUser, Message message) {
        this.botUser = botUser;
        this.message = message;
    }

    @Override
    public void process() {
        processNextCommand(botUser.getBotStateData().getNextCommend());
        processNextCommand(new BotAbortState());
    }

    private void processNextCommand(BotState nextCommend) {
        if (nextCommend != null) {
            if (nextCommend.isMessageValid(message)){
                logger.info(String.format(LOG_COMMAND_IS_VALID, nextCommend.getClass().getSimpleName()));
                response = nextCommend.getResponse(getUserIdentityNumber());
                botUser.getBotStateData().setNextBotCommend(nextCommend.getNextCommand());
            }
        }
    }
}
