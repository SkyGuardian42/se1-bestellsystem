package datamodel;

/**
 * @author Malte Janßen
 */

public class Article {
    private final String id;
    private String description;
    private long unitPrice;
    private int unitsInStore;

    protected Article(String id, String description, long price, int units) {
        this.id = id;
        this.description = description;
        this.unitPrice = price;
        this.unitsInStore = units;
    }

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the unitPrice
     */
    public long getUnitPrice() {
        return unitPrice;
    }

    /**
     * @param unitPrice the unitPrice to set
     */
    public void setUnitPrice(long unitPrice) {
        this.unitPrice = unitPrice;
    }

    /**
     * @return the unitsInStore
     */
    public int getUnitsInStore() {
        return unitsInStore;
    }

    /**
     * @param unitsInStore the unitsInStore to set
     */
    public void setUnitsInStore(int unitsInStore) {
        this.unitsInStore = unitsInStore;
    }

}
