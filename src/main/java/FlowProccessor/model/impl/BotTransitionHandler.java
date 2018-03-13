package FlowProccessor.model.impl;

import FlowProccessor.model.IBotTransitionHandler;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardRemove;


/**
 * The type Bot transition handler.
 */
public abstract class BotTransitionHandler implements IBotTransitionHandler {

    private boolean direction;
    private BotTransition transition;
    private BotFlow flow;

    /**
     * Instantiates a new Bot transition handler.
     *
     * @param direction  the direction
     * @param transition the transition
     * @param flow       the flow
     */
    public BotTransitionHandler(boolean direction, BotTransition transition, BotFlow flow) {
        this.direction = direction;
        this.transition = transition;
        this.flow = flow;
    }

    /**
     * Is direction next boolean.
     *
     * @return the boolean
     */
    public boolean isDirectionNext() {
        return direction;
    }

    /**
     * Sets direction.
     *
     * @param direction the direction
     */
    public void setDirection(boolean direction) {
        this.direction = direction;
    }

    /**
     * Gets transition.
     *
     * @return the transition
     */
    public BotTransition getTransition() {
        return transition;
    }

    /**
     * Sets transition.
     *
     * @param transition the transition
     */
    public void setTransition(BotTransition transition) {
        this.transition = transition;
    }

    /**
     * Gets flow.
     *
     * @return the flow
     */
    public BotFlow getFlow() {
        return flow;
    }

    /**
     * Sets flow.
     *
     * @param flow the flow
     */
    public void setFlow(BotFlow flow) {
        this.flow = flow;
    }
}
