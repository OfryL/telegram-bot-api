package FlowProccessor.model;


import FlowProccessor.model.impl.BotBaseFlowEntity;
import FlowProccessor.model.impl.BotBaseModelEntity;
import FlowProccessor.model.impl.BotStep;
import FlowProccessor.model.impl.BotTransition;
import org.json.JSONObject;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;

import java.util.List;
import java.util.Set;

public interface IBotFlow {

     BotBaseModelEntity getModel();

     Set<BotTransition> getTransitions();

     BotBaseFlowEntity getActiveEntity();

     SendMessage complete(Update update, BotBaseModelEntity parentModel);
}
