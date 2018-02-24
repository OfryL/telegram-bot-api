package FlowProccessor.locator;

import FlowProccessor.factory.BotFlowFactory;
import FlowProccessor.model.impl.*;
import org.telegram.telegrambots.api.objects.Update;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class EntityLocator {

    public static BotCommand locateCommand(Set<BotCommand> commands, final Update update) {

        String commandString = update.getMessage() != null ? update.getMessage().getText() : "";

         Optional<BotCommand> command = commands.stream().filter(c -> c.getIdentifier().equals(commandString)).findFirst();

         return command.orElse(null);
    }

    public static BotFlow locateFlow(Set<BotFlow> flows, final String flowId) {

        Optional<BotFlow> flow = flows.stream().filter(f -> f.getId().equals(flowId)).findFirst();

        return flow.orElse(null);

    }

    public static BotFlowFactory locateFlowFactory(Set<BotFlowFactory> factories, String flowId) {

        Optional<BotFlowFactory> flowFactory = factories.stream().filter(fc -> fc.getId().equals(flowId)).findFirst();

        return flowFactory.orElse(null);
    }

    public static Set<BotTransition> locateTransitions(BotFlow flow, BotBaseFlowEntity entity) {

        Set<BotTransition> transitions = flow.getTransitions();

        Stream<BotTransition> matched = transitions.stream().filter(t -> t.getFrom().getId().equals(entity.getId()));

        return matched.collect(Collectors.toSet());
    }

    public static BotFlowCallback locateFlowCallback(BotFlow flow, BotBaseFlowEntity from) {

        Optional<BotFlowCallback> callback = flow.getCallbacks().stream().filter(c -> c.getFrom().getId().equals(from.getId())).findFirst();

        return callback.orElse(null);

    }

}
