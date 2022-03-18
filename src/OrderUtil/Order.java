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

import org.junit.jupiter.params.shadow.com.univocity.parsers.conversions.ShortConversion;
import org.junit.platform.console.shadow.picocli.CommandLine.Help.Column.Overflow;

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
    private String date; // should be the ordering date, no?
    private LinkedList<Product> orderContents; // fill in Product based on your
    // own product class
    private ShippingSpeed shippingSpeed; // or use enums //2, 1, 0 in that order
    private int priority;
    private static int count = 1000;
    private double price;

    public enum ShippingSpeed {
        OVERNIGHT,
        RUSH,
        STANDARD

    }

    // Constructor

    public Order() {
        orderID = ++count;
        customer = null;
        price = 0;
        date = "";
        shippingSpeed = null;
        priority = -1;
        orderContents = new LinkedList<>();
    }

    // orderContents
    public Order(int orderID, Customer customer, String date, ShippingSpeed shippingSpeed) {
        orderID = this.orderID;
        customer = this.customer;
        date = LocalDate.now().toString();
        // orderContents = new LinkedList<>(o); // copies o onto the orderContents
        shippingSpeed = this.shippingSpeed;
        priority = this.calculatePriority(shippingSpeed);
        orderContents = new LinkedList<>();

    }

    /** GETTERS/ACCESSORS */
    public int getOrderID() {
        return orderID;
    }

    public void addProduct(Product p) {
        if (p == null) {
            System.out.println("BURHRURHRH");
        }
        orderContents.addLast(p);
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

    public ShippingSpeed getShippingSpeed() {
        return shippingSpeed;
    }

    public int getPriority() {
        return priority;
    }

    public void changeShippingSpeed(ShippingSpeed value) {
        this.shippingSpeed = value;
    }

    /** MUTATOR FOR PRIORITY */
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

    public double getPrice() {

        orderContents.positionIterator();
        while (!orderContents.offEnd()) {
            price += orderContents.getIterator().getPrice();
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
