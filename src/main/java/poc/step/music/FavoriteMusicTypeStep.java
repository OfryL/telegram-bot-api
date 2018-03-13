package poc.step.music;

import FlowProccessor.controller.BotFlowController;
import FlowProccessor.model.impl.BotBaseModelEntity;
import org.telegram.telegrambots.api.objects.CallbackQuery;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import poc.step.BaseStep;

import java.util.*;

public class FavoriteMusicTypeStep extends BaseStep {

    public FavoriteMusicTypeStep(String id) {
        super(id);
    }

    private Map<String, String> options = new HashMap<>();

    @Override
    public void begin(Update update, BotBaseModelEntity model, BotFlowController controller) {

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
        List<InlineKeyboardButton> back = new ArrayList<>();
        back.add(new InlineKeyboardButton().setText("back").setCallbackData("back"));
        rowsInline.add(back);

        // Add it to the message
        markupInline.setKeyboard(rowsInline);

        controller.executeOperation(
                update,
                sendNewMessage("Please select your favorite music type", markupInline)
        );
    }

    @Override
    public boolean isValid(Update update, BotBaseModelEntity model, BotFlowController controller) {

        CallbackQuery query = update.getCallbackQuery();

        if (query != null) {

            String key = query.getData();

            return options.get(key) != null;
        }

        return false;

    }

    @Override
    public boolean process(Update update, BotBaseModelEntity model, BotFlowController controller) {

        model.set("favoriteType",update.getCallbackQuery().getData() );

        return true;
    }

    @Override
    public void invalidMessage(Update update, BotBaseModelEntity model, BotFlowController controller) {

        controller.executeOperation(
                update,
                sendNewMessage("You Must select one of the options")
        );
    }

    @Override
    public void complete(Update update, BotBaseModelEntity model, BotFlowController controller) {

        controller.executeOperation(
                update,
                deleteMessage(update.getCallbackQuery().getMessage().getMessageId())
        );
    }
}
