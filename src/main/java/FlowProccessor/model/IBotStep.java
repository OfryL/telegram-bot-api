package FlowProccessor.model;

import org.json.JSONObject;
import org.telegram.telegrambots.api.objects.Update;

public interface IBotStep {

     String begin(JSONObject flowInput);

     boolean isValid(Update update);

     boolean process(Update update, JSONObject flowInput);

     String getInvalidText();
}
