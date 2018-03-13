package FlowProccessor.model;

import org.telegram.telegrambots.api.objects.Update;

/**
 * The interface Bot flow model.
 */
public interface IBotTransitionHandler {

    boolean beforeTransition(String userIdentifier, Update update);
}
