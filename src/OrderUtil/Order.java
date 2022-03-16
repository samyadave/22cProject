package OrderUtil;

import java.util.ArrayList;
import java.util.Comparator;

import ProductUtil.Product;
import UserUtil.Customer;
import UserUtil.LinkedList;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;

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
    private String customerLogin;
    private String date; // should be the ordering date, no?
    private LinkedList<Product> orderContents; // fill in Product based on your own product class
    private int shippingSpeed; // or use enums //2, 1, 0 in that order
    private int priority;
    private static int count = 1000;

    // Constructor

    public Order() {
        orderID = ++count;
        customer = null;
        date = "";
        orderContents = new LinkedList<>();
        shippingSpeed = -1;
        priority = -1;
        // priority = -1; for now, not really sure. could be a value we'd have to
        // calculate instead
    }

    // orderContents
    public Order(int orderID, Customer customer, String date, LinkedList<Product> o, int shippingSpeed) {
        orderID = this.orderID; // should this be randomized? in this class or in other? should be around 10
        customer = this.customer;
        date = this.date;
        orderContents = new LinkedList<>(o); // copies o onto the orderContents
        shippingSpeed = this.shippingSpeed;
        priority = -1;
    }

    /** GETTERS/ACCESSORS */
    public int getOrderID() {
        return orderID;
    }

    public Customer getCustomer() {
        return customer;
    }

    public String getDate() {
        return date;
    }

    public LinkedList<Product> getOrderContents() {
        return orderContents;
    }

    public int getShippingSpeed() {
        return shippingSpeed;
    }

    public int getPriority() {
        return priority;
    }

    /** MUTATOR FOR PRIORITY */
    public void calculatePriority(int shippingSpeed) { // what do we think about updating the priority values constantly
                                                       // based on local time?
        LocalTime lt = LocalTime.now();

        int shippingSpeedInt;
        int dateInt;
        int timeInt;
        int total;
        shippingSpeedInt = (shippingSpeed + 1) * 2;
        // this.date.

        // total = shippingSpeedInt + dateInt + timeInt;

    }

    /** To be called on whenever refreshing an order */
    public void calculateDate() {
        LocalDate lt = LocalDate.now();
        this.date = lt.toString();
    }

    @Override
    public String toString() {
        String sShippingSpeed;
        if (shippingSpeed == 2) {
            sShippingSpeed = "Overnight Shipping";
        } else if (shippingSpeed == 1) {
            sShippingSpeed = "Rush Shipping";
        } else if (shippingSpeed == 0) {
            sShippingSpeed = "Standard Shipping";
        } else {
            sShippingSpeed = "n/a";
        }

        return "Order ID: " + orderID + "\n" +
                "Customer: " + customer.getFirstName() + " " + customer.getLastName() + "\n" +
                "Date Ordered: " + date + "\n" +
                "OrderedContents" + orderContents.toString() + "\n"
                + "Shipping Speed: " + sShippingSpeed + "\n" +
                "Priority: " + priority;

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true; // compares memory address
        } else if (!(o instanceof Order)) {
            return false; // makes sure comparing 2 of same type
        } else {
            Order p = (Order) o;
            return this.getOrderID() == p.getOrderID() && this.getCustomer().equals(p.getCustomer()) &&
                    getDate().equalsIgnoreCase(p.getDate()) && this.getOrderContents().equals(p.getOrderContents())
                    && (this.getShippingSpeed() == p.getShippingSpeed()) && this.getPriority() == p.getPriority();
        }
    }
}
