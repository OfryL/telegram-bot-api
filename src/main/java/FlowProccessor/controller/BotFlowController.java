package FlowProccessor.controller;

import FlowProccessor.config.ProcessorConfig;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;

public abstract class BotFlowController extends TelegramLongPollingBot  implements IBotFlowController {

    @Override
    public ProcessorConfig getConfig() {
        return new ProcessorConfig();
    }
}
