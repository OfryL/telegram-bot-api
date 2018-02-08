package FlowProccessor.model;

import org.json.JSONObject;
import org.telegram.telegrambots.api.objects.Update;

public interface IBotStep {

    public String begin(String lastResult);

    public boolean isValid(Update update);

    public void process(Update update, JSONObject flowInput);

    public String getInvalidText();
}
