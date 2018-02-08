package FlowProccessor.model.impl;

/**
 * The type Bot base flow entity.
 */
public abstract class BotBaseFlowEntity {

    private String id;

    /**
     * Instantiates a new Bot base flow entity.
     *
     * @param id the id
     */
    public BotBaseFlowEntity(String id) {
        this.id = id;
    }

    /**
     * Gets id.
     *
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * Sets id.
     *
     * @param id the id
     */
    public void setId(String id) {
        this.id = id;
    }
}
