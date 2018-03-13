package FlowProccessor.model;

import FlowProccessor.model.impl.BotBaseModelEntity;
import org.telegram.telegrambots.api.objects.Update;

public interface IBotCondition {

     boolean checkCondition(Update update, BotBaseModelEntity model);
}
