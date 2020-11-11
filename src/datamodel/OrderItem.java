package datamodel;

/**
 * @author Malte Janßen
 */

public class OrderItem {
    private String description;
    private Article article;
    private int unitsOrdered;

    protected OrderItem(String description, Article article, int units) {
        this.description = description;
        this.article = article;
        this.unitsOrdered = units;
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
     * @return the unitsOrdered
     */
    public int getUnitsOrdered() {
        return unitsOrdered;
    }

    /**
     * @param unitsOrdered the unitsOrdered to set
     */
    public void setUnitsOrdered(int unitsOrdered) {
        this.unitsOrdered = unitsOrdered;
    }

    /**
     * @return the article
     */
    public Article getArticle() {
        return article;
    }
}
