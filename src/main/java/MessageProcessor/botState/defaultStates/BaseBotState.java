package MessageProcessor.botState.defaultStates;

import MessageProcessor.botState.BotState;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardMarkup;

public abstract class BaseBotState implements BotState {

    boolean checkMessageForCommand(Message message, String command) {
        return message.getText() != null && message.getText().equalsIgnoreCase(command);
    }

    protected SendMessage sendNewMessage(String chatId, String msgTxt) {
        return new SendMessage().setChatId(chatId).setText(msgTxt);
    }

    SendMessage sendNewMessage(String chatId, String msgTxt, ReplyKeyboardMarkup replyKeyboardMarkup) {
        return new SendMessage().setChatId(chatId).setText(msgTxt).setReplyMarkup(replyKeyboardMarkup);
    }

    protected SendMessage sendNewMessage(String chatId, String msgTxt, InlineKeyboardMarkup inlineKeyboardMarkup) {
        return new SendMessage().setChatId(chatId).setText(msgTxt).setReplyMarkup(inlineKeyboardMarkup);
    }

}
