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
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;

import OrderUtil.Heap;
import OrderUtil.Order;
import ProductUtil.Product;
import ProductUtil.ToString;
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
    private static Heap<Order> orders = new Heap<>(100);

    public static User loggedIn;

    /**
     * <ul>
     * <li>Customer
     * <li>Employee
     * <li>Manager
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
            loggedIn = Database.customerDatabase.find(new Customer(username, password));
        } else if (employeeDatabase.contains(new Employee(username, password))) {
            loggedIn = Database.employeeDatabase.find(new Employee(username, password));
        } else {
            return Status.Failed;
        }

        return Status.Success;
    }

    /**
     * Populates the database on startup
     */
    public static void startUp() {
        Product.populateCatalogue();
        populateCustomers();
        populateEmployees();
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

                Database.customerDatabase.add(cust);
                Database.usernames.add(data[2]);
                Database.credentials.add(data[2] + data[3]);
            }
            f.close();
        } catch (IOException e) {
            System.out.println(e);
        }
    }

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
            System.out.println(e);
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
            System.out.println(e);
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

    public static Order priorityOrder() {
        return orders.getMin();
    }

    public static String priorityOrdersStr() {
        return orders.toStr(new EmployeeOrderStr());
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
     * @param o
     */
    public static void shipOrder(Order o) {
        o.getCustomer().shipOrder(o.getOrderID());
        orders.remove(orders.getIndex(o, new IDComparator()), new IDComparator());
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
    public static LinkedList<Order> searchOrderCust(Customer customer) {
        Order o = orders.search(new Order(customer), new CustomerComparator());

        if (o != null) {
            return o.getCustomer().getUnshippedOrders();
        }
        return null;
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
        return p1.getPriority() - p2.getPriority();
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

class EmployeeOrderStr implements ToString<Order> {
    @Override
    public String toStr(Order t) {
        return t.emptoString();
    }

}
