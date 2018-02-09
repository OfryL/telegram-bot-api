package client.model;

import FlowProccessor.model.IBotFlowModel;
import FlowProccessor.model.impl.BotBaseModelEntity;
import org.json.JSONObject;

/**
 * The type Test model.
 */
public class TestModel extends BotBaseModelEntity implements IBotFlowModel {

    private String number;
    private String text;

    @Override
    public void setFlowInput(JSONObject flowInput) {

        number = flowInput.getString("userNumber");
        text = flowInput.getString("userText");
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
