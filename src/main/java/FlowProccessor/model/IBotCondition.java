package FlowProccessor.model;

import FlowProccessor.model.impl.BotStep;
import org.json.JSONObject;
import org.telegram.telegrambots.api.objects.Update;

public interface IBotCondition {

     boolean checkCondition(JSONObject flowInput);
}
