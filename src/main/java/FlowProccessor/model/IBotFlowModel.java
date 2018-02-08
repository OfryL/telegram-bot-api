package FlowProccessor.model;

import FlowProccessor.model.impl.BotBaseModelEntity;
import org.json.JSONObject;

/**
 * The interface Bot flow model.
 */
public interface IBotFlowModel {

    /**
     * Sets flow input.
     *
     * @param flowInput the flow input
     */
    public void setFlowInput(JSONObject flowInput);
}