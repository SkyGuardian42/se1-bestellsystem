package system;

import datamodel.Order;
import datamodel.OrderItem;

import java.util.function.Consumer;

class OrderProcessor implements Components.OrderProcessor {
    private final InventoryManager inventoryManager;

    public OrderProcessor(InventoryManager inventoryManager) {
        this.inventoryManager = inventoryManager;
    }

    /**
     * Validates order against available inventory. An order can only be
     * accepted when all(!) its OrderItems can be fulfilled. If an order
     * is accepted (and only then), inventory is reduced by ordered items.
     * If order is not accepted, inventory remains unchanged.
     *
     * @param order order to accept
     * @return true it order is accepted
     */
    @Override
    public boolean accept(Order order) {
        return false;
    }

    /**
     * Refined variation of accept( order ) method that allows to pass code
     * via Java 8's Functional Interfaces that executes in cases the order
     * is accepted or rejected and when specific order items are rejected.
     *
     * @param order                 order to accept
     * @param acceptCode            Functional Interface invoked when order is accepted
     * @param rejectCode            Functional Interface invoked when order is rejected
     * @param rejectedOrderItemCode invoked for each rejected order item
     * @return
     */
    @Override
    public boolean accept(Order order, Consumer<Order> acceptCode, Consumer<Order> rejectCode, Consumer<OrderItem> rejectedOrderItemCode) {
        return false;
    }

    /**
     * Calculate order value as sum of value of all order items.
     *
     * @param order order to calculate
     * @return value of order in cents (as long)
     */
    @Override
    public long orderValue(Order order) {
        return 0;
    }

    /**
     * Calculate the Value-Added Tax (VAT) included in a gross value.
     * E.g. at a 19% VAT tax rate in Germany, a gross value of 49,84 EUR
     * includes 7,96 EUR VAT.
     *
     * @param grossValue value of which included VAT is calculated
     * @return included VAT
     */
    @Override
    public long vat(long grossValue) {
        return vat(grossValue, 1);
    }

    /**
     * Refined variation to calculate included VAT with different VAT tax
     * rates auch as 1: 19%, 2: 7%.
     *
     * @param grossValue value of which included VAT is calculated
     * @param rateIndex  VAT tax rate according as index: 1=19%, 2=7%
     * @return included VAT
     */
    @Override
    public long vat(long grossValue, int rateIndex) {
        double taxRate = 0;

        switch(rateIndex) {
            case 1:
                taxRate = 0.19;
                break;
            case 2:
                taxRate = 0.07;
        }

        return Math.round(grossValue * taxRate);
    }
}
