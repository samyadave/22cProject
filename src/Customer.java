
public class Customer extends User {
    // note user is an abstract class containing first name, last name, login, and
    // password
    private String address;
    private String city;
    private String state;
    private String zip;
    private LinkedList<Order> shippedOrders;
    private LinkedList<Order> unshippedOrders;

    // getters, setters, constructors go here

    /**
     * 
     * @param address
     * @param city
     * @param state
     * @param zip
     */
    public Customer(String address, String city, String state, String zip) {
        this.address = address;
        this.city = city;
        this.state = state;
        this.zip = zip;
        this.shippedOrders = new LinkedList<>();
        this.unshippedOrders = new LinkedList<>();
    }

    /**
     * 
     * @return
     */
    public String getAddress() {
        return address;
    }

    /**
     * 
     * @param address
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * 
     * @return
     */
    public String getCity() {
        return city;
    }

    /**
     * 
     * @param address
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * 
     * @return
     */
    public String getState() {
        return state;
    }

    /**
     * 
     * @param address
     */
    public void setState(String state) {
        this.state = state;
    }

    /**
     * 
     * @return
     */
    public String getZip() {
        return zip;
    }

    /**
     * 
     * @param address
     */
    public void setZip(String zip) {
        this.zip = zip;
    }

    /**
     * 
     * @return
     */
    public LinkedList<Order> getShippedOrders() {
        return shippedOrders;
    }

    /**
     * 
     * @param address
     */
    public void setShippedOrders(LinkedList<Order> shippedOrders) {
        this.shippedOrders = shippedOrders;
    }

    /**
     * 
     * @return
     */
    public LinkedList<Order> getUnshippedOrders() {
        return unshippedOrders;
    }

    /**
     * 
     * @param address
     */
    public void setUnshippedOrders(LinkedList<Order> unshippedOrders) {
        this.unshippedOrders = unshippedOrders;
    }

}
