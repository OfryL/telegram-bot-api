package poc.step.contact;

import FlowProccessor.controller.BotFlowController;
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
    public void begin(Update update, BotBaseModelEntity model, BotFlowController controller) {

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

        controller.executeOperation(
                update,
                sendNewMessage("Please share yourself as contact",replyKeyboardMarkup)
        );
    }

    @Override
    public boolean isValid(Update update, BotBaseModelEntity model, BotFlowController controller) {

        Contact contact = update.getMessage().getContact();

        return contact != null;
    }

    @Override
    public boolean process(Update update, BotBaseModelEntity model, BotFlowController controller) {

        model.set("contact",update.getMessage().getContact());

        return true;
    }

    @Override
    public void complete(Update update, BotBaseModelEntity model, BotFlowController controller) {

        ReplyKeyboardRemove replyKeyboardRemove = new ReplyKeyboardRemove();

        controller.executeOperation(
                update,
                new SendMessage().setChatId(update.getMessage().getChatId()).setText("Thanks for sending contact").setReplyMarkup(replyKeyboardRemove)
        );
    }

    @Override
    public void invalidMessage(Update update, BotBaseModelEntity model, BotFlowController controller) {

        controller.executeOperation(
                update,
                sendNewMessage("You Must share yourself as contact")
        );
    }
}
