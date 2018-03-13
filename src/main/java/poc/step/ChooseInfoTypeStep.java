package poc.step;

import FlowProccessor.controller.BotFlowController;
import FlowProccessor.model.impl.BotBaseModelEntity;
import org.telegram.telegrambots.api.objects.CallbackQuery;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChooseInfoTypeStep extends BaseStep {

    public ChooseInfoTypeStep(String id) {
        super(id);
    }

    private Map<String, String> options = new HashMap<>();

    @Override
    public void begin(Update update, BotBaseModelEntity model, BotFlowController controller) {

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

        options.forEach((k, v) -> {

            rowInline.add(new InlineKeyboardButton().setText(v).setCallbackData(k));
        });

        // Set the keyboard to the markup
        rowsInline.add(rowInline);
        // Add it to the message
        markupInline.setKeyboard(rowsInline);


        controller.executeOperation(
                update,
                sendNewMessage("Hey, Please choose what type of info would you like to fill up!", markupInline)
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

        model.set("infoType", update.getCallbackQuery().getData());

        return true;
    }

    @Override
    public void invalidMessage(Update update, BotBaseModelEntity model, BotFlowController controller) {

        controller.executeOperation(
                update,
                sendNewMessage("You must pick one of the options!")
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
