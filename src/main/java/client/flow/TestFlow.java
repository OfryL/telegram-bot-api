package client.flow;

import FlowProccessor.model.impl.BotBaseModelEntity;
import FlowProccessor.model.impl.BotFlow;
import client.model.TestModel;

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
}
