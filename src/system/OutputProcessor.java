package system;

import datamodel.Customer;
import datamodel.Order;
import datamodel.OrderItem;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

class OutputProcessor implements Components.OutputProcessor {
    private final InventoryManager inventoryManager;
    private final OrderProcessor orderProcessor;
    private static final int printLineWidth = 95;

    public OutputProcessor(InventoryManager inventoryManager, OrderProcessor orderProcessor) {
        this.inventoryManager = inventoryManager;
        this.orderProcessor = orderProcessor;
    }

    /**
     * Print orders to System.out in format (example):
     * <p>
     * #5234968294, Eric's Bestellung: 1x Kanne                           20,00 EUR
     * #8592356245, Eric's Bestellung: 4x Teller, 8x Becher, 4x Tassen    49,84 EUR
     * #3563561357, Anne's Bestellung: 1x Kanne aus Porzellan             20,00 EUR
     * #6135735635, Nadine Ulla's Bestellung: 12x Teller blau/weiss Ker.. 77,88 EUR
     * #4835735356, Timo's Bestellung: 1x Kaffeemaschine, 6x Tasse        47,93 EUR
     * #6399437335, Sandra's Bestellung: 1x Teekocher, 4x Becher, 4x Te.. 51,91 EUR
     * -------------                                    ------------- -------------
     * Gesamtwert aller Bestellungen:                                    267,56 EUR
     * <p>
     * |<----------------------------<printLineWidth>----------------------------->|
     *
     * @param orders   list of orders to print
     * @param printVAT print included VAT at the end of each line item
     */
    @Override
    public void printOrders(List<Order> orders, boolean printVAT) {
        StringBuffer sbAllOrders = new StringBuffer("-------------\n");
        StringBuffer sbLineItem = new StringBuffer();

        long totalPrice = 0;

        // add all orders
        for(Order order : orders) {

            // Generate customer name
            Customer customer = order.getCustomer();
            String customerName = splitName( customer, customer.getFirstName() + " " + customer.getLastName() );

            // Order price
            long orderTotal = 0;

            // Get String of items
            Iterable<OrderItem> items = order.getItems();
            ArrayList<String> itemStrings = new ArrayList<>();

            for(OrderItem item: items) {
                itemStrings.add(item.getUnitsOrdered() + "x " + item.getDescription());

                // Add item price to orderTotal
                orderTotal += item.getUnitsOrdered() * item.getArticle().getUnitPrice();
            }

            String itemsAsString =  String.join(", ",itemStrings);

            // Generate Order string
            String orderString = "#" + order.getId() + ", " + customerName + "'s Bestellung: " + itemsAsString;

            // Add order price to total
            totalPrice += orderTotal;

            sbLineItem= fmtLine(orderString, fmtPrice(orderTotal, "EUR", 14), printLineWidth);
            sbLineItem.append("\n");
            sbAllOrders.append(sbLineItem);
        }

        // calculate total price
        String fmtPriceTotal = pad(fmtPrice(totalPrice, "", " EUR"), 14, true);

        // append final line with totals
        sbAllOrders
                .append(fmtLine("-------------", "‐‐‐‐‐‐‐‐‐‐‐‐‐", printLineWidth))
                .append("\n").
                append(fmtLine("Gesamtwert aller Bestellungen:", fmtPriceTotal, printLineWidth));

        // print sbAllOrders StringBuffer with all output to System.out
        System.out.println(sbAllOrders.toString());
    }

    /**
     * Print available inventory.
     */
    @Override
    public void printInventory() {

    }

    /**
     * Format long-price in 1/100 (cents) to String using DecimalFormatter, add
     * currency and pad to minimum width right-aligned.
     * For example, 299, "EUR", 12 -> "    2,99 EUR"
     *
     * @param price    price as long in 1/100 (cents)
     * @param currency currency as String, e.g. "EUR"
     * @return price as String with currency and padded to minimum width
     */
    @Override
    public String fmtPrice(long price, String currency) {
        String fmtPrice = pad(fmtPrice(price, "", " " + currency), 14, true);
        return fmtPrice;
    }

    /**
     * Format long-price in 1/100 (cents) to String using DecimalFormatter, add
     * currency and pad to minimum width right-aligned.
     * For example, 299, "EUR", 12 -> "    2,99 EUR"
     *
     * @param price    price as long in 1/100 (cents)
     * @param currency currency as String, e.g. "EUR"
     * @param width    minimum width to which result is padded
     * @return price as String with currency and padded to minimum width
     */
    @Override
    public String fmtPrice(long price, String currency, int width) {
        String fmtPrice = pad(fmtPrice(price, "", " " + currency), 14, true);
        return fmtPrice;
    }

