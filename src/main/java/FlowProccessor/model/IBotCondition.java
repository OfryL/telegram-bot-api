package FlowProccessor.model;

import FlowProccessor.model.impl.BotBaseModelEntity;
import FlowProccessor.model.impl.BotStep;
import org.json.JSONObject;
import org.telegram.telegrambots.api.objects.Update;

import java.lang.reflect.InvocationTargetException;

public interface IBotCondition {

     boolean checkCondition(BotBaseModelEntity model);
}
