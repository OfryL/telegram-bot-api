package poc.flow;

import FlowProccessor.model.impl.BotFlow;
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
    public SendMessage complete(Update update) {

        String message = "Finihsed personal info flow! , You can press /start to re-do";

        return new SendMessage().setText(
                message
        );
    }

}
