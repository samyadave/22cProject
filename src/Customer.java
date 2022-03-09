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

    private static HashTable<String> customerCredentials;
    private static HashTable<Customer> customerDatabase;

    /**
     * 
     * @param firstName
     * @param lastName
     * @param login
     * @param password
     * @param address
     * @param city
     * @param state
     * @param zip
     * 
     */
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

    /**
     * 
     * @return address of Customer
     */
    public String getAddress() {
        return address;
    }

    /**
     * 
     * @param address address of Customer
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * 
     * @return city of Customer
     */
    public String getCity() {
        return city;
    }

    /**
     * 
     * @param city city of customer
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * 
     * @return state of customer
     */
    public String getState() {
        return state;
    }

    /**
     * 
     * @param address state of customer
     */
    public void setState(String state) {
        this.state = state;
    }

    /**
     * 
     * @return zip code of customer
     */
    public String getZip() {
        return zip;
    }

    /**
     * 
     * @param zip zip code of customer
     */
    public void setZip(String zip) {
        this.zip = zip;
    }

    /**
     * 
     * @return LinkedList containing shipped orders of the customer
     */
    public LinkedList<Order> getShippedOrders() {
        return shippedOrders;
    }

    /**
     * 
     * @param shippedOrder
     */
    public void addShippedOrders(Order shippedOrder) {
        this.shippedOrders.addLast(shippedOrder);

    }

    /**
     * 
     * @return LinkedList containing unshipped orders of the Customer
     */
    public LinkedList<Order> getUnshippedOrders() {
        return unshippedOrders;
    }

    /**
     * 
     * @param unshippedOrder
     */
    public void setUnshippedOrders(Order unshippedOrder) {
        this.unshippedOrders.addLast(unshippedOrder);
    }

    public static Customer login(String username, String password) {
        if (Customer.authenticateLogin(username, password)) {
            return customerDatabase.get(username + password);
        }

        return null;
    }

    static boolean authenticateLogin(String username, String password) {
        if (customerCredentials.contains(username + password)) {
            return true;
        }

        return false;
    }

}
