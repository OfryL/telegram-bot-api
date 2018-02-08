package FlowProccessor.factory;

import FlowProccessor.model.impl.BotCondition;

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

    public BotCondition equalsCondition(final String expected) {

        return new BotCondition() {
            @Override
            public boolean checkCondition(String stepResponse) {
                return stepResponse.equalsIgnoreCase(expected);
            }
        };
    }

    public BotCondition always() {
        return new BotCondition() {
            @Override
            public boolean checkCondition(String stepResponse) {
                return true;
            }
        };
    }

}
