package MessageProcessor;

import MessageProcessor.botState.BotState;
import MessageProcessor.botState.defaultStates.BotAbortState;
import MessageProcessor.user.BotUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;

import static MessageProcessor.botState.defaultStates.BotAbortState.ABORT_COMMAND;


public class BotMessageProcessorImp implements BotMessageProcessor {
    private static final String LOG_COMMAND_IS_VALID = "%s is valid, processing new command";
    private static final String ERROR_PROCESS_REPLAY_MSG = "I can`t process this message right now. You can %s the process instead.";
    private static final String NEW_PROCESS_LOG = "new process";

    private final Logger logger = LoggerFactory.getLogger(BotMessageProcessorImp.class);

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
        logger.debug(NEW_PROCESS_LOG);
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
                logger.debug(String.format(LOG_COMMAND_IS_VALID, nextCommend.getClass().getSimpleName()));
                response = nextCommend.getResponse(getUserIdentityNumber());
                botUser.getBotStateData().setNextBotCommend(nextCommend.getNextCommand());
            }
        }
    }
}
