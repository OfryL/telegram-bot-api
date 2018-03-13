package FlowProccessor.factory;

import FlowProccessor.model.BotBaseFlowEntity;

public abstract class BotFlowFactory extends BotBaseFlowEntity implements IBotFlowFactory{

    public BotFlowFactory(String id) {
        super(id);
    }
}
