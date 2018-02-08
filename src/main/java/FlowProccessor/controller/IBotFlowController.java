package FlowProccessor.controller;

import FlowProccessor.factory.BotFlowFactory;
import FlowProccessor.model.impl.BotCommand;
import FlowProccessor.model.impl.BotFlow;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;

import java.util.List;
import java.util.Set;

import static MessageProcessor.botState.defaultStates.BotAbortState.ABORT_COMMAND;

public interface IBotFlowController {

    public Set<BotFlowFactory> getFlowFactories();

    public Set<BotCommand> getCommands();

    public void sendMessage(Update update, String text);

    public Long getUserIdentityNumber(Update update);

    public SendMessage getDefaultResponse(Update update);

    //TODO - Flow Transitions

}
