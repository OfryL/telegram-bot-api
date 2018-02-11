package FlowProccessor.model.impl;

import FlowProccessor.model.IBotStep;
import org.json.JSONObject;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardMarkup;

/**
 * The type Bot step.
 */
public abstract class BotStep extends BotBaseFlowEntity implements IBotStep {

    /**
     * Instantiates a new Bot step.
     *
     * @param id the id
     */
    public BotStep(String id) {
        super(id);
    }


    @Override
    public boolean process(Update update, JSONObject flowInput) {
        return true;
    }


    @Override
    public SendMessage complete(Update update, JSONObject flowInput) {

        return null;
    }

    public SendMessage sendNewMessage(String text) {
        return new SendMessage().setText(text);
    }

    public SendMessage sendNewMessage(String text, ReplyKeyboardMarkup replyKeyboardMarkup) {
        return new SendMessage().setText(text).setReplyMarkup(replyKeyboardMarkup);
    }

    public SendMessage sendNewMessage(String text, InlineKeyboardMarkup inlineKeyboardMarkup) {
        return new SendMessage().setText(text).setReplyMarkup(inlineKeyboardMarkup);
    }


}
