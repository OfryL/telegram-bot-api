package client.flow;

import FlowProccessor.model.impl.BotBaseModelEntity;
import FlowProccessor.model.impl.BotFlow;
import client.model.TestModel;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;

public class TestFlow extends BotFlow {

    private TestModel model;

    /**
     * Instantiates a new Bot flow.
     *
     * @param id the id
     */
    public TestFlow(String id) {
        super(id);
        model = new TestModel();
    }

    @Override
    public TestModel getModel() {
        return model;
    }

    public void setModel(TestModel model) {
        this.model = model;
    }

    @Override
    public SendMessage complete(Update update) {

        return new SendMessage().setText(
                String.format(
                        "Finished flow, User number is: %s, User Text is: %s",
                        model.getNumber(),
                        model.getText()
                )
        );
    }
}
