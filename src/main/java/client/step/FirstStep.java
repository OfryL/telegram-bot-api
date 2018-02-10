package client.step;

import FlowProccessor.model.impl.BotStep;
import org.json.JSONObject;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

public class FirstStep extends BotStep {

    public FirstStep(String id) {
        super(id);
    }

    @Override
    public SendMessage begin(JSONObject flowInput) {

/*       ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);
        List<KeyboardRow> keyboard = new ArrayList<>();
        KeyboardRow row = new KeyboardRow();
        KeyboardButton keyboardButton = new KeyboardButton().setText("Share Contact").setRequestContact(true);
        row.add(keyboardButton);
        keyboard.add(row);
        replyKeyboardMarkup.setKeyboard(keyboard);*/
        return sendNewMessage("Hey, Please enter any number");
    }

    @Override
    public boolean isValid(Update update) {

        String input = update.getMessage().getText();

        boolean valid = true;
        try{
            Integer.parseInt(input);
        }
        catch (Exception e) {
            valid = false;
        }

        return valid;
    }

    @Override
    public boolean process(Update update, JSONObject flowInput) {

        flowInput.put(
                "userNumber",
                update.getMessage().getText()
        );

        return true;
    }

    @Override
    public SendMessage invalidMessage() {

        return this.sendNewMessage("Not a valid number!");
    }
}
