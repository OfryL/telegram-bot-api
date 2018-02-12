package poc.step.music;

import FlowProccessor.model.impl.BotStep;
import org.json.JSONObject;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.CallbackQuery;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardRemove;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.nio.charset.Charset;
import java.util.*;

public class FavoriteMusicTypeStep extends BotStep {

    public FavoriteMusicTypeStep(String id) {
        super(id);
    }

    private Map<String, String> options = new HashMap<>();

    @Override
    public SendMessage begin(JSONObject flowInput) {

        options.put(
                "rock",
                "Rock&Fucking Roll"
        );

        options.put(
                "classic",
                "Classic"
        );

        options.put(
                "trance",
                "Trance music"
        );

        options.put(
                "folk",
                "Folk"
        );

        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        List<InlineKeyboardButton> first = new ArrayList<>();
        List<InlineKeyboardButton> second = new ArrayList<>();

        options.forEach((k, v) -> {

            if(first.size() == second.size()) {
                first.add(new InlineKeyboardButton().setText(v).setCallbackData(k));
            }
            else {
                second.add(new InlineKeyboardButton().setText(v).setCallbackData(k));
            }

        });

        rowsInline.add(first);
        rowsInline.add(second);

        // Add it to the message
        markupInline.setKeyboard(rowsInline);

        return sendNewMessage("Please select your favorite music type", markupInline);
    }

    @Override
    public boolean isValid(Update update) {

        CallbackQuery query = update.getCallbackQuery();

        if (query != null) {

            String key = query.getData();

            return options.get(key) != null;
        }

        return false;

    }

    @Override
    public boolean process(Update update, JSONObject flowInput) {

        flowInput.put(
                "favoriteType",
                update.getCallbackQuery().getData()
        );

        return true;
    }

    @Override
    public SendMessage invalidMessage() {

        return this.sendNewMessage("You Must select one of the options");
    }

    @Override
    public SendMessage complete(Update update, JSONObject flowInput) {

        ReplyKeyboardRemove replyKeyboardRemove = new ReplyKeyboardRemove();

        return new SendMessage().setChatId(update.getCallbackQuery().getMessage().getChatId()).setText("Thanks for peaking your favorite type!").setReplyMarkup(replyKeyboardRemove);
    }
}
