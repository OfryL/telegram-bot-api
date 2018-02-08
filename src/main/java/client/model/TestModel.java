package client.model;

import FlowProccessor.model.IBotFlowModel;
import FlowProccessor.model.impl.BotBaseModelEntity;
import org.json.JSONObject;

public class TestModel extends BotBaseModelEntity implements IBotFlowModel {

    private String some;


    @Override
    public void setFlowInput(JSONObject flowInput) {

    }

    public String getSome() {
        return some;
    }

    public void setSome(String some) {
        this.some = some;
    }
}
