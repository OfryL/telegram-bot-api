package FlowProccessor.model.impl;

import FlowProccessor.controller.BotFlowController;
import FlowProccessor.controller.IBotFlowController;
import FlowProccessor.model.IBotCommand;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;

/**
 * The type Bot command.
 */
public abstract class BotCommand implements IBotCommand {

    private String identifier;
    private String flowEntityId;

    public BotCommand(String identifier, String flowEntityId) {

        this.identifier = identifier;
        this.flowEntityId = flowEntityId;
    }

    @Override
    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }


    @Override
    public String getFlowEntityId() {
        return flowEntityId;
    }

    public void setFlowEntityId(String flowEntityId) {
        this.flowEntityId = flowEntityId;
    }

    @Override
    public void doAction(Update update, BotFlowController controller) {

    }
}
