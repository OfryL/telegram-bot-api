package poc.flow;

import FlowProccessor.model.impl.BotBaseModelEntity;
import FlowProccessor.model.impl.BotFlow;
import org.json.JSONObject;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import poc.model.PersonalInfoModel;

public class PersonalInfoFlow extends BotFlow {

    private PersonalInfoModel model;

    /**
     * Instantiates a new Bot flow.
     *
     * @param id the id
     */
    public PersonalInfoFlow(String id) {
        super(id);
        setModel(new PersonalInfoModel());
    }

    @Override
    public PersonalInfoModel getModel() {
        return model;
    }

    public void setModel(PersonalInfoModel model) {
        this.model = model;
    }

    @Override
    public SendMessage complete(Update update, BotBaseModelEntity parentModel) {

        String message;
        if(getModel().getContactModel() != null) {

            message = "I see you shared contact info, Thanks :" + this.getModel().getContactModel().getContact().getFirstName();
        }
        else {
            message="I see you choose music info, Nice to know that you love " + this.getModel().getMusicModel().getFavoriteType();
        }

        return new SendMessage().setText(
                message
        );
    }

}
