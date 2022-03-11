package UserUtil;

import OrderUtil.Order;

/**
 * MegaMart
 * Customer Class - 22C Course Project
 */

public class Customer extends User {
    private String address;
    private String city;
    private String state;
    private String zip;
    private LinkedList<Order> shippedOrders;
    private LinkedList<Order> unshippedOrders;

    public Customer() {
        this.shippedOrders = new LinkedList<>();
        this.unshippedOrders = new LinkedList<>();
    }

    public Customer(String[] data) {
        super(data[0], data[1], data[2], data[3]);
        this.address = data[4];
        this.city = data[5];
        this.state = data[6];
        this.zip = data[7];
        this.shippedOrders = new LinkedList<>();
        this.unshippedOrders = new LinkedList<>();
    }

    /**
     * Constructor for fetching Customer account when attempting
     * a login
     * 
     * @param username
     * @param password
     */
    public Customer(String username, String password) {
        super(username, password);
    }

    public Customer(String firstName, String lastName, String login, String password, String address, String city,
            String state, String zip) {
        super(firstName, lastName, login, password);
        this.address = address;
        this.city = city;
        this.state = state;
        this.zip = zip;
        this.shippedOrders = new LinkedList<>();
        this.unshippedOrders = new LinkedList<>();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public LinkedList<Order> getShippedOrders() {
        return shippedOrders;
    }

    public void addShippedOrders(Order shippedOrder) {
        this.shippedOrders.addLast(shippedOrder);

    }

    public LinkedList<Order> getUnshippedOrders() {
        return unshippedOrders;
    }

    public void addUnshippedOrders(Order unshippedOrder) {
        this.unshippedOrders.addLast(unshippedOrder);
    }

    @Override
    public String toString() {
        return String.format("%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s", firstName, lastName, userName, password, address, city,
                state, zip);
    }

}