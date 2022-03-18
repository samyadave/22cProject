
/**
 * UserInterface.java
 * @author Meigan Lu
 * @author Eileen Huynh
 * @author Sol Valdimarsdottir
 * @author Sam Yadav
 * @author Brandon Ho
 * CIS 22C Course Project
 */
import java.util.Scanner;

import javax.xml.crypto.Data;

import Database.Database;
import Database.Database.Status;
import Database.Database.UserType;
import OrderUtil.Order;
import OrderUtil.Order.ShippingSpeed;
import ProductUtil.Product;
import UserUtil.Customer;
import UserUtil.Employee;
import java.util.ArrayList;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

public class UserInterface {
    private static Scanner input = new Scanner(System.in);

    private static ArrayList<String> receipt = new ArrayList<String>();
    private static ArrayList<String> log = new ArrayList<String>();

    private Order shoppingCart = new Order();

    public UserInterface() {

    }

    /**
     * Welcome user and initialize user information
     */
    public void run() {
        System.out.printf("%s\n\t%s\n\t%s\n\t%s\n\t%s\n%s", "Welcome to MegaMart!", "Choose an option:", "1. Sign in",
                "2. Sign up", "3. Continue as guest", "Enter choice: ");
        int choice = input.nextInt();
        switch (choice) {
            case 1:
                signIn();
                break;
            case 2:
                signUp();
                break;
            case 3:
                customerMenu(new Customer());
                break;
        }

    }

    /**
     * Asks the user to sign in if they have an existing account
     */
    public void signIn() {
        System.out.print("Enter your username: ");
        String username = input.next();
        System.out.print("Enter your password: ");
        String password = input.next();

        Status t = Database.login(username, password);

        if (t == Status.Success) {
            UserType position = Database.getPosition(username, password);
            System.out.println("\nSuccessfully logged in!");
            switch (position) {
                case Customer:
                    customerMenu((Customer) Database.loggedIn);
                    break;
                case Manager:
                case Employee:
                    employeeMenu((Employee) Database.loggedIn);
                    break;
            }
        } else {
            System.out.println("Unable to log in, returning to main menu");
            run();
        }

    }

    /**
     * Asks the user to sign up if they don't have an existing account
     */
    public void signUp() {
        System.out.print("Enter your first name:");
        String firstName = input.next();
        System.out.print("Enter your last name:");
        String lastName = input.next();
        System.out.print("Enter your username:");
        String email = input.next();
        System.out.print("Enter your password:");
        String password = input.next();
        System.out.print("Enter your street address:");
        String address = input.next();
        System.out.print("Enter your city:");
        String city = input.next();
        System.out.print("Enter your state:");
        String state = input.next();
        System.out.print("Enter your ZIP code:");
        String zip = input.next();
        Status added = Database.addUser(new Customer(firstName, lastName, email, password, address, city, state, zip));
        switch (added) {
            case Success:
                System.out.println("Successfully created an account and logged in!");
                Database.login(email, password);
                customerMenu((Customer) Database.loggedIn);
                break;
            case Error:
            case Failed:
                System.out.println("Account creation failed, returning to menu!");
        }
    }

    /**
     * prints the customer menu
     * 
     * @param cust verify user position
     */
    public synchronized void customerMenu(Customer cust) {
        String choice = "";
        System.out.printf("\nWelcome to MegaMart%s!\n", ", " + cust.getFirstName() == null ? cust.getFirstName() : "");
        try {
            System.out.println("Loading...");
            wait(1000);
        } catch (Exception e) {
            System.out.println(e);
        }

        while (!choice.equalsIgnoreCase("X")) {
            System.out.println("\tMenu: " + "\n\tA. Search for Product" + "\n\tB. Display Database of Products"
                    + "\n\tC. Place an Order"
                    + "\n\tD. View your Shopping Cart" + "\n\tE. View Purchases" + "\n\tX. Exit\n");
            System.out.print("\nPlease select from one of the options: ");
            choice = input.next(); // taking in choices from menu
            customer(choice, cust);
        }
        System.out.println("Logging out... Goodbye!\nHead over to Receipt.txt to view your order information.");
        printReceipt(receipt);

    }

