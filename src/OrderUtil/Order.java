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
    // private LinkedList<Product> orderContents; // fill in Product based on your
    // own product class
    private int shippingSpeed; // or use enums //2, 1, 0 in that order
    private int priority;
    private static int count = 1000;

    // Constructor

    public Order() {
        orderID = ++count;
        customer = null;
        customerLogin = "";
        date = "";
        shippingSpeed = -1;
        priority = -1;
    }

    // orderContents
    public Order(int orderID, Customer customer, String date, int shippingSpeed) {
        orderID = this.orderID;
        customer = this.customer;
        customerLogin = customer.getLogin();
        date = LocalDate.now().toString();
        // orderContents = new LinkedList<>(o); // copies o onto the orderContents
        shippingSpeed = this.shippingSpeed;
        priority = this.calculatePriority(shippingSpeed);
    }

    /** GETTERS/ACCESSORS */
    public int getOrderID() {
        return orderID;
    }

    public Customer getCustomer() {
        return customer;
    }

    public String getCName() {
        String c = "" + this.getCustomer().getFirstName() + " " + this.getCustomer().getLastName();
        return c;
    }

    public String getDate() {
        return date;
    }

    // public LinkedList<Product> getOrderContents() {
    // return orderContents;
    // }

    public int getShippingSpeed() {
        return shippingSpeed;
    }

    public int getPriority() {
        return priority;
    }

    /** MUTATOR FOR PRIORITY */
    public int calculatePriority(int shippingSpeed) {
        LocalTime lt = LocalTime.now();
        LocalDate ld = LocalDate.now();
        int hour = lt.getHour();
        int minute = lt.getMinute();
        int day = ld.getDayOfMonth();
        int month = ld.getMonthValue();

        // overnight - 1 day
        // rush - 3 day
        // standard - 5 day

        if (shippingSpeed == 1) {
            day++;
        } else if (shippingSpeed == 2) {
            day += 3;
        } else {
            day += 5;
        }

        return day + hour + minute + month;

    }

    public static Order getOrder(String customerLogin) {
        Order o = new Order();
        return null;
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
                "Shipping Speed: " + sShippingSpeed + "\n" +
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
                    getDate().equalsIgnoreCase(p.getDate()) && (this.getShippingSpeed() == p.getShippingSpeed())
                    && this.getPriority() == p.getPriority();
        }
    }
}
