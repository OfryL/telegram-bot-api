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

     Set<BotFlowFactory> getFlowFactories();

     Set<BotCommand> getCommands();

     void sendMessage(Update update, SendMessage message);

     Long getUserIdentityNumber(Update update);

     SendMessage getDefaultResponse(Update update);

}
