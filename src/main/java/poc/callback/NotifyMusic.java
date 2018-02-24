package poc.callback;

import FlowProccessor.model.impl.BotBaseFlowEntity;
import FlowProccessor.model.impl.BotBaseModelEntity;
import FlowProccessor.model.impl.BotFlowCallback;
import org.telegram.telegrambots.api.methods.BotApiMethod;

import java.io.Serializable;

public class NotifyMusic extends BotFlowCallback {

    public NotifyMusic(BotBaseFlowEntity from) {
        super(from);
    }

    @Override
    public BotApiMethod<? extends Serializable> doCallback(BotBaseModelEntity modelEntity) {
        return null;
    }
}
