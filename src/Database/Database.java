/**
 * Database.java
 * @author Meigan Lu
 * @author Eileen Huynh
 * @author Sol Valdimarsdottir
 * @author Sam Yadav
 * @author Brandon Ho
 * CIS 22C Course Project
 */
package Database;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;

import OrderUtil.Heap;
import OrderUtil.Order;
import OrderUtil.Order.ShippingSpeed;
import ProductUtil.BST;
import ProductUtil.Product;
import UserUtil.Customer;
import UserUtil.Employee;
import UserUtil.HashTable;
import UserUtil.LinkedList;
import UserUtil.User;

/**
 * Class with storage capabilities
 */
public class Database {
    private static HashTable<Customer> customerDatabase = new HashTable<>(100);
    private static HashTable<Employee> employeeDatabase = new HashTable<>(100);
    private static HashTable<String> usernames = new HashTable<>(100);
    private static HashTable<String> credentials = new HashTable<>(100);
    private static BST<Product> itemsName = new BST<>();
    private static BST<Product> itemsType = new BST<>();
    private static Heap<Order> orders = new Heap<>(100);

    public static User loggedIn;

    /**
     * <ul>
     * <li>Customer
     * <li>Employee
     * </ul>
     */
    public enum UserType {
        Customer,
        Employee,
        Manager
    }

    /**
     * <ul>
     * <li>Failed - Attempt to add user with duplicate username
     * <li>Success - Successfully performed the operation,
     * <li>Error - Something went wrong
     * </ul>
     */
    public enum Status {
        Failed,
        Success,
        Error
    }

    /**
     * Adds a user to the proper database after validating and
     * casting to the appropriate type - Customer or Employee
     * 
     * @precondition Username must be unique
     * @param user User to add to Database
     * @return Status enum - success, fail, or error
     */
    public static Status addUser(Object user) {
        if (!(user instanceof User)) {
            return Status.Failed;
        }

        User u = (User) user;

        if (Database.usernames.contains(u.getLogin())) {
            return Status.Failed;
        }

        if ((u instanceof Customer)) {
            Database.customerDatabase.add((Customer) u);
            addCustomer((Customer) u);
        } else if (u instanceof Employee) {
            Database.employeeDatabase.add((Employee) u);
            addEmployee((Employee) u);
        } else {
            return Status.Error;
        }

        Database.usernames.add(u.getLogin());
        Database.credentials.add(u.getLogin() + u.getPassword());

        return Status.Success;
    }

    /**
     * Checks whether a user can be added with a
     * specific username
     * 
     * @param username
     * @return whether username is not a duplicate
     */
    public static boolean validateUsername(String username) {
        return !(Database.usernames.contains(username));
    }

    /**
     * Retrieves the proper user object after verifying the
     * credentials passed and stores in loggedIn user variable.
     * 
     * @param ut UserType enum to clarify Customer or Employee login
     * @return Status of operation
     */
    public static Status login(String username, String password) {
        if (!Database.credentials.contains(username + password)) {
            return Status.Failed;
        }

        if (customerDatabase.contains(new Customer(username, password))) {
            loggedIn = Database.customerDatabase.get(new Customer(username, password));
        } else if (employeeDatabase.contains(new Employee(username, password))) {
            loggedIn = Database.employeeDatabase.get(new Employee(username, password));
        } else {
            return Status.Failed;
        }

        return Status.Success;
    }

    /**
     * Populates the database on startup
     */
    public static void startUp() {
        populateCatalogue();
        populateOrders();

        populateCustomers();
        populateEmployees();
    }

    private static void populateOrders() {

        orders.insert(new Order(10012, new Customer("firstName", "lastName", "login", "password", "address",
                "city", "state",
                "2222"), ShippingSpeed.OVERNIGHT), new PriorityComparator());
    }

