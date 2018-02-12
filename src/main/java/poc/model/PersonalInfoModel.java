package poc.model;

import FlowProccessor.model.IBotFlowModel;
import FlowProccessor.model.impl.BotBaseModelEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.json.JSONObject;

/**
 * The type Test model.
 */
public class PersonalInfoModel extends BotBaseModelEntity implements IBotFlowModel {

    private String infoType;
    private ContactModel contactModel;
    private MusicModel musicModel;
    private boolean confirmed;

    public void setFlowInput(JSONObject flowInput) {


        this.setInfoType(
                flowInput.getString("infoType")
        );

        this.setConfirmed(
                flowInput.getBoolean("textConfirmed")
        );

        Object contactModel =  flowInput.opt("contactModel");

        if(contactModel != null) {
            this.setContactModel(
                    (ContactModel)contactModel
            );
        }

        Object musicModel =  flowInput.opt("musicModel");

        if(musicModel != null) {
            this.setMusicModel(
                    (MusicModel) musicModel
            );
        }

    }

    public String getInfoType() {
        return infoType;
    }

    public void setInfoType(String infoType) {
        this.infoType = infoType;
    }

    public ContactModel getContactModel() {
        return contactModel;
    }

    public void setContactModel(ContactModel contactModel) {
        this.contactModel = contactModel;
    }

    public MusicModel getMusicModel() {
        return musicModel;
    }

    public void setMusicModel(MusicModel musicModel) {
        this.musicModel = musicModel;
    }

    public boolean isConfirmed() {
        return confirmed;
    }

    public void setConfirmed(boolean confirmed) {
        this.confirmed = confirmed;
    }
}