    /**
     * print out employee/manager menu
     * 
     * @param emp usr position
     */
    public void employeeMenu(Employee emp) { // looks good
        String choice = "";

        System.out.printf("Welcome, %s!", emp.getFirstName());
        while (!choice.equalsIgnoreCase("X")) {
            System.out.println("\n\tMenu:");
            System.out.printf("\t%s\n\t%s\n\t%s\n\t%s", "A. Search for an order",
                    "B. View order with highest priority", "C. View all orders", "D. Ship order");
            if (emp.isManager()) {
                System.out.println("\n\tE. Update Products Catalogue By Primary Key ");
            }

            System.out.println("\tX. Quit");
            System.out.print("Please select from one of the options: ");
            choice = input.next(); // taking in choices from menu
            if (choice.equalsIgnoreCase("E")) {
                manager(choice);
            } else {
                employee(choice);
            }
        }
        System.out.println("Logging out... Goodbye!\nHead over to Receipt.txt to view your activity log.");
        printLog(log);
    }

    /**
     * implements the menu options for a customer
     * 
     * @param choice the menu option the user chooses as a customer
     * @param cust   a customer
     */
    private void customer(String choice, Customer cust) {
        String pname, ptype, search;
        int view;
        if (choice.equalsIgnoreCase("A")) {
            System.out.print("Search by:\n\tA. Name\n\tB. Product type");
            System.out.print("\nPlease select from one of the options: ");
            search = input.next();
            if (search.equalsIgnoreCase("A")) {
                input.nextLine();
                System.out.print("Enter the name of the product you wish to search for: ");
                pname = input.nextLine();
                Product nfound = Product.searchName(pname);
                if (nfound != null) {
                    System.out.println("One record of requested product found by name: " + nfound.toString());
                    System.out.println("Add to cart (y/n)?");
                    String add = input.next();
                    if (add.equalsIgnoreCase("y")) {
                        shoppingCart.addProduct(nfound);
                        System.out.println("Item added to cart!");
                    }
                } else {
                    System.out.println("Sorry! Requested product not found in database.");
                }
            } else if (search.equalsIgnoreCase("B")) {
                System.out.println("Types:\n\tBakery\n\tDairy\n\tMeat\n\tSnacks\n\tProduce");
                System.out.print("Enter the type of product: ");
                ptype = input.next();
                Product tfound = Product.searchType(ptype);
                if (tfound != null) {
                    System.out.println("One record of requested product found by type: \n" + tfound.toString());
                    System.out.println("Add to cart (y/n)?");
                    String add = input.next();
                    if (add.equalsIgnoreCase("y")) {
                        shoppingCart.addProduct(tfound);
                        System.out.println("Item added to cart!");
                    }
                } else {
                    System.out.println("Sorry! Requested product not found in database.");
                }
            } else {
                System.out.println("Invalid option. Please try again!"); // change to loop
            }
        } else if (choice.equalsIgnoreCase("B")) {
            System.out.print("View database of products sorted by:\n\t1. Name (primary key)\n\t"
                    + "2. Product type (secondary key)");
            System.out.print("\nPlease select from one of the options: ");
            view = input.nextInt();
            if (view == 1) {
                Product.displaybyName();
            } else if (view == 2) {
                Product.displaybyType();
            } else {
                System.out.println("Sorry! Invalid option.");
            }

            // Place an order, make a priority and ID
            // Brandon and Sol's stuff TODO:
        } else if (choice.equalsIgnoreCase("C")) {
            System.out.printf("\t%s\n\t%s\n\t%s", "Overnight: 7000 Bruhcoins ($0.07)", "Rush: 4500 BruhCoins ($0.04)",
                    "Standard: 100 BruhCoins ($0.001)");
            System.out.println("Select shipping speed: ");
            String ship = input.next();
            ShippingSpeed s = ship.toLowerCase() == "overnight" ? ShippingSpeed.OVERNIGHT
                    : ship.toLowerCase() == "rush" ? ShippingSpeed.RUSH
                            : ship.toLowerCase() == "standard" ? ShippingSpeed.STANDARD : null;
            shoppingCart.changeShippingSpeed(s);
            System.out.println("Total price is: " + shoppingCart.getPrice());
            System.out.println("Here is your shopping cart: \n\n");
            System.out.println(shoppingCart.toString());
        } else if (choice.equalsIgnoreCase("D")) {// view shopping cart
            System.out.println("Here is your shopping cart: \n\n");
            // get shopping cart and print it using toString from Heap

            System.out.println(shoppingCart.toString());

        } else if (choice.equalsIgnoreCase("E")) {// view purchases
            if (cust.getUnshippedOrders().getLength() == 0) {
                System.out.println("No unshipped orders");
            } else {
                System.out.println("Unshipped orders:");
                System.out.println(cust.getUnshippedOrders());
            }

            if (cust.getShippedOrders().getLength() == 0) {
                System.out.println("No shipped orders");
            } else {
                System.out.println("Shipped orders:");
                System.out.println(cust.getShippedOrders());
            }

        } else if (!choice.equalsIgnoreCase("X")) {
            System.out.println("\nInvalid option!"); // change to loop
        }

    }