    /**
     * Populates the customer hashtables with the
     * information from customer.txt
     *
     */
    private static void populateCustomers() {
        try {
            BufferedReader f = new BufferedReader(
                    new FileReader("./src/Database/Customer.txt"));
            String line = f.readLine();
            while (line != null) {
                String[] data = new String[8];
                for (int i = 0; i < 8; i++) {
                    data[i] = line;
                    line = f.readLine();
                }
                Customer cust = new Customer(data);

                // TODO: add to order class
                // Order temp = Database.orderDatabase.findOrder(cust.getLogin());
                // if (temp.isShipped()) {
                // cust.addShippedOrders(temp);
                // } else if (!temp.isDelivered) {
                // cust.addUnshippedOrders(temp);
                // }

                Database.customerDatabase.add(cust);
                Database.usernames.add(data[2]);
                Database.credentials.add(data[2] + data[3]);
            }
            f.close();
        } catch (IOException e) {
            // TODO
            System.out.println(e);
        }
    }

    // /**
    // * Creates 2-way connection between order objects and
    // * customer objects on startup
    // */
    // private static void addOrderConnection(Customer c) {
    // c
    // }

    /**
     * Creates a new entry in the file when
     * a customer is added
     * 
     * @param c Customer to add
     */
    private static void addCustomer(Customer c) {
        try {
            BufferedWriter f = new BufferedWriter(
                    new FileWriter("./src/Database/Customer.txt", true));
            f.write('\n' + c.toString());
            f.close();
        } catch (IOException e) {
            // TODO

        }
    }

    /**
     * Populates the employee hashtables from the
     * data in the employee.txt file
     */
    private static void populateEmployees() {
        try {
            BufferedReader f = new BufferedReader(
                    new FileReader("./src/Database/Employee.txt"));
            String line = f.readLine();
            while (line != null) {
                String[] data = new String[5];
                for (int i = 0; i < 5; i++) {
                    data[i] = line;
                    line = f.readLine();
                }
                Database.employeeDatabase.add(new Employee(data));
                Database.usernames.add(data[2]);
                Database.credentials.add(data[2] + data[3]);
            }
            f.close();
        } catch (IOException e) {
            // TODO
            System.out.println(e);
        }
    }

    /**
     * Creates a new entry in the file when
     * a employee is added
     * 
     * @param emp Employee to add
     */
    private static void addEmployee(Employee emp) {
        try {
            BufferedWriter f = new BufferedWriter(
                    new FileWriter("./src/Database/Employee.txt", true));
            f.write('\n' + emp.toString());
            f.close();
        } catch (IOException e) {
            // TODO
        }
    }

    /**
     * populate the store with products from Catalogue
     * 
     * @param input Scanner
     * 
     */
    private static void populateCatalogue() {
        try {
            Scanner input = new Scanner(new File("\\src\\Database\\Catalogue.txt"));
            String name = "";
            String type = "";
            int calories = 0;
            String bestby = "";
            double price = 0.0;
            String description = "";
            int numInStock = 0;

            while (input.hasNextLine()) {
                name = input.nextLine();
                type = input.nextLine();
                calories = input.nextInt();
                input.nextLine();
                bestby = input.nextLine();
                price = input.nextDouble();
                input.nextLine();
                description = input.nextLine();
                numInStock = input.nextInt();

                if (input.hasNextLine()) {
                    input.nextLine();
                    input.nextLine();
                }

                Product p = new Product(name, type, calories, bestby, price, description,
                        numInStock);
                itemsName.insert(p, new NameComparator());
                itemsType.insert(p, new TypeComparator());

            }
        } catch (IOException e) {
            // TODO: handle exception
        }
    }

    /**
     * Returns the type of user given a username
     * and password
     * 
     * @return Type of account, if exists
     */
    public static UserType getPosition(String username, String password) {
        if (!usernames.contains(username)) {
            return null;
        }
        if (customerDatabase.contains(new Customer(username, password))) {
            return UserType.Customer;
        } else {
            Employee emp = employeeDatabase.get(new Employee(username, password));
            if (emp.isManager()) {
                return UserType.Manager;
            }
            return UserType.Employee;
        }

    }

    /**
     * Displays all products stored in the BST
     * sorted by primary key name
     */
    public static void displaybyName() {
        System.out.println("Products sorted by Name: " + itemsName.inOrderString());
    }

    /**
     * Displays all products stored in the BST
     * sorted by secondary key type
     */
    public static void displaybyType() {
        System.out.println("Products sorted by Type: " + itemsType.inOrderString());
    }

