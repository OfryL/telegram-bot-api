package client.model;

import FlowProccessor.model.IBotFlowModel;
import FlowProccessor.model.impl.BotBaseModelEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.json.JSONObject;

/**
 * The type Test model.
 */
public class TestModel extends BotBaseModelEntity implements IBotFlowModel {

    private static final String NUMBER_FIELD = "userNumber";
    private static final String TEXT_FIELD = "userText";
    private static final String IS_CONFIRMED_FIELD = "confirmedText";

    @JsonProperty(NUMBER_FIELD)
    private String number;

    @JsonProperty(TEXT_FIELD)
    private String text;

    @JsonProperty(IS_CONFIRMED_FIELD)
    private boolean confirmed;

    @Override
    public void setFlowInput(JSONObject flowInput) {

        number = flowInput.getString("userNumber");
        text = flowInput.optString("userText");
        confirmed = flowInput.optBoolean("confirmedText");

    }

    /**
     * Gets number.
     *
     * @return the number
     */
    public String getNumber() {
        return number;
    }

    /**
     * Sets number.
     *
     * @param number the number
     */
    public void setNumber(String number) {
        this.number = number;
    }

    /**
     * Gets text.
     *
     * @return the text
     */
    public String getText() {
        return text;
    }

    /**
     * Sets text.
     *
     * @param text the text
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * Is confirmed boolean.
     *
     * @return the boolean
     */
    public boolean isConfirmed() {
        return confirmed;
    }

    /**
     * Sets confirmed.
     *
     * @param confirmed the confirmed
     */
    public void setConfirmed(boolean confirmed) {
        this.confirmed = confirmed;
    }

    @Override
    public String toString() {
        return "TestModel{" +
                "number='" + number + '\'' +
                ", text='" + text + '\'' +
                ", confirmed='" + confirmed + '\'' +
                '}';
    }
}
