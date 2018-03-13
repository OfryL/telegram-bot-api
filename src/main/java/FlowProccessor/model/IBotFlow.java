package FlowProccessor.model;


import FlowProccessor.controller.BotFlowController;
import FlowProccessor.model.impl.*;
import org.telegram.telegrambots.api.objects.Update;

import java.util.Set;

public interface IBotFlow {

    BotBaseModelEntity getModel();

    Set<BotTransition> getTransitions();

    BotBaseFlowEntity getActiveEntity();

    Set<BotFlowCallback> getCallbacks();

    void onExist(Update update, BotBaseModelEntity model, BotFlowController controller);

    void complete(Update update, BotBaseModelEntity model, BotFlowController controller);
}
