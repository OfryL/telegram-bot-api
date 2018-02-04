package MessageProcessor.botState.states;

import MessageProcessor.botState.BotState;
import MessageProcessor.botState.defaultStates.BaseBotState;
import org.telegram.telegrambots.api.objects.Contact;

abstract class BotBaseVerifyState extends BaseBotState implements BotState {

    Contact contact;

    BotBaseVerifyState() {
    }

    BotBaseVerifyState(Contact contact) {
        this.contact = contact;
    }
}
