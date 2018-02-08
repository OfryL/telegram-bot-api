package FlowProccessor.locator;

import FlowProccessor.factory.BotFlowFactory;
import FlowProccessor.model.impl.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public final class EntityLocator {

    public static BotCommand locateCommand(Set<BotCommand> commands, final String commandString) {

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

    public static BotBaseFlowEntity locateFlowEnttiy(BotFlow flow, final String entityId) {

        List<BotBaseFlowEntity> entities = flow.getFlowEntities();

        Optional<BotBaseFlowEntity> entity = entities.stream().filter(e -> e.getId().equals(entityId)).findFirst();

        if(!entity.isPresent()) {

            throw new IllegalArgumentException(
                    String.format(
                            "Could not find entity %s in flow %s",
                            entityId,
                            flow.getId()
                    )
            );
        }

        return entity.get();
    }

    public static BotTransition locateTransition(BotFlow flow, BotBaseFlowEntity entity) {

        Set<BotTransition> transitions = flow.getTransitions();

        Optional<BotTransition> transition = transitions.stream().filter( t -> t.getFrom().equals(entity.getId())).findFirst();

        return transition.orElse(null);
    }

}
