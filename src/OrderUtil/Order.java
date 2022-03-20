/**
 * Order.java
 * @author Meigan Lu
 * @author Eileen Huynh
 * @author Sol Valdimarsdottir
 * @author Sam Yadav
 * @author Brandon Ho
 * CIS 22C Course Project
 */
package OrderUtil;

import ProductUtil.Product;
import UserUtil.Customer;
import UserUtil.LinkedList;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Orders are made in the shopping cart, however
 * They are only given an orderID once they are purchased
 * Additionally, they will be given a date, and the priority will be calculated
 * Each time the program opens, previous orders in the cart will
 * need to be updated with a new priority value
 */

public class Order {

    private int orderID;
    private Customer customer;
    private String date; // should be the ordering date, no?
    private LinkedList<Product> orderContents; // fill in Product based on your
    // own product class
    private ShippingSpeed shippingSpeed; // or use enums //2, 1, 0 in that order
    private int priority;
    private static int count = 1000;
    private double price;

    /**
     * An enumerator for denoting the ShippingSpeed
     * in three categories:
     */
    public enum ShippingSpeed {
        OVERNIGHT,
        RUSH,
        STANDARD

    }

    /**
     * Default constructor for an Order()
     * Made for pre-purchasing purposes
     */
    public Order() {
        orderID = ++count;
        customer = null;
        price = 0;
        date = "";
        shippingSpeed = null;
        priority = -1;
        orderContents = new LinkedList<>();
    }

    /**
     * A constructor for an Order, for when Customer decides to purchase
     * a set of items
     * 
     * @param orderID
     * @param customer
     * @param shippingSpeed
     */
    public Order(int orderID, Customer customer, ShippingSpeed shippingSpeed) {
        this.orderID = orderID;
        this.customer = customer;
        this.date = LocalDate.now().toString();
        // orderContents = new LinkedList<>(o); // copies o onto the orderContents
        this.shippingSpeed = shippingSpeed;
        this.priority = calculatePriority(shippingSpeed);
        orderContents = new LinkedList<>();
    }

    /**
     * A one argument Customer constructor for order
     * 
     * @param customer
     */
    public Order(Customer customer) {
        this(-1, customer, null);
    }

    /**
     * A one argument orderID constructor for Order
     * 
     * @param orderID
     */
    public Order(int orderID) {
        this(orderID, null, null);
    }

    /**
     * Will remove one stock from a product after ordering
     */
    public void removeStock() {
        orderContents.positionIterator();
        while (!orderContents.offEnd()) {
            int quant = orderContents.getIterator().getQuantity();
            orderContents.getIterator().updateNumInStock(quant - 1);
            orderContents.advanceIterator();
        }
    }

    /**
     * Checks whether a specified product is in the orderedContents
     * 
     * @param p - the product checked for
     * @return boolean - whether the product is in
     */
    public boolean contains(Product p) {
        orderContents.positionIterator();
        while (!orderContents.offEnd()) {
            if (orderContents.getIterator().equals(p)) {
                return true;
            }
            orderContents.advanceIterator();
        }

        return false;
    }

    /** GETTERS/ACCESSORS */

    /**
     * 
     * @return the orderID
     */
    public int getOrderID() {
        return orderID;
    }

    /**
     * 
     * @return whether the orderedContents is empty
     */
    public boolean isEmpty() {
        return orderContents.isEmpty();
    }

    /** MUTATORS */
    /**
     * Modifies orderedContents with a new product at the end
     * 
     * @param p - the product to add to orderedContents
     */
    public void addProduct(Product p) {
        if (p == null) {
            System.out.println("BURHRURHRH");
        }
        orderContents.addLast(p);
    }

    /**
     * 
     * @return the customer user information
     */
    public Customer getCustomer() {
        return customer;
    }

    /**
     * 
     * @return the customer's first and last name
     */
    public String getCName() {
        String c = "" + this.getCustomer().getFirstName() + " " + this.getCustomer().getLastName();
        return c;
    }

    /**
     * 
     * @return a String of the date shipped
     */
    public String getDate() {
        return date;
    }

    /**
     * 
     * @return the ShippingSpeed enum denoting the type of shipping
     */
    public ShippingSpeed getShippingSpeed() {
        return shippingSpeed;
    }

