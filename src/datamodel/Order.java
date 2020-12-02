package datamodel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Malte Janßen
 */

public class Order {
    private final long id;
    private Date date;
    private Customer customer;
    private List<OrderItem> items;

    /**
     *
     * @param id       id of the order
     * @param date     date of the order
     * @param customer customer of the order
     */
    protected Order(long id, Date date, Customer customer) {
        this.id = id;

        if(date == null)
            this.date = new Date();
        else
            this.date = date;

        this.customer = customer;
        this.items = new ArrayList<>();
    }

    /**
     * @return the id
     */
    public long getId() {
        return id;
    }

    /**
     * @return the date
     */
    public Date getDate() {
        return date;
    }

    /**
     * @return the customer
     */
    public Customer getCustomer() {
        return customer;
    }

    /**
     * @return the items
     */
    public Iterable<OrderItem> getItems() {
        return items;
    }

    /**
     * @return the amount of items in the order
     */
    public int count() {
        return items.size();
    }

    /**
     * @param item the Item to be added
     * @return the order object
     */
    public Order addItem(OrderItem item) {
        if(item == null || items.contains(item))
            return this;

        this.items.add(item);
        return this;
    }

    /**
     * @param item the item to be removed
     * @return the order object
     */
    public Order removeItem(OrderItem item) {
        this.items.remove(item);
        return this;
    }

    /**
     * @return the order object
     */
    public Order clearItems() {
        this.items.clear();
        return this;
    }

    /**
     * Total price of the order
     * @return price of whole order
     */
//    public long getTotalPrice() {
//        return items.stream().mapToLong(OrderItem::getTotalPrice).sum();
//    }

//    public String toString() {
//        String str;
//        return "#" + getId() + ", " + getCustomer().getFirstName() + "'s Bestellung: " + itemsToString();
//    }
//
//    public String itemsToString() {
//        String[] itemStrings = new String[items.size()];
//        int i = 0;
//        for(OrderItem o : items) {
//            itemStrings[i++] = o.toString();
//        }
//        return String.join(", ",itemStrings);
//    }
}
