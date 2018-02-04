package MessageProcessor.botState.states;

import MessageProcessor.botState.BotState;
import MessageProcessor.botState.defaultStates.BotStartState;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Contact;
import org.telegram.telegrambots.api.objects.Message;

public class BotVerifySecStageState extends BotBaseVerifyState implements BotState {


    private static final String HELLO_MSG = "Your id is = %s!";
    private String id;

    BotVerifySecStageState(Contact contact) {
        super(contact);
    }

    @Override
    public boolean isMessageValid(Message message) {
        String messageText = message.getText();
        if (this.verifyId(messageText)){
            this.id = messageText;
            return true;
        }
        return false;
    }

    private boolean verifyId(String text) {
        return true;
    }

    @Override
    public SendMessage getResponse(String chatId){
        return sendNewMessage(chatId, String.format(HELLO_MSG, this.id));
    }

    @Override
    public BotState getNextCommand(){
        return new BotStartState();
    }
}
