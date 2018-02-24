package FlowProccessor.model;


import FlowProccessor.model.impl.*;
import org.json.JSONObject;
import org.telegram.telegrambots.api.methods.BotApiMethod;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

public interface IBotFlow {

    BotBaseModelEntity getModel();

    Set<BotTransition> getTransitions();

    BotBaseFlowEntity getActiveEntity();

    Set<BotFlowCallback> getCallbacks();

    BotTransition getBackTransition();

    BotApiMethod<? extends Serializable> onBack(Update update);

    SendMessage complete(Update update, BotBaseModelEntity parentModel);
}
