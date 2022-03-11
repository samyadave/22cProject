package OrderUtil;

import java.util.Comparator;

import ProductUtil.Product;
import UserUtil.Customer;
import UserUtil.LinkedList;

public class Order {

    private int orderID;

    private Customer customer;

    private String date;

    private LinkedList<Product> orderContents; // fill in Product based on your own product class

    private int shippingSpeed; // or use enums

    private int priority;

    // getters, setters, constructors go here

}