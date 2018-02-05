package controllers;

import MessageProcessor.BotMessageProcessor;
import MessageProcessor.BotMessageProcessorImp;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

public class MainBotController extends TelegramLongPollingBot {

    public void onUpdateReceived(Update update) {
        System.out.println(update);

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
