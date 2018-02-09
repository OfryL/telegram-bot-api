package client;

import FlowProccessor.BotFlowProcessor;
import FlowProccessor.controller.BotFlowController;
import FlowProccessor.factory.BotFlowFactory;
import FlowProccessor.model.impl.BotCommand;
import MessageProcessor.BotMessageProcessor;
import MessageProcessor.BotMessageProcessorImp;
import client.command.StartCommand;
import client.config.ProcessorConfig;
import client.flow.TestFlowFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MainBotController extends BotFlowController {
    private static final String NEW_UPDATE_LOG = "New update is here %s";

    private final Logger logger = LoggerFactory.getLogger(MainBotController.class);

    public void onUpdateReceived(Update update) {

        logger.debug(String.format(NEW_UPDATE_LOG, update));

        BotFlowProcessor.getInstance().processUpdate(update);
    }

    public String getBotUsername() {
        return ProcessorConfig.BOT_USER;
    }

    public String getBotToken() {
        return ProcessorConfig.BOT_TOKEN;
    }

    private void executeSendMessage(SendMessage sendMessage) {
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Set<BotFlowFactory> getFlowFactories() {

        Set<BotFlowFactory> factories = new HashSet<>();

        factories.add(
                new TestFlowFactory("test")
        );

        return factories;
    }

    @Override
    public Set<BotCommand> getCommands() {

        Set<BotCommand> commands = new HashSet<>();

        commands.add(new StartCommand("test"));

        return commands;
    }

    @Override
    public void sendMessage(Update update, String text) {

        SendMessage message = new SendMessage().setText(text).setChatId(getUserIdentityNumber(update));

        executeSendMessage(message);
    }

    public Long getUserIdentityNumber(Update update) {
        return update.getMessage().getChatId();
    }

    @Override
    public SendMessage getDefaultResponse(Update update) {

        SendMessage message = new SendMessage().setText("Dunno what to do!").setChatId(getUserIdentityNumber(update));

        return message;
    }
}
