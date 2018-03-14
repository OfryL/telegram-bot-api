package poc;

import FlowProccessor.BotFlowProcessor;
import FlowProccessor.config.ProcessorConfig;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.exceptions.TelegramApiRequestException;

public class MainPOC {
    public static void main(String args[]) {

        ApiContextInitializer.init();

        // Instantiate Telegram Bots API
        TelegramBotsApi botsApi = new TelegramBotsApi();

        // Register our bot
        try {

            POCBotController bot = new POCBotController();

            botsApi.registerBot(bot);

            BotFlowProcessor.getInstance().init(
                    bot
            );

        } catch (TelegramApiRequestException e) {
            e.printStackTrace();
        }
    }

}
