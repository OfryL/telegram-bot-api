package FlowProccessor.controller;

import FlowProccessor.cache.BotCacheManager;
import org.telegram.telegrambots.api.methods.BotApiMethod;
import org.telegram.telegrambots.api.objects.Update;

import java.io.Serializable;

public interface IBotFlowController {

    <T extends Serializable, Method extends BotApiMethod<T>> T executeOperation(Update update, Method method);

    void sendVideo(Update update, String videoUrl, String caption);

    void sendPhoto(Update update, String photo, String caption);

    Long getUserCacheIdentifier(Update update);

    Integer getUserId(Update update);

    void sendDefaultResponse(Update update);

    BotCacheManager getCacheManager();

}
