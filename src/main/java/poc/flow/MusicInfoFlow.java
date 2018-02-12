package poc.flow;

import FlowProccessor.model.impl.BotFlow;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import poc.model.MusicModel;
import poc.model.PersonalInfoModel;

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
    public SendMessage complete(Update update) {

        String message = "Thanks for filling up music your favorite information";

        return new SendMessage().setText(
                message
        );
    }

}
