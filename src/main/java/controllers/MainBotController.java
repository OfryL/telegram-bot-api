package controllers;

import MessageProcessor.BotMessageProcessor;
import MessageProcessor.BotMessageProcessorImp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

public class MainBotController extends TelegramLongPollingBot {
    private static final String NEW_UPDATE_LOG = "New update is here %s";

    private final Logger logger = LoggerFactory.getLogger(MainBotController.class);

    public void onUpdateReceived(Update update) {
        logger.debug(String.format(NEW_UPDATE_LOG, update));

        BotMessageProcessor tgsMessageProcessor = new BotMessageProcessorImp();
        tgsMessageProcessor.init(null, update.getMessage());
        tgsMessageProcessor.process();
        executeSendMessage(tgsMessageProcessor.getResponse());
    }

    public String getBotUsername() {
        return "test";
    }

    public String getBotToken() {
        return "";
    }

    private void executeSendMessage(SendMessage sendMessage) {
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
