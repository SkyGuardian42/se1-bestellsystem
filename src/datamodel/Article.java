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
        this.setDescription(description);
        this.setUnitPrice(price);
        this.setUnitsInStore(units);
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
        if(description == null) {
            this.description = "";
        } else {
            this.description = description;
        }
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
        if(unitPrice < 0) {
            this.unitPrice = 0;
        } else if (unitPrice == Long.MAX_VALUE) {
            this.unitPrice = 0;
        } else {
            this.unitPrice = unitPrice;
        }
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
        if(unitsInStore < 0 || unitsInStore == Integer.MAX_VALUE) {
            this.unitsInStore = 0;
        } else {
            this.unitsInStore = unitsInStore;
        }
    }
}
