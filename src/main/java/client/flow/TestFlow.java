package client.flow;

import FlowProccessor.model.impl.BotBaseModelEntity;
import FlowProccessor.model.impl.BotFlow;
import client.model.TestModel;
import org.json.JSONObject;
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
    public SendMessage complete(Update update, JSONObject parentFlowInput) {

        String message = "Finished flow, User number is : " + model.getNumber();

        message = String.format(
                "%s, %s",
                message,
                model.isConfirmed() ? "User was validated by specific text" : "User was validated by text length!"
        );

        return new SendMessage().setText(
                message
        );
    }
}
