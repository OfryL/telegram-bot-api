package FlowProccessor.factory;

import FlowProccessor.model.impl.BotFlow;

/**
 * The interface Flow factory.
 */
public interface IBotFlowFactory {

    /**
     * Create flow t.
     *
     * @param <T> the type parameter
     * @return the t
     */
    public <T extends BotFlow> T createFlow();
}
