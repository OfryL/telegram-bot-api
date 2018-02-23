package FlowProccessor.model.impl;

import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardRemove;

/**
 * The type Bot base flow entity.
 */
public abstract class BotBaseFlowEntity {

    private String id;

    /**
     * Instantiates a new Bot base flow entity.
     *
     * @param id the id
     */
    public BotBaseFlowEntity(String id) {
        this.id = id;
    }

    /**
     * Gets id.
     *
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * Sets id.
     *
     * @param id the id
     */
    public void setId(String id) {
        this.id = id;
    }

    protected SendMessage sendNewMessage(String text) {
        return new SendMessage().setText(text);
    }

    protected SendMessage sendNewMessage(String text, ReplyKeyboardMarkup replyKeyboardMarkup) {
        return new SendMessage().setText(text).setReplyMarkup(replyKeyboardMarkup);
    }

    protected SendMessage sendNewMessage(String text, InlineKeyboardMarkup inlineKeyboardMarkup) {
        return new SendMessage().setText(text).setReplyMarkup(inlineKeyboardMarkup);
    }

    protected SendMessage sendNewMessageAndRemoveKeyboard(String text,ReplyKeyboardRemove replyKeyboardRemove) {

        return new SendMessage().setText(text).setReplyMarkup(replyKeyboardRemove);
    }

    public DeleteMessage deleteMessage(Integer messageId) {

        return new DeleteMessage().setMessageId(messageId);
    }
}
