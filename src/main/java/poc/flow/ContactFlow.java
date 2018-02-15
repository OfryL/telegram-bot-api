package poc.flow;

import FlowProccessor.model.impl.BotBaseModelEntity;
import FlowProccessor.model.impl.BotFlow;
import org.json.JSONObject;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import poc.model.ContactModel;
import poc.model.MusicModel;

public class ContactFlow extends BotFlow {

    private ContactModel model;

    /**
     * Instantiates a new Bot flow.
     *
     * @param id the id
     */
    public ContactFlow(String id) {
        super(id);
        setModel(new ContactModel());
    }

    @Override
    public ContactModel getModel() {
        return model;
    }

    public void setModel(ContactModel model) {
        this.model = model;
    }

    @Override
    public SendMessage complete(Update update, BotBaseModelEntity parentModel) {

        String message = "User finished the contact flow";

        parentModel.set(
                "contactModel",
                this.getModel()
        );

        return new SendMessage().setText(
                message
        );
    }

}
