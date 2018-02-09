package FlowProccessor.factory;

import FlowProccessor.model.impl.BotCondition;
import org.json.JSONObject;

/**
 * The type Bot condition factory.
 */
public class BotConditionFactory {

    private static BotConditionFactory instance;

    private BotConditionFactory(){}

    /**
     * Get instance bot condition factory.
     *
     * @return the bot condition factory
     */
    public static synchronized BotConditionFactory getInstance(){
        if(instance == null){
            synchronized (BotConditionFactory.class) {
                if(instance == null){
                    instance = new BotConditionFactory();
                }
            }
        }
        return instance;
    }

    public BotCondition equalsCondition(final String expected, final String key) {

        return new BotCondition() {
            @Override
            public boolean checkCondition(JSONObject flowInput) {
                return flowInput.getString(key).equalsIgnoreCase(expected);
            }
        };
    }

    public BotCondition always() {
        return new BotCondition() {
            @Override
            public boolean checkCondition(JSONObject flowInput) {
                return true;
            }
        };
    }

}
