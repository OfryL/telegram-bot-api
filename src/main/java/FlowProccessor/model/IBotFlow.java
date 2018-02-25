package FlowProccessor.model;


import FlowProccessor.controller.BotFlowController;
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

    Set<BotTransition> getChildInterceptors();

    void onBack(Update update, BotBaseModelEntity model, BotFlowController controller);

    void complete(Update update, BotBaseModelEntity model, BotFlowController controller);
}
