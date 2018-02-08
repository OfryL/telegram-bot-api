package client;

import FlowProccessor.BotFlowProcessor;
import FlowProccessor.factory.BotConditionFactory;
import FlowProccessor.model.impl.*;
import client.MainBotController;
import org.json.JSONObject;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import org.telegram.telegrambots.exceptions.TelegramApiRequestException;

import java.util.ArrayList;
import java.util.List;

public class MainRunner {
    public static void main(String args[]) {

        ApiContextInitializer.init();

        // Instantiate Telegram Bots API
        TelegramBotsApi botsApi = new TelegramBotsApi();

        // Register our bot
        try {

            MainBotController bot = new MainBotController();

            botsApi.registerBot(bot);

            BotFlowProcessor.getInstance().init(
                    bot
            );

        } catch (TelegramApiRequestException e) {
            e.printStackTrace();
        }
    }

}
