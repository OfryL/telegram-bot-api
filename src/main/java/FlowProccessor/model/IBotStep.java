package FlowProccessor.model;

import org.json.JSONObject;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;

public interface IBotStep {

    SendMessage begin(JSONObject flowInput);

    boolean isValid(Update update);

    boolean process(Update update, JSONObject flowInput);

    SendMessage complete(Update update, JSONObject flowInput);

    SendMessage invalidMessage();
}
