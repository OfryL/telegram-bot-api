package FlowProccessor.cache;

import FlowProccessor.factory.BotFlowFactory;
import FlowProccessor.model.impl.BotBaseModelEntity;
import FlowProccessor.model.impl.BotCommand;
import FlowProccessor.model.impl.BotFlow;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface ICacheManager {

    BotFlow getActiveFlow(String userIdentifier);

    Set<BotCommand> getCommands();

    BotFlowFactory getFactory(String flowId);

    void cacheFlow(String userIdentifier, BotFlow flow);

    void clearFlow(String userIdentifier);

    BotFlow getParentFlow(String userIdentifier);

    BotBaseModelEntity getActiveFlowModel(String userIdentifier);
}
