package FlowProccessor.model;


import FlowProccessor.model.impl.BotBaseFlowEntity;
import FlowProccessor.model.impl.BotBaseModelEntity;
import FlowProccessor.model.impl.BotStep;
import FlowProccessor.model.impl.BotTransition;

import java.util.List;
import java.util.Set;

public interface IBotFlow {

    public <T extends BotBaseModelEntity> T getModel();

    public Set<BotTransition> getTransitions();

    public BotBaseFlowEntity getActiveEntity();

    public List<BotBaseFlowEntity> getFlowEntities();

    public void complete();
}