    /**
     * Search for a product by primary key name
     * 
     * @param name of product to search
     * @return Product found or null
     */
    public static Product searchName(String name) {
        Product n = new Product(name, "");
        return itemsName.search(n, new NameComparator());
    }

    /**
     * Search for a product by secondary key type
     * 
     * @param type the type of the product
     * @return the Product found or null
     */
    public static Product searchType(String type) {
        Product t = new Product("", type);
        return itemsType.search(t, new NameComparator());
    }

    public static Order viewOrderHighestPri() {
        return orders.getMin();
    }

    public static String viewingOrdersbyHighestPri() {
        return orders.toString();
    }

    public static Order searchID(int orderID) {
        Order o = new Order(orderID);
        return orders.search(o, new IDComparator());
    }

    /**
     * Inserts an order into the order heap
     * 
     * @param o
     */
    public static void addOrder(Order o) {
        orders.insert(o, new PriorityComparator());
    }

    /*
     * ﾊ,,ﾊ
     * （ ﾟωﾟ ) No Thank You
     * ／ ＼
     * ((⊂ ） ﾉ＼つ))
     * （＿⌒ヽ
     * ヽ ﾍ }
     * 
     * ε≡Ξ ﾉノ ｀J
     */
    // line 501

    /**
     * Ships an order from orderID
     * and removes from order heap
     * 
     * @param orderId
     */
    public static void shipOrder(int orderId) {
        Order o = orders.search(new Order(orderId), new IDComparator());

        o.getCustomer().shipOrder(orderId);
        orders.remove(orders.getIndex(new Order(orderId), new IDComparator()), new IDComparator());
    }

    /**
     * Adds the shopping cart to the heap,
     * resets it, and puts it into
     * Customer's unshipped orders
     * 
     */
    public static void placeOrder(Order o, Customer c) {
        orders.insert(o, new PriorityComparator());
        c.addUnshippedOrders(o);
    }

    /**
     * 
     * @param customer
     * @return
     */
    public static ArrayList<LinkedList<Order>> searchOrderCust(Customer customer) {
        Order o = orders.search(new Order(customer), new CustomerComparator());

        if (o != null) {
            ArrayList<LinkedList<Order>> arr = new ArrayList<>();
            arr.add(o.getCustomer().getUnshippedOrders());
            arr.add(o.getCustomer().getShippedOrders());
            return arr;
        }
        return null;
    }

}

/**
 * Comparator for BST class - Products sorted by primary key
 */
class NameComparator implements Comparator<Product> {
    /**
     * Compares the primary keys (name) of two Product objects
     * 
     * @param p1 first product to compare
     * @param p2 second prodcut to compare
     * @return an int based on if p1 is >, <, or = to p2
     */
    @Override
    public int compare(Product p1, Product p2) {
        if (p1.equals(p2)) {
            return 0;
        }
        return p1.getName().compareTo(p2.getName());
    }
}

/**
 * Comparator for BST class - Products sorted by secondary key
 */
class TypeComparator implements Comparator<Product> {
    /**
     * Compares the secondary keys (type) of two Product objects
     * 
     * @param p1 first product to compare
     * @param p2 second prodcut to compare
     * @return an int based on if p1 is >, <, or = to p2
     */
    @Override
    public int compare(Product p1, Product p2) {
        if (p1.equals(p2)) {
            return 0;
        }
        return p1.getType().compareTo(p2.getType());
    }
}

/**
 * Comparator for Heap class
 */
class PriorityComparator implements Comparator<Order> {
    @Override
    public int compare(Order p1, Order p2) {
        if (p1.equals(p2)) {
            return 0;
        }
        return Integer.compare(p1.getPriority(), (p2.getPriority()));
    }
}

/**
 * Comparator for Heap class
 */
class CustomerComparator implements Comparator<Order> {

    @Override
    public int compare(Order o1, Order o2) {
        if (o1.equals(o2)) {
            return 0;
        }
        return o1.getCName().compareTo(o2.getCName());
    }
}

/**
 * Comparator for Heap class
 */
class IDComparator implements Comparator<Order> {

    @Override
    public int compare(Order o1, Order o2) {
        if (o1.equals(o2)) {
            return 0;
        }
        return Integer.compare(o1.getOrderID(), o2.getOrderID());
    }
}
