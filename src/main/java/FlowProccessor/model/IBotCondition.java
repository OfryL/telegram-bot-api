package FlowProccessor.model;

import FlowProccessor.model.impl.BotStep;
import org.telegram.telegrambots.api.objects.Update;

public interface IBotCondition {

    public boolean checkCondition(String stepResponse);
}
