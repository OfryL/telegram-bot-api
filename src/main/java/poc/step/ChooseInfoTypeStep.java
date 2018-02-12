package poc.step;

import FlowProccessor.model.impl.BotStep;
import org.json.JSONObject;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.CallbackQuery;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChooseInfoTypeStep extends BotStep {

    public ChooseInfoTypeStep(String id) {
        super(id);
    }

        Map<String,String> options = new HashMap<>();

    @Override
    public SendMessage begin(JSONObject flowInput) {

        options.put(
                "music",
                "Music Info"
        );

        options.put(
                "contact",
                "Contact Info"
        );

        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        List<InlineKeyboardButton> rowInline = new ArrayList<>();

        options.forEach((k,v) -> {

            rowInline.add(new InlineKeyboardButton().setText(v).setCallbackData(k));
        });

        // Set the keyboard to the markup
        rowsInline.add(rowInline);
        // Add it to the message
        markupInline.setKeyboard(rowsInline);
        return sendNewMessage("Hey, Please choose what type of info would you like to fill up!", markupInline);
    }

    @Override
    public boolean isValid(Update update) {

        CallbackQuery query = update.getCallbackQuery();

        if(query != null) {

            String key = query.getData();

            return options.get(key) != null;
        }

        return false;
    }

    @Override
    public boolean process(Update update, JSONObject flowInput) {

        flowInput.put(
                "infoType",
                update.getCallbackQuery().getData()
        );

        return true;
    }

    @Override
    public SendMessage invalidMessage() {

        return this.sendNewMessage("You must pick one of the options!");
    }
}