    /**
     * implements the menu choice the user has chosen
     * 
     * @param choice from menu option
     */
    private void manager(String choice) {
        String pname, ptype;
        String option = "", description, bestby;
        double price;
        int stock, calories;
        int search = 0;
        Product p = new Product();
        Product tfound, nfound;
        if (choice.equalsIgnoreCase("E")) {
            System.out.println("\n\tMenu:" +
                    "\n\tA. Add New Product"
                    + "\n\tB. Update an Existing Product Price, Description or Add More to Stock"
                    + "\n\tC. Remove a Product" + "\n\tD. Display All Products");
            System.out.print("Please select from one of the options: ");
            option = input.next();
            input.nextLine();
            if (option.equalsIgnoreCase("A")) {
                System.out.print("Enter the name of the product: ");
                pname = input.nextLine();
                System.out.print("Enter the type of the product: ");
                ptype = input.nextLine();
                tfound = Product.searchType(ptype);
                nfound = Product.searchName(pname);
                if (tfound != null && nfound != null) {
                    System.out.println("Updating the number of " + pname + " in stock!");
                    System.out.print("Enter number of " + pname + " to add: ");
                    stock = input.nextInt();
                    nfound.updateNumInStock(stock);

                    System.out.print(nfound.toString());
                    log.add("Updated number of " + pname + " in stock: " + stock + "\n");
                } else {
                    System.out.println("It looks like we don't have this item in our database...Let's add it in!");
                    System.out.print("Enter total calories of " + pname + ": ");
                    calories = input.nextInt();
                    input.nextLine();
                    System.out.print("Enter the best by date for " + pname + ": ");
                    bestby = input.nextLine();
                    System.out.print("Enter price of " + pname + ": $");
                    price = input.nextDouble();
                    input.nextLine();
                    System.out.print("Enter your description for " + pname + ": ");
                    description = input.nextLine();
                    System.out.print("Enter the number of " + pname + " in stock: ");
                    stock = input.nextInt();

                    Product add = new Product(pname, ptype, calories, bestby, price, description, stock);
                    p.addProduct(add);

                    System.out.print(add.toString());
                    log.add("New product added: \nName: " + pname + "\nType: " + ptype);

                }
            } else if (option.equalsIgnoreCase("B")) {
                System.out.print("Enter the name of the product: ");
                pname = input.nextLine();
                System.out.print("Enter the type of the product: ");
                ptype = input.nextLine();
                tfound = Product.searchName(pname);
                nfound = Product.searchType(ptype);
                if (tfound != null && nfound != null) {
                    while (search != 4) {
                        System.out.print("\nUpdate an existing:\n\t1. Product Price"
                                + "\n\t2. Product Description\n\t3. Number in Stock\n\t4. Exit");
                        System.out.print("\nPlease select from one of the options: ");
                        search = input.nextInt();
                        if (search == 1) {
                            System.out.print("Enter new price of " + pname + ": $");
                            price = input.nextDouble();
                            nfound.setPrice(price);
                            System.out.println("\nUpdating the price of " + pname + "!");
                            System.out.print(nfound.toString());
                            log.add("Updated price of " + pname + ": $" + price);
                        } else if (search == 2) {
                            input.nextLine();
                            System.out.print("Enter your new description for " + pname + ": ");
                            description = input.nextLine();
                            nfound.setDescription(description);
                            System.out.println("\nUpdating the description for " + pname + "!");
                            System.out.print(nfound.toString());
                            log.add("Updated description of " + pname + ": " + description);
                        } else if (search == 3) {
                            System.out.print("Enter the new number of " + pname + " in stock: ");
                            stock = input.nextInt();
                            nfound.setNumInStock(stock);
                            System.out.println("\nUpdating the number of " + pname + " in stock!");
                            System.out.print(nfound.toString());
                            log.add("Updated number of " + pname + " in stock: " + stock);
                        } else if (search == 4) {
                            System.out.println("Exiting! Back to main menu!");
                        } else {
                            System.out.println("Invalid option. Please try again!");
                        }

                    }
                } else {
                    System.out.println("Sorry! Requested product not found in database.");
                }
            } else if (option.equalsIgnoreCase("C")) {
                System.out.print("Enter the name of the product: ");
                pname = input.nextLine();
                tfound = Product.searchName(pname);
                System.out.print("Enter the type of the product: ");
                ptype = input.nextLine();
                nfound = Product.searchType(ptype);
                if (tfound != null && nfound != null) {
                    p.removeProduct(tfound);
                    System.out.println("Removing " + pname + " from the database!");
                    log.add("Removed product " + pname + " from catalogue.");
                } else {
                    System.out.println("Sorry! Requested product not found in database.");
                }
            } else if (option.equalsIgnoreCase("D")) {
                Product.displaybyName();
                Product.displaybyType();
            } else {
                System.out.println("Invalid option. Please try again!");
            }
        } else {
            System.out.println("\nInvalid option. Please try again!");
        }

    }

