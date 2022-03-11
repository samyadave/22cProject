package Database;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import UserUtil.Customer;
import UserUtil.Employee;
import UserUtil.HashTable;
import UserUtil.User;

/**
 * Abstract class with storage capabilities
 */
public abstract class Database {
    public static HashTable<Customer> customerDatabase = new HashTable<>(100);
    public static HashTable<Employee> employeeDatabase = new HashTable<>(100);
    public static HashTable<String> usernames = new HashTable<>(100);
    public static HashTable<String> credentials = new HashTable<>(100);

    /**
     * <ul>
     * <li>Customer
     * <li>Employee
     * </ul>
     */
    public enum UserType {
        Customer,
        Employee
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
        if (!validateUser(user)) {
            return Status.Failed;
        }

        User u = (User) user;

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
     * Helper method to validate user - checks if
     * username already exists
     */
    private static boolean validateUser(Object user) {
        if (!(user instanceof User)) {
            return false;
        }

        User u = (User) user;

        return !(Database.usernames.contains(u.getLogin()));
    }

    /**
     * 
     * @param username
     * @return
     */
    public static boolean validateUsername(String username) {
        return !(Database.usernames.contains(username));
    }

    /**
     * Retrieves the proper user object after verifying the
     * credentials passed. Returns null if credentials not valid
     * 
     * @param ut UserType enum to clarify Customer or Employee login
     * @return the appropriate Customer or Employee object as User object
     */
    public static User login(String username, String password, UserType ut) {
        if (!Database.credentials.contains(username + password)) {
            return null;
        }

        switch (ut) {
            case Customer:
                return Database.customerDatabase.get(new Customer(username, password));
            case Employee:
                return Database.employeeDatabase.get(new Employee(username, password));
        }

        return null;
    }

    /**
     * TODO
     */
    public static void startUp() {
        populateCustomers();
        populateEmployees();
    }

    /**
     * TODO
     */
    private static void populateCustomers() {
        try {
            BufferedReader f = new BufferedReader(new FileReader(".\\src\\Database\\Customer.txt"));
            String line = f.readLine();
            while (line != null) {
                String[] data = new String[8];
                for (int i = 0; i < 8; i++) {
                    data[i] = line;
                    line = f.readLine();
                }
                Database.customerDatabase.add(new Customer(data));
                Database.usernames.add(data[2]);
            }
            f.close();
        } catch (IOException e) {
            // TODO
            System.out.println(e);
        }
    }

    /**
     * TODO
     * 
     * @param c
     */
    private static void addCustomer(Customer c) {
        try {
            BufferedWriter f = new BufferedWriter(new FileWriter(".\\src\\Database\\Customer.txt", true));
            f.write('\n' + c.toString());
            f.close();
        } catch (IOException e) {
            // TODO

        }
    }

    /**
     * TODO
     */
    private static void populateEmployees() {
        try {
            BufferedReader f = new BufferedReader(new FileReader(".\\src\\Database\\Employee.txt"));
            String line = f.readLine();
            while (line != null) {
                String[] data = new String[5];
                for (int i = 0; i < 5; i++) {
                    data[i] = line;
                    line = f.readLine();
                }
                Database.employeeDatabase.add(new Employee(data));
                Database.usernames.add(data[2]);
            }
            f.close();
        } catch (IOException e) {
            // TODO
            System.out.println(e);
        }
    }

    /**
     * TODO
     * 
     * @param emp
     */
    private static void addEmployee(Employee emp) {
        try {
            BufferedWriter f = new BufferedWriter(new FileWriter(".\\src\\Database\\Employee.txt", true));
            f.write('\n' + emp.toString());
            f.close();
        } catch (IOException e) {
            // TODO
        }
    }

}
