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
}
