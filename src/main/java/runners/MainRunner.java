package runners;

import controllers.MainBotController;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.exceptions.TelegramApiException;

public class MainRunner {
    public static void main(String args[]) {
        ApiContextInitializer.init();

        // Instantiate Telegram Bots API
        TelegramBotsApi botsApi = new TelegramBotsApi();

        // Register our bot
        try {
            botsApi.registerBot(new MainBotController());
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
