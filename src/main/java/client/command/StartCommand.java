package client.command;

import FlowProccessor.model.impl.BotCommand;

public class StartCommand extends BotCommand {

    public StartCommand(String flowId) {

        super("/start", flowId);
    }

    @Override
    public String getMessage() {
        return "Hello and welcome this shiet nigga";
    }
}
