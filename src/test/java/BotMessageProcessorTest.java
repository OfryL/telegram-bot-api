import MessageProcessor.BotMessageProcessor;
import MessageProcessor.BotMessageProcessorImp;
import MessageProcessor.botState.BotStateData;
import MessageProcessor.botState.BotStateDataImp;
import MessageProcessor.botState.defaultStates.BotStartState;
import MessageProcessor.user.BotUser;
import MessageProcessor.user.BotUserImp;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.User;

public class BotMessageProcessorTest {
    private final Logger logger = LoggerFactory.getLogger(BotMessageProcessorTest.class);

    @Before
    public void before(){
        logger.info("BotMessageProcessorTest started");
    }

    @After
    public void after(){
        logger.info("BotMessageProcessorTest done");
    }

    @Test
    public void checkStartState(){
        Message message = getStartMessageMock();
        BotUser botUser = getTestUser();
        BotMessageProcessor botMessageProcessor = new BotMessageProcessorImp();
        botMessageProcessor.init(botUser, message);
        botMessageProcessor.process();
        SendMessage response = botMessageProcessor.getResponse();

        Assert.assertEquals(response.getText(), BotStartState.BOT_START_MSG);
    }

    private Message getStartMessageMock() {
        Message message = Mockito.mock(Message.class);
        Mockito.when(message.getText()).thenReturn("/start");
        return message;
    }

    private BotUser getTestUser() {
        BotStateData botStateData = new BotStateDataImp();
        return new BotUserImp(new User(), botStateData);
    }
}
