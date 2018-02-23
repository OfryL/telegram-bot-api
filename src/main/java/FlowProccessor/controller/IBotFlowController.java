package FlowProccessor.controller;

import FlowProccessor.cache.AbstractCacheManager;
import FlowProccessor.factory.BotFlowFactory;
import FlowProccessor.model.impl.BotCommand;
import org.telegram.telegrambots.api.methods.BotApiMethod;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

public interface IBotFlowController {

    <T extends Serializable, Method extends BotApiMethod<T>> T executeOperation(Update update, Method method);

    Long getUserIdentityNumber(Update update);

    void sendDefaultResponse(Update update);

    AbstractCacheManager getCacheManager();

}
