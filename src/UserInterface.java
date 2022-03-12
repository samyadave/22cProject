
import java.util.Scanner;

import Database.Database;
import Database.Database.UserType;
import ProductUtil.Product;
import UserUtil.Customer;
import UserUtil.Employee;

public class UserInterface {
    private static Scanner input = new Scanner(System.in);

    public void run() {
        // Sam's TODO
        // login ?
        // sign up
        // continue as guest
        System.out.println("Welcome to MegaMart!");
        System.out.print("Enter your username: ");
        String username = input.next();
        System.out.print("Enter your password: ");
        String password = input.next();

        UserType position = Database.getPosition(username, password);
        switch (position) {
            case Employee:
                employeeMenu((Employee) Database.login(username, password, position));
                break;
            case Manager:
            case Customer:
                customerMenu((Customer) Database.login(username, password, position));
                break;

        }

        printReceipt();
    }

    /**
     * 
     * @param cust
     */
    public void customerMenu(Customer cust) {
        String choice = "";

        while (!choice.equalsIgnoreCase("X")) {
            System.out.println("A. Search for Product" + "\nB. Display Database of Products" + "\nC. Place an Order"
                    + "\nD. View your Shopping Cart" + "\nE. View Order Status" + "\nX. Exit\n");
            System.out.print("\nPlease select from one of the options: ");
            choice = input.next(); // taking in choices from menu
            customer(choice);
        }

    }

    /**
     * 
     * @param emp
     */
    public void employeeMenu(Employee emp) { // looks good
        String choice = "";

        while (!choice.equalsIgnoreCase("X")) {
            System.out.printf("\t%s\n\t%s\n\t%s\n\t%s\n\t%s\n\t%s\n", "A. Search for an order",
                    "B. View order with highest priority", "C. View all orders", "D. Ship order");
            if (emp.isManager()) {
                System.out.println("\tE. Update Products Catalogue By Primary Key ");
            }

            System.out.println("\tX. Quit");
            System.out.print("\nPlease select from one of the options: ");
            choice = input.next(); // taking in choices from menu

            if (choice.equalsIgnoreCase("E")) {
                manager(choice);
            } else {
                employee(choice);
            }

        }
    }

    /**
     * TODO
     */
    public void printReceipt() {

    }

    /**
     * 
     * @param choice
     */
    private void customer(String choice) {
        String pname, ptype, search;
        int view;
        if (choice.equalsIgnoreCase("A")) {
            System.out.print("Search by:\n\tA. Name\n\tB. Product type");
            search = input.next();
            if (search.equalsIgnoreCase("A")) {
                input.nextLine();
                System.out.print("Enter the name of the product you wish to search for: ");
                pname = input.nextLine();
                Product nfound = Product.searchName(pname);
                if (nfound != null) {
                    System.out.println("One record of requested product found by name: " + nfound.toString());
                } else {
                    System.out.println("Sorry! Requested product not found in database.");
                }
            } else if (search.equalsIgnoreCase("B")) {
                System.out.println("Types:\n\tBakery\n\tDairy\n\tMeat\n\tSnacks\n\tProduce");
                System.out.print("Enter the type of product: ");
                ptype = input.next();
                Product tfound = Product.searchType(ptype);
                if (tfound != null) {
                    System.out.println("One record of requested product found by type: /n" + tfound.toString());
                } else {
                    System.out.println("Sorry! Requested product not found in database.");
                }
            } else {
                System.out.println("Invalid option."); // change to loop
            }
        } else if (choice.equalsIgnoreCase("B")) {
            System.out.print("View database of products sorted by:\n\t1. By primary key name\n\t"
                    + "2. By secondary key type: ");
            view = input.nextInt();
            if (view == 1) {
                Product.displaybyName();
            } else if (view == 2) {
                Product.displaybyType();
            } else {
                System.out.println("Sorry! Invalid option."); // loop
            }

            // Brandon and Sol's stuff TODO:
        } else if (choice.equalsIgnoreCase("C")) {

        } else if (choice.equalsIgnoreCase("D")) {

        } else if (choice.equalsIgnoreCase("E")) {

        } else {
            System.out.println("\nInvalid option!"); // change to loop
        }

    }

    /**
     * 
     * @param choice
     */
    private void manager(String choice) {
        String option = "";
        if (choice == "E") {
            System.out.println(
                    "\nA. Add New Product" + "\nB. Update an Existing Product Price, Description or Add More to Stock"
                            + "\nC. Remove a Product");
            System.out.print("\nPlease select from one of the options: ");
            option = input.next();
            // Meigan and Eileen's TODO
            if (option.equalsIgnoreCase("A")) {

            } else if (option.equalsIgnoreCase("B")) {

            } else if (option.equalsIgnoreCase("C")) {

            } else {
                System.out.println("Invalid option!");
            }
        } else {
            System.out.println("\nInvalid option!");
        }

    }

    /**
     * 
     * @param choice
     */
    private void employee(String choice) {
        // Brandon and Sol's TODO
    }

}
