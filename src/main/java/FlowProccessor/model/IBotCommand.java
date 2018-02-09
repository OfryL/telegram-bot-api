package FlowProccessor.model;

import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;

public interface IBotCommand {

     String getIdentifier();

     String getFlowEntityId();

     SendMessage getMessage(Update update);
}
