
/**
 * MegaMart.java
 * @author Meigan Lu
 * @author Eileen Huynh
 * @author Sol Valdimarsdottir
 * @author Sam Yadav
 * @author Brandon Ho
 * CIS 22C Course Project
 */

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;

import Database.Database;
import ProductUtil.Product;
import UserUtil.Customer;
import UserUtil.Employee;
import UserUtil.User;

public class MegaMart {
    private User currentUser;
    private Database.UserType ut;
    private Product[] shoppingCart;
    private static final String products = "Catalogue.txt";

  
    public static void main(String[] args) throws IOException {
        MegaMart.onStart();
    }

    // public static void main(String[] args) {
    // onStart();
    // Database.addUser(new Customer("firstName", "lastName", "login", "password",
    // "address", "city", "state", "zip"));
    // Database.addUser(new Employee("firstName", "lastName", "login1", "password",
    // true));

    // // UserInterface ui = new UserInterface();
    // // ui.run();
    // }

    private static void onStart() {
        // initialize databases
        Database.startUp();
        // populate products
        UserInterface u = new UserInterface();
        u.run();
    }
}
