package poc.step.contact;

import FlowProccessor.controller.BotFlowController;
import FlowProccessor.model.impl.BotBaseModelEntity;
import org.telegram.telegrambots.api.objects.Update;
import poc.step.BaseStep;

public class AgeStep extends BaseStep {

    public AgeStep(String id) {
        super(id);
    }


    @Override
    public void begin(Update update, BotBaseModelEntity model, BotFlowController controller) {

        controller.executeOperation(
                update,
                sendNewMessage("Please fill your age")
        );
    }

    @Override
    public boolean isValid(Update update, BotBaseModelEntity model, BotFlowController controller) {

        String input = update.getMessage().getText();

        try {

            Integer age = Integer.parseInt(input);

            return age > 20;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean process(Update update, BotBaseModelEntity model, BotFlowController controller) {

        model.set(
                "age",
                Integer.parseInt(update.getMessage().getText())
        );

        return true;
    }

    @Override
    public void invalidMessage(Update update, BotBaseModelEntity model, BotFlowController controller) {

        controller.executeOperation(
                update,
                sendNewMessage("Must be over 20 years old")
        );
    }
}
