package FlowProccessor.factory;

import FlowProccessor.model.impl.BotBaseModelEntity;
import FlowProccessor.model.impl.BotCondition;
import org.json.JSONObject;
import org.telegram.telegrambots.api.objects.Update;

import java.lang.reflect.InvocationTargetException;

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
            public boolean checkCondition(Update update, BotBaseModelEntity model) {
                return String.valueOf(model.get(key)).equalsIgnoreCase(expected);
            }
        };
    }

    public BotCondition always() {
        return new BotCondition() {
            @Override
            public boolean checkCondition(Update update,BotBaseModelEntity model) {
                return true;
            }
        };
    }

    public BotCondition oppositeOf(BotCondition condition) {

        return new BotCondition() {
            @Override
            public boolean checkCondition(Update update, BotBaseModelEntity model) {
                return !condition.checkCondition(update, model);
            }
        };

    }

    public BotCondition callbackDataEqualsCondition(String expected) {

        return new BotCondition() {
            @Override
            public boolean checkCondition(Update update, BotBaseModelEntity model) {
                return update.getCallbackQuery().getData().equals(expected);
            }
        };
    }
}
