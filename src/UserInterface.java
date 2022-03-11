import java.io.File;
import java.util.Scanner;

import Database.Database;
import Database.Database.UserType;
import ProductUtil.Product;

public class UserInterface {
    public void run() {
        try {

        } catch (Exception e) {
            // TODO: handle exception
        }
        MegaMart m = new MegaMart();

        String pname, ptype;
        int view;
        String first, last, email, password; // user details
        // String position = "C"; // declared for printing purposes

        boolean account = false; // check for existing account
        String choice = " ";

        Database.populateCatalogue();

        Scanner input = new Scanner(System.in);
        System.out.println("Welcome to MegaMart!");
        System.out.print("Enter your email address: ");
        email = input.next();
        System.out.print("Enter your password: ");
        password = input.next();
        // User u = Database.login(username, password, );

        // SEARCH HASH TABLE FOR EXISTING ACCOUNTS,
        // ex: return true found false not found CAN BE CHANGED
        if (account == true) {
            UserType position = Database.getPosition(user);
            // check if search results position match M, E, or C
            System.out.println("Welcome "); // position = name !
            if (position == "M") {
                m.managerMenu();
            } else if (position == "E") {
                m.employeeMenu();
            } else if (position == "C") {
                m.customerMenu();
            }
        } else {
            System.out.println("It looks like we don't have your account.");
            System.out.print("Would you like to create an account(Y) or continue in as a guest(N)? ");
            choice = input.next();

            if (choice == "Y") {
                System.out.println("\nLet's create an account for you!");
                System.out.print("Enter your first name: ");
                first = input.next();
                System.out.println("Enter yor last name: ");
                last = input.next();
                // Customer c = new Customer(first, last, email, password);

                m.customerMenu();
            } else {
                System.out.println("...Continuing as a guest");
                // DOES GUEST HAVE THE SAME PRIVILEGES AS MEMBERS
                position = "G";
                m.customerMenu();
            }
        }

        System.out.print("\nPlease select from one of the options: ");
        choice = input.next(); // taking in choices from menu
        if (position == UserType.Manager) {
            while (!choice.equalsIgnoreCase("X")) {
                if (choice == "A") {

                } else if (choice == "B") {

                } else if (choice == "") {

                } else if (choice == "") {

                } else if (choice == "") {

                } else {
                    System.out.println("\nInvalid option!");
                }
            }
            m.printReceipt();
        } else if (position == UserType.Employee) {
            while (!choice.equalsIgnoreCase("X")) {
                if (choice == "A") {

                } else if (choice == "B") {

                } else if (choice == "") {

                } else if (choice == "") {

                } else if (choice == "") {

                } else {
                    System.out.println("\nInvalid option!");
                }
            }
            m.printReceipt();
        } else if (position == "C" || position == "G") {
            while (!choice.equalsIgnoreCase("X")) {
                System.out.println("");
                if (choice == "A") {
                    System.out.print("Enter the name of the product you wish to search for: ");
                    pname = input.next();
                    System.out.print("Enter the type of product: ");
                    ptype = input.next();

                } else if (choice == "B") {
                    System.out.print("View database of products sorted by primary key name (1)"
                            + "or by secondary key type(2): ");
                    view = input.nextInt();
                    if (view == 1) {
                        Product.displaybyName();

                    } else if (view == 2) {
                        Product.displaybyType();
                    } else {
                        System.out.println("Sorry! Invalid option.");
                    }

                } else if (choice == "C") {

                } else if (choice == "D") {

                } else if (choice == "E") {

                } else {
                    System.out.println("\nInvalid option!");
                }
            }
            m.printReceipt();
        }

        input.close();

    }

    public void managerMenu() {

    }

    public void managerMenu() {

    }
}
