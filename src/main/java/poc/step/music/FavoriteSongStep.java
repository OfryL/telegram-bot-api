package poc.step.music;

import FlowProccessor.controller.BotFlowController;
import FlowProccessor.model.impl.BotBaseModelEntity;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import poc.step.BaseStep;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class FavoriteSongStep extends BaseStep {

    public FavoriteSongStep(String id) {
        super(id);
    }

    @Override
    public void begin(Update update, BotBaseModelEntity model, BotFlowController controller) {

        LinkedHashMap<String,String> menu = new LinkedHashMap<>();

        menu.put("BACK_TO_CHOOSE_MUSIC","Back To choose music type");

        controller.executeOperation(
                update,
                sendNewMessage("Please write your favorite song", generateSingleLineMenu(menu))
        );
    }

    @Override
    public boolean isValid(Update update, BotBaseModelEntity model, BotFlowController controller) {

        //TODO Songs api ? Async ?
        return true;
    }

    @Override
    public boolean process(Update update, BotBaseModelEntity model, BotFlowController controller) {

        model.set(
                "favoriteSong",
                update.getMessage().getText()
        );

        return true;
    }

    protected SendMessage sendNewMessage(String text, InlineKeyboardMarkup inlineKeyboardMarkup) {
        return (new SendMessage()).setText(text).setReplyMarkup(inlineKeyboardMarkup);
    }


    protected InlineKeyboardMarkup generateSingleLineMenu(LinkedHashMap<String, String> menuMap) {

        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();

        menuMap.forEach((k, v) -> {

            List<InlineKeyboardButton> rowInline = new ArrayList<>();
            if(k.startsWith("http")){

                //Url button
                rowInline.add(new InlineKeyboardButton().setText(v).setUrl(k));
            }
            else {
                //Callback button
                rowInline.add(new InlineKeyboardButton().setText(v).setCallbackData(k));
            }

            // Set the keyboard to the markup
            rowsInline.add(rowInline);
        });

        // Add it to the message
        markupInline.setKeyboard(rowsInline);

        return markupInline;
    }

}
