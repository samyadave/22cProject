
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

}
