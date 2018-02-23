package FlowProccessor.model.impl;

import FlowProccessor.model.IBotStep;
import org.json.JSONObject;
import org.telegram.telegrambots.api.methods.BotApiMethod;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardMarkup;

import java.io.Serializable;

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
    public boolean isValid(Update update) {
        return true;
    }

    @Override
    public BotApiMethod<? extends Serializable> begin(BotBaseModelEntity model) {
        return null;
    }

    @Override
    public BotApiMethod<? extends Serializable> beforeProcess(Update update, BotBaseModelEntity model) {
        return new SendMessage();
    }

    @Override
    public boolean process(Update update, BotBaseModelEntity model) {
        return true;
    }

    @Override
    public BotApiMethod<? extends Serializable> complete(Update update, BotBaseModelEntity model) {

        return null;
    }

    @Override
    public BotApiMethod<? extends Serializable> invalidMessage() {
        return null;
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


}
