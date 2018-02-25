package poc.flow;

import FlowProccessor.controller.BotFlowController;
import FlowProccessor.model.impl.BotBaseModelEntity;
import FlowProccessor.model.impl.BotFlow;
import org.json.JSONObject;
import org.telegram.telegrambots.api.methods.BotApiMethod;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.api.objects.Update;
import poc.model.MusicModel;
import poc.model.PersonalInfoModel;

import java.io.Serializable;

public class MusicInfoFlow extends BotFlow {

    private MusicModel model;

    /**
     * Instantiates a new Bot flow.
     *
     * @param id the id
     */
    public MusicInfoFlow(String id) {
        super(id);
        setModel(new MusicModel());
    }

    @Override
    public MusicModel getModel() {
        return model;
    }

    public void setModel(MusicModel model) {
        this.model = model;
    }

    @Override
    public void complete(Update update, BotBaseModelEntity parentModel, BotFlowController controller) {

        String message = "Thanks for filling up music your favorite information!";

        parentModel.set(
                "musicModel",
                this.getModel()
        );

        controller.executeOperation(
                update,
                sendNewMessage(message)
        );
    }

    @Override
    public void onBack(Update update, BotBaseModelEntity model, BotFlowController controller) {

        controller.executeOperation(
                update,
                deleteFromCallback(update)
        );
    }
}
