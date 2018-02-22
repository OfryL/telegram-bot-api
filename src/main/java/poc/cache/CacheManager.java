package poc.cache;

import FlowProccessor.cache.AbstractCacheManager;
import FlowProccessor.factory.BotFlowFactory;
import FlowProccessor.locator.EntityLocator;
import FlowProccessor.model.impl.BotBaseModelEntity;
import FlowProccessor.model.impl.BotCommand;
import FlowProccessor.model.impl.BotFlow;

import java.util.*;
import java.util.stream.Collectors;

public class CacheManager extends AbstractCacheManager{

    public CacheManager(Set<BotFlowFactory> botFlowFactories, Set<BotCommand> commands) {
        super(botFlowFactories, commands);
    }
}