    /**
     * Will return the priority of an item to be sorted in the Heap by minHeap
     * 
     * @return calculates the priority value as well as returns one
     */
    public int getPriority() {
        return priority;
    }

    /**
     * Changes the shipping speed from (usually) default to a new value
     * 
     * @param value - the ShippingSpeed value
     */
    public void changeShippingSpeed(ShippingSpeed value) {
        this.shippingSpeed = value;
    }

    /**
     * Will calculate and return the priority of an item to be sorted in the Heap by
     * minHeap after an order is submitted to be purchased
     * 
     * @return calculates the priority value as well as returns one
     */
    public int calculatePriority(ShippingSpeed shippingSpeed) {
        LocalTime lt = LocalTime.now();
        LocalDate ld = LocalDate.now();
        int hour = lt.getHour();
        int minute = lt.getMinute();
        int day = ld.getDayOfMonth();
        int month = ld.getMonthValue();

        // overnight - 1 day
        // rush - 3 day
        // standard - 5 day

        if (shippingSpeed == ShippingSpeed.OVERNIGHT) {
            day++;
        } else if (shippingSpeed == ShippingSpeed.RUSH) {
            day += 3;
        } else {
            day += 5;
        }

        return day + hour + minute + month;

    }

    /**
     * Gets the price of the order
     * 
     * @return the order's products' price
     */
    public double getPrice() {
        orderContents.positionIterator();
        while (!orderContents.offEnd()) {
            price += orderContents.getIterator().getPrice() * orderContents.getIterator().getQuantity();
            orderContents.advanceIterator();
        }

        if (shippingSpeed == ShippingSpeed.OVERNIGHT) {
            price += 0.07;
        } else if (shippingSpeed == ShippingSpeed.RUSH) {
            price += 0.04;
        } else {
            price += 0.01; // we r better than amazon and jeff bezos probably
        }

        return price;
    }

    /**
     * 
     * @return Returns the order's information with Name, Prices, and Quanities each
     *         by Product
     */
    public String productString() {
        String res = "";
        orderContents.positionIterator();
        while (!orderContents.offEnd()) {
            res += String.format("\n\tName: %s\n\tPrice: %.2f\n\tQuantity: %d", orderContents.getIterator().getName(),
                    orderContents.getIterator().getPrice() * orderContents.getIterator().getQuantity(),
                    orderContents.getIterator().getQuantity());
            orderContents.advanceIterator();
        }

        return res + "\n";
    }

    /**
     * Returns a toString of the product with Date Ordered, Product, ShippingSpeed,
     * and Priority information
     */
    @Override
    public String toString() {
        String sShippingSpeed = "n/a";
        if (shippingSpeed == null) {
            sShippingSpeed = "n/a";
        } else if (shippingSpeed == ShippingSpeed.OVERNIGHT) {
            sShippingSpeed = "Overnight Shipping";
        } else if (shippingSpeed == ShippingSpeed.RUSH) {
            sShippingSpeed = "Rush Shipping";
        } else if (shippingSpeed == ShippingSpeed.STANDARD) {
            sShippingSpeed = "Standard Shipping";
        }

        return "Order ID: " + orderID + "\n" +
        // "Customer: " + customer.getFirstName() + " " + customer.getLastName() + "\n"
        // +
                "Date Ordered: " + date + "\n" +
                "Shipping Speed: " + sShippingSpeed + "\n" +
                "Priority: " + priority
                + orderContents.toString();
    }

    /**
     * An equals compare for the Order class
     * 
     * @param obj - object to be compared
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Order other = (Order) obj;
        if (customer == null) {
            if (other.customer != null)
                return false;
        } else if (!customer.equals(other.customer))
            return false;
        if (date == null) {
            if (other.date != null)
                return false;
        } else if (!date.equals(other.date))
            return false;
        if (orderContents == null) {
            if (other.orderContents != null)
                return false;
        } else if (!orderContents.equals(other.orderContents))
            return false;
        if (orderID != other.orderID)
            return false;
        if (Double.doubleToLongBits(price) != Double.doubleToLongBits(other.price))
            return false;
        if (priority != other.priority)
            return false;
        if (shippingSpeed != other.shippingSpeed)
            return false;
        return true;
    }

}
