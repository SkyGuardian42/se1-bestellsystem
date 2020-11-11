package system;

import datamodel.Article;

import java.util.Optional;

class InventoryManager implements Components.InventoryManager {
    /**
     * Indicate whether inventory contains article with given id.
     *
     * @param id article id
     * @return true, if article with id is in inventory
     */
    @Override
    public boolean containsArticle(String id) {
        return false;
    }

    /**
     * Return inventory as iterable of Articles (iterable to prevent list manipulation from outside)
     *
     * @return article as iterable
     */
    @Override
    public Iterable<Article> getInventory() {
        return null;
    }

    /**
     * Return article from inventory by its id. Returns Optional.
     *
     * @param id article id
     * @return article as Optional
     */
    @Override
    public Optional<Article> get(String id) {
        return Optional.empty();
    }

    /**
     * Return the number of articles in inventory.
     *
     * @return number of OrderItems
     */
    @Override
    public long count() {
        return 0;
    }

    /**
     * Add Article to inventory, if article.id is not already present.
     *
     * @param article article added, if article.id is not already present
     * @return self-reference to allow method chaining
     */
    @Override
    public Components.InventoryManager add(Article article) {
        return null;
    }

    /**
     * Remove Article from inventory.
     *
     * @param article article to remove
     * @return self-reference to allow method chaining
     */
    @Override
    public Components.InventoryManager remove(Article article) {
        return null;
    }

    /**
     * Clear all articles from inventory.
     *
     * @return self-reference to allow method chaining
     */
    @Override
    public void clear() {

    }
}
