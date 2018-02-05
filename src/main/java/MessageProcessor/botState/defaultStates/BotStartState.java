package MessageProcessor.botState.defaultStates;

import MessageProcessor.botState.BotState;
import MessageProcessor.botState.states.BotVerifyFirstStageState;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardRow;
import java.util.ArrayList;
import java.util.List;

public class BotStartState extends BaseBotState implements BotState {
    private static final String START_COMMAND = "/start";

    @Override
    public boolean isMessageValid(Message message) {
        return this.checkMessageForCommand(message, START_COMMAND);
    }

    @Override
    public SendMessage getResponse(String chatId) {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(true);
        List<KeyboardRow> keyboard = new ArrayList<>();
        KeyboardRow row = new KeyboardRow();
        KeyboardButton keyboardButton = new KeyboardButton().setText("test").setRequestContact(true);
        row.add(keyboardButton);
        keyboard.add(row);
        replyKeyboardMarkup.setKeyboard(keyboard);
        return sendNewMessage(chatId, "test", replyKeyboardMarkup);
    }


    @Override
    public BotState getNextCommand() {
        return new BotVerifyFirstStageState();
    }
}
