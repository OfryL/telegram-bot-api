package poc.step.contact;

import FlowProccessor.model.impl.BotBaseModelEntity;
import FlowProccessor.model.impl.BotStep;
import org.json.JSONObject;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Contact;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardRemove;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardRow;
import poc.step.BaseStep;

import java.util.ArrayList;
import java.util.List;

public class ContactStep extends BaseStep {

    public ContactStep(String id) {
        super(id);
    }

    @Override
    public SendMessage begin(BotBaseModelEntity model) {

        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);
        List<KeyboardRow> keyboard = new ArrayList<>();
        KeyboardRow row = new KeyboardRow();
        KeyboardButton keyboardButton = new KeyboardButton().setText("Share Contact").setRequestContact(true);
        row.add(keyboardButton);
        keyboard.add(row);
        replyKeyboardMarkup.setKeyboard(keyboard);
        return sendNewMessage("Please share yourself as contact",replyKeyboardMarkup);
    }

    @Override
    public boolean isValid(Update update) {

        Contact contact = update.getMessage().getContact();

        return contact != null;
    }

    @Override
    public boolean process(Update update, BotBaseModelEntity model) {

        model.set("contact",update.getMessage().getContact());

        return true;
    }

    @Override
    public SendMessage complete(Update update, BotBaseModelEntity model) {

        ReplyKeyboardRemove replyKeyboardRemove = new ReplyKeyboardRemove();

        return new SendMessage().setChatId(update.getMessage().getChatId()).setText("Thanks for sending contact").setReplyMarkup(replyKeyboardRemove);
    }

    @Override
    public SendMessage invalidMessage() {

        return this.sendNewMessage("You Must share yourself as contact");
    }
}
