package poc.model;

import FlowProccessor.model.IBotFlowModel;
import FlowProccessor.model.impl.BotBaseModelEntity;
import org.json.JSONObject;

/**
 * The type Test model.
 */
public class MusicModel extends BotBaseModelEntity implements IBotFlowModel {

    private String favoriteSong;
    private String favoriteType;

    public void setFlowInput(JSONObject flowInput) {

        this.setFavoriteSong(
                flowInput.getString("favoriteSong")
        );

        this.setFavoriteType(
                flowInput.getString("favoriteType")
        );

    }

    public String getFavoriteSong() {
        return favoriteSong;
    }

    public void setFavoriteSong(String favoriteSong) {
        this.favoriteSong = favoriteSong;
    }

    public String getFavoriteType() {
        return favoriteType;
    }

    public void setFavoriteType(String favoriteType) {
        this.favoriteType = favoriteType;
    }
}