    /**
     * implements the menu options for an employee
     * 
     * @param choice the menu option the user chooses as a customer
     */
    private void employee(String choice) {
        if (choice.equalsIgnoreCase("A")) { // search for order
            System.out.println("Would you like to search your order by:"
                    + "\nA.Order ID" + "\n" +
                    "B. Customer First and Last name");

            // SEARCHING FOR ORDER

            String searchOption = input.next();
            if (searchOption.equalsIgnoreCase("A")) {
                System.out.println("Please enter the order ID: ");
                String orderID = input.next();
                // some method to accept the orderID and find order
            } else if (searchOption.equalsIgnoreCase("B")) {
                System.out.println("Please enter customer first and last name: ");
            } else {

            }
        } else if (choice.equalsIgnoreCase("B")) { // view order w/ highest priority
            System.out.println("Order with Highest Priority: ");
        } else if (choice.equalsIgnoreCase("C")) { // view all orders
            System.out.println("Viewing all Orders by Highest Priority: " + "\n");

        } else if (choice.equalsIgnoreCase("D")) { // ship order
            // call remove from heap and puts the order into customer linked list
        } else if (choice.equalsIgnoreCase("X")) {

        } else {
            System.out.println("#####\nInvalid option. Please try again!");
            System.out.println("Within employee else statement");
        }
    }

    /**
     * write employee/manager activity log and receipt to an outfile
     */
    public void printLog(ArrayList<String> log) {
        try {
            if (log != null) {
                File file = new File("Log.txt");
                PrintWriter output = new PrintWriter(file);

                for (int i = 0; i < log.size(); i++) {
                    output.println(log.get(i));
                }
                output.close();
            } else {
                System.out.println("You have no activities from today!");
            }
        } catch (IOException e) {
            System.out.println(e);
        }

    }

    /**
     * write customer order information and receipt to an outfile
     */
    public void printReceipt(ArrayList<String> receipt) {
        try {
            if (receipt != null) {
                File file = new File("Receipt.txt");
                PrintWriter output = new PrintWriter(file);

                for (int i = 0; i < receipt.size(); i++) {
                    output.println(receipt.get(i));
                }
                output.close();
            } else {
                System.out.println("You have no pending orders!");
            }
        } catch (IOException e) {
            System.out.println(e);
        }

    }

}
