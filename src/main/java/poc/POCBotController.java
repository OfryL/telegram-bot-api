package poc;

import FlowProccessor.BotFlowProcessor;
import FlowProccessor.cache.AbstractCacheManager;
import FlowProccessor.controller.BotFlowController;
import FlowProccessor.factory.BotFlowFactory;
import FlowProccessor.model.impl.BotCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.api.methods.BotApiMethod;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import poc.cache.CacheManager;
import poc.command.AbortCommand;
import poc.command.StartCommand;
import poc.config.ProcessorConfig;
import poc.flow.factory.ContactInfoFlowFactory;
import poc.flow.factory.MusicInfoFlowFactory;
import poc.flow.factory.PersonalInfoFlowFactory;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class POCBotController extends BotFlowController {
    private static final String NEW_UPDATE_LOG = "New update is here %s";

    private final Logger logger = LoggerFactory.getLogger(POCBotController.class);
    private CacheManager cacheManager;

    public void onUpdateReceived(Update update) {

        logger.info(String.format(NEW_UPDATE_LOG, update));

        BotFlowProcessor.getInstance().processUpdate(update);
    }

    public String getBotUsername() {
        return ProcessorConfig.BOT_USER;
    }

    public String getBotToken() {
        return ProcessorConfig.BOT_TOKEN;
    }

    @Override
    public AbstractCacheManager getCacheManager() {

        if (this.cacheManager == null) {

            Set<BotFlowFactory> factories = new HashSet<>();

            factories.add(
                    new PersonalInfoFlowFactory("personalInfoFlow")
            );

            factories.add(
                    new ContactInfoFlowFactory("contactFlow")
            );

            factories.add(
                    new MusicInfoFlowFactory("musicFlow")
            );


            Set<BotCommand> commands = new HashSet<>();

            commands.add(new StartCommand("personalInfoFlow"));
            commands.add(new AbortCommand(null));

            this.cacheManager = new CacheManager(factories, commands);
        }

        return cacheManager;
    }

    @Override
    public <T extends Serializable, Method extends BotApiMethod<T>> T executeOperation(Update update, Method method) {

        Long userIdentifier = getChatId(update);

        if (method instanceof SendMessage) {

            ((SendMessage) method).setChatId(userIdentifier);
        }
        else if (method instanceof DeleteMessage) {

            ((DeleteMessage) method).setChatId(String.valueOf(userIdentifier));
        }

        try {
            return execute(method);
        } catch (TelegramApiException e) {

            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Long getUserCacheIdentifier(Update update) {

        return getChatId(update);
    }

    private Long getChatId(Update update) {

        Message message = update.getMessage() != null ? update.getMessage() : update.getCallbackQuery().getMessage();

        return message.getChatId();
    }

    @Override
    public Integer getUserId(Update update) {

        Message message = update.getMessage() != null ? update.getMessage() : update.getCallbackQuery().getMessage();

        return message.getFrom().getId();
    }

    @Override
    public void sendDefaultResponse(Update update) {

        this.executeOperation(update, new SendMessage().setText("Dunno what to do!"));

    }

    @Override
    public void sendVideo(Update update, String videoUrl, String caption) {

    }

    @Override
    public void sendPhoto(Update update, String photo, String caption) {

    }
}
