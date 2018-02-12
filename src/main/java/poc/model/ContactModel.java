package poc.model;

import FlowProccessor.model.IBotFlowModel;
import FlowProccessor.model.impl.BotBaseModelEntity;
import org.json.JSONObject;
import org.telegram.telegrambots.api.objects.Contact;

/**
 * The type Test model.
 */
public class ContactModel extends BotBaseModelEntity implements IBotFlowModel {

    private Integer age;

    private Contact contact;
    
    public void setFlowInput(JSONObject flowInput) {

        this.setAge(
                flowInput.getInt("age")
        );

        this.setContact(
                (Contact) flowInput.get("contact")
        );

    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }
}