    /**
     * Format long-price in 1/100 (cents) to String using DecimalFormatter and
     * prepend prefix and append postfix String. For example, 299, "(", ")" ->
     * "(2,99)"
     *
     * @param price   price as long in 1/100 (cents)
     * @param prefix  String to prepend before price
     * @param postfix String to append after price
     * @return price as String
     */
    private String fmtPrice(long price, String prefix, String postfix) {
        StringBuffer fmtPriceSB = new StringBuffer();
        if (prefix != null) {
            fmtPriceSB.append(prefix);
        }

        fmtPriceSB = fmtPrice(fmtPriceSB, price);

        if (postfix != null) {
            fmtPriceSB.append(postfix);
        }
        return fmtPriceSB.toString();
    }

    /**
     * Format long-price in 1/100 (cents) to String using DecimalFormatter. For
     * example, 299 -> "2,99"
     *
     * @param sb    StringBuffer to which price is added
     * @param price price as long in 1/100 (cents)
     * @return StringBuffer with added price
     */
    private StringBuffer fmtPrice(StringBuffer sb, long price) {
        if (sb == null) {
            sb = new StringBuffer();
        }
        double dblPrice = ((double) price) / 100.0; // convert cent to Euro
        DecimalFormat df = new DecimalFormat("#,##0.00", new DecimalFormatSymbols(new Locale("de"))); // rounds double
        // to 2 digits

        String fmtPrice = df.format(dblPrice); // convert result to String in DecimalFormat
        sb.append(fmtPrice);
        return sb;
    }

    /**
     * Format line to a left-aligned part followed by a right-aligned part padded to
     * a minimum width.
     * For example:
     * <p>
     * <left-aligned part>                          <>       <right-aligned part>
     * <p>
     * "#5234968294, Eric's Bestellung: 1x Kanne         20,00 EUR   (3,19 MwSt)"
     * <p>
     * |<-------------------------------<width>--------------------------------->|
     *
     * @param leftStr  left-aligned String
     * @param rightStr right-aligned String
     * @param width    minimum width to which result is padded
     * @return String with left- and right-aligned parts padded to minimum width
     */
    @Override
    public StringBuffer fmtLine(String leftStr, String rightStr, int width) {
        StringBuffer sb = new StringBuffer(leftStr);
        int shiftable = 0; // leading spaces before first digit
        for (int i = 1; rightStr.charAt(i) == ' ' && i < rightStr.length(); i++) {
            shiftable++;
        }
        final int tab1 = width - rightStr.length() + 1; // - ( separator? 3 : 0 );
        int sbLen = sb.length();
        int excess = sbLen - tab1 + 1;
        int shift2 = excess - Math.max(0, excess - shiftable);
        if (shift2 > 0) {
            rightStr = rightStr.substring(shift2, rightStr.length());
            excess -= shift2;
        }
        if (excess > 0) {
            switch (excess) {
                case 1:
                    sb.delete(sbLen - excess, sbLen);
                    break;
                case 2:
                    sb.delete(sbLen - excess - 2, sbLen);
                    sb.append("..");
                    break;
                default:
                    sb.delete(sbLen - excess - 3, sbLen);
                    sb.append("...");
                    break;
            }
        }
        String strLineItem = String.format("%-" + (tab1 - 1) + "s%s", sb.toString(), rightStr);
        sb.setLength(0);
        sb.append(strLineItem);
        return sb;
    }

    /**
     * Split single-String name to first- and last name and set to the customer object,
     * e.g. single-String "Eric Meyer" is split into "Eric" and "Meyer".
     *
     * @param customer object for which first- and lastName are set
     * @param name     single-String name that is split into first- and last name
     * @return returns single-String name extracted from customer object
     */
    @Override
    public String splitName(Customer customer, String name) {
        String sanName = name.trim();

        // Handle name seperated by comma. If true,last name is first
        boolean commaSeperated = sanName.contains(",");
        String[] seperatedNames = sanName.split("[, ]+");

        String lastName = "";
        String firstName = "";

        if(commaSeperated) {
            lastName = seperatedNames[0];

            for(int i = 1; i<seperatedNames.length; i++) {
                firstName += seperatedNames[i] + " ";
            }
        } else {
            lastName = seperatedNames[seperatedNames.length - 1];

            for(int i = 0; i<seperatedNames.length - 1; i++) {
                firstName += seperatedNames[i] + " ";
            }
        }

        firstName = firstName.trim();

        customer.setFirstName(firstName);
        customer.setLastName(lastName);

        return singleName(customer);
    }

    /**
     * Returns single-String name obtained from first- and lastName attributes of
     * Customer object, e.g. "Eric", "Meyer" returns single-String "Meyer, Eric".
     *
     * @param customer object referred to
     * @return name derived from first- and lastName attributes
     */
    @Override
    public String singleName(Customer customer) {
        return customer.getLastName() + ", " + customer.getFirstName();
    }

    /**
     * Pad string to minimum width, either right-aligned or left-aligned
     *
     * @param str          String to pad
     * @param width        minimum width to which result is padded
     * @param rightAligned flag to chose left- or right-alignment
     * @return padded String
     */
    private String pad(String str, int width, boolean rightAligned) {
        String fmtter = (rightAligned ? "%" : "%-") + width + "s";
        String padded = String.format(fmtter, str);
        return padded;
    }
}
