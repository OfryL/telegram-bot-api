package MessageProcessor.botState.states;

import MessageProcessor.botState.BotState;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Contact;
import org.telegram.telegrambots.api.objects.Message;

public class BotVerifyFirstStageState extends BotBaseVerifyState implements BotState {


    private static final String HELLO_MSG = "Hello %s!\nPlease write down your id...";

    @Override
    public boolean isMessageValid(Message message) {
//        if (BotUserImp.getVerifyStage == 1)

        Contact contact = message.getContact();
        if (contact != null){
            this.contact = contact;
            return true;
        }
        return false;
    }

    @Override
    public SendMessage getResponse(String chatId){
        return sendNewMessage(chatId, String.format(HELLO_MSG, this.contact.getFirstName()));
    }

    @Override
    public BotState getNextCommand(){
        return new BotVerifySecStageState(this.contact);
    }
}
