
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
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class UserInterface {
    private static Scanner input = new Scanner(System.in);

    private static ArrayList<String> receipt = new ArrayList<String>(); // for the customers
    private static ArrayList<String> log = new ArrayList<String>(); // for the employee
    private static int receiptNum = 1;
    private Order shoppingCart = new Order();
    private boolean firstRun = false;

    /**
     * Welcome user and initialize user information.
     * Takes in user choice and calls the appropriate
     * function to handle
     */
    public void run() {
        if (firstRun == false) {
            firstRun = true;
            System.out.println(
                    "                                                                               $$\\     \n" +
                            "                                                                               $$ |    \n"
                            +
                            "$$$$$$\\$$$$\\   $$$$$$\\   $$$$$$\\   $$$$$$\\  $$$$$$\\$$$$\\   $$$$$$\\   $$$$$$\\ $$$$$$\\   \n"
                            +
                            "$$  _$$  _$$\\ $$  __$$\\ $$  __$$\\  \\____$$\\ $$  _$$  _$$\\  \\____$$\\ $$  __$$\\\\_$$  _|  \n"
                            +
                            "$$ / $$ / $$ |$$$$$$$$ |$$ /  $$ | $$$$$$$ |$$ / $$ / $$ | $$$$$$$ |$$ |  \\__| $$ |    \n"
                            +
                            "$$ | $$ | $$ |$$   ____|$$ |  $$ |$$  __$$ |$$ | $$ | $$ |$$  __$$ |$$ |       $$ |$$\\ \n"
                            +
                            "$$ | $$ | $$ |\\$$$$$$$\\ \\$$$$$$$ |\\$$$$$$$ |$$ | $$ | $$ |\\$$$$$$$ |$$ |       \\$$$$  |\n"
                            +
                            "\\__| \\__| \\__| \\_______| \\____$$ | \\_______|\\__| \\__| \\__| \\_______|\\__|        \\____/ \n"
                            +
                            "                        $$\\   $$ |                                                     \n"
                            +
                            "                        \\$$$$$$  |                                                     \n"
                            +
                            "                         \\______/                                                      \n");

            System.out.println("Established 1987");
        }
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
        String username = input.nextLine();
        System.out.print("Enter your password: ");
        String password = input.nextLine();

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
            System.out.println("Unable to log in, returning to main menu.");
            run();
        }

    }

    /**
     * Asks the user to sign up if they don't have an existing account
     */
    public void signUp() {
        System.out.print("Enter your first name:");
        String firstName = input.nextLine();
        System.out.print("Enter your last name:");
        String lastName = input.nextLine();
        System.out.print("Enter your username:");
        String email = input.nextLine();
        System.out.print("Enter your password:");
        String password = input.nextLine();
        System.out.print("Enter your street address:");
        String address = input.nextLine();
        System.out.print("Enter your city:");
        String city = input.nextLine();
        System.out.print("Enter your state:");
        String state = input.nextLine();
        System.out.print("Enter your ZIP code:");
        String zip = input.nextLine();
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
        try {
            System.out.println("Loading...\n");
            wait(1000);
        } catch (Exception e) {
            System.out.println(e);
        }
        System.out.printf("\nWelcome to MegaMart%s!\n", ", " + cust.getFirstName() + cust.getLastName());

        while (!choice.equalsIgnoreCase("X")) {
            String s = shoppingCart.isEmpty() ? "" : "\n\tF. Edit Cart";
            System.out.println("\tMenu: " + "\n\tA. Search for Product" + "\n\tB. Display Database of Products"
                    + "\n\tC. Place an Order"
                    + "\n\tD. View your Shopping Cart" + "\n\tE. View Purchases" + s + "\n\tX. Exit\n\tY. Logout\n");
            System.out.print("\nPlease select from one of the options: ");
            choice = input.nextLine(); // taking in choices from menu
            if (choice.equalsIgnoreCase("Y")) {
                break;
            }
            handleCustomerChoice(choice, cust);
        }
        System.out.println("Logging out... Goodbye!\nHead over to Receipt.txt to view your order information.");
        printReceipt(receipt);
        if (choice.equalsIgnoreCase("y")) {
            run();
        }

    }

    /**
     * print out employee/manager menu
     * 
     * @param emp usr position
     */
    public synchronized void employeeMenu(Employee emp) { // looks good
        String choice = "";

        try {
            System.out.println("Loading...\n");
            wait(1000);
        } catch (Exception e) {
            System.out.println(e);
        }
        System.out.printf("Welcome, %s %s!", emp.getFirstName(), emp.getLastName());
        while (!choice.equalsIgnoreCase("X")) {
            System.out.println("\n\tMenu:");
            System.out.printf("\t%s\n\t%s\n\t%s\n\t%s", "A. Search for an order",
                    "B. View order with highest priority", "C. View all orders", "D. Ship order");
            if (emp.isManager()) {
                System.out.println("\n\tE. Update Products Catalogue By Primary Key ");
            }

            System.out.println("\tY. Logout");
            System.out.println("\tX. Quit");
            System.out.print("Please select from one of the options: ");
            choice = input.nextLine(); // taking in choices from menu
            if (choice.equalsIgnoreCase("Y")) {
                break;
            }
            if (choice.equalsIgnoreCase("E")) {
                handleManagerChoice(choice);
            } else if (!choice.equalsIgnoreCase("X")) {
                handleEmployeeChoice(choice);
            }
        }
        System.out.println("Logging out... Goodbye!\n");
        printLog(log);
        if (choice.equalsIgnoreCase("Y")) {
            run();
        }
    }

    /**
     * implements the menu options for a customer
     * 
     * @param choice the menu option the user chooses as a customer
     * @param cust   a customer
     */
    private void handleCustomerChoice(String choice, Customer cust) {
        if (choice.equalsIgnoreCase("A")) {
            customerSearchProd();
        } else if (choice.equalsIgnoreCase("B")) {
            customerDisplayProd();
        } else if (choice.equalsIgnoreCase("C")) {
            placeOrder(cust);
        } else if (choice.equalsIgnoreCase("D")) {// view shopping cart
            displayCart();
        } else if (choice.equalsIgnoreCase("E")) {// view purchases
            customerViewPurchases(cust);
        } else if (choice.equalsIgnoreCase("F") && !shoppingCart.isEmpty()) {
            editCart();
        } else if (!choice.equalsIgnoreCase("X")) {
            System.out.println("\nInvalid option!"); // change to loop
        }
    }

    /**
     * Option f in Customer menu.
     * Allows customer to edit items
     * in their cart
     */
    private void editCart() {
        displayCart();
        System.out.println("Enter the name of the item you would like to edit: ");
        String choice = input.nextLine();
        if (shoppingCart.contains(choice)) {
            Product p = Product.searchName(choice);
            System.out.println("A. Change quantity\nB. Remove item");
            choice = input.nextLine();
            if (choice.equalsIgnoreCase("a")) {
                System.out.println("Enter new quantity: ");
                choice = input.nextLine();
                if (Integer.valueOf(choice) < p.getNumInStock() && Integer.valueOf(choice) > 0) {
                    p.setQuantity(Integer.valueOf(choice));
                    System.out.println("Quantity updated!");
                    displayCart();
                } else {
                    System.out.println("Unable to update quantity.");
                }
            } else if (choice.equalsIgnoreCase("b")) {
                shoppingCart.removeItem(p);
                System.out.println("Item removed!");
                displayCart();
            }
        } else {
            System.out.println("Invalid item name.");
        }
    }

    /**
     * Option A in customer menu.
     * Allows customer to search for
     * specific product
     * 
     */
    private void customerSearchProd() {
        String ptype, pname, search;
        System.out.print("Search by:\n\tA. Name\n\tB. Product type");
        System.out.print("\nPlease select from one of the options: ");
        search = input.nextLine();
        if (search.equalsIgnoreCase("A")) {
            input.nextLine();
            System.out.print("Enter the name of the product you wish to search for: ");
            pname = input.nextLine();
            Product nfound = Product.searchName(pname);
            if (nfound != null) {
                System.out.println("One record of requested product found by name: " + nfound.toString());
                addToCart(nfound);
            } else {
                System.out.println("Sorry! Requested product not found in database.");
            }
        } else if (search.equalsIgnoreCase("B")) {
            System.out.println("Types:\n\tBakery\n\tDairy\n\tMeat\n\tSnacks\n\tProduce");
            System.out.print("Enter the type of product: ");
            ptype = input.nextLine();
            Product tfound = Product.searchType(ptype);
            if (tfound != null) {
                System.out.println("One record of requested product found by type: \n" + tfound.toString());
                addToCart(tfound);
            } else {
                System.out.println("Sorry! Requested product not found in database.");
            }
        } else {
            System.out.println("Invalid option. Please try again!");
        }
    }

    /**
     * Handles adding a product with specific
     * quantity to the shopping cart after
     * user searches for a product
     * 
     * @param p
     */
    private void addToCart(Product p) {
        int update = 0;
        System.out.print("Add to cart (y/n)? ");
        String add = input.next();
        if (add.equalsIgnoreCase("y")) {
            System.out.print("Enter quantity: ");
            String quantity = input.next();
            while (Integer.valueOf(quantity) >= p.getNumInStock() && !quantity.equalsIgnoreCase("X")) {
                System.out.println("Not enough stock, please try again.");
                System.out.print("Enter quantity: (X to return to menu)");
                quantity = input.next();
            }
            update = Integer.valueOf(quantity) * (-1);
            p.updateNumInStock(update);
            if (!quantity.equalsIgnoreCase("X")) {
                if (shoppingCart.contains(p)) {
                    p.setQuantity(p.getQuantity() + Integer.valueOf(quantity));
                }
                p.setQuantity(Integer.valueOf(quantity));
                shoppingCart.addProduct(p);
                System.out.println("Item added to cart!");
            }
        }
    }

    /**
     * Option B in customer menu.
     * Displays the list of products
     * 
     */
    private void customerDisplayProd() {
        int view;
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
    }

    /**
     * Option C in customer menu,
     * Allows customer to choose shipping speed and place an order;
     * Order is put into customer's unshipped orders
     * 
     */
    private void placeOrder(Customer cust) {
        displayCart();
        if (shoppingCart.isEmpty()) {
            System.out.println("Please add a minimum of $10 to place an order");
        } else {
            System.out.printf("\t%s\n\t%s\n\t%s\n", "1. Overnight: 7000 Bruhcoins ($0.07)",
                    "2. Rush: 4500 BruhCoins ($0.04)",
                    "3. Standard: 100 BruhCoins ($0.001)");
            System.out.print("Select shipping speed: ");
            String ship = input.nextLine();
            ShippingSpeed s = ship.equals("1") ? ShippingSpeed.OVERNIGHT
                    : ship.equals("2") ? ShippingSpeed.RUSH
                            : ship.equals("3") ? ShippingSpeed.STANDARD : null;
            shoppingCart.changeShippingSpeed(s);
            System.out.printf("Total price is: $%.2f\n", shoppingCart.getPrice());
            System.out.print("Continue to checkout? (y/n): ");
            ship = input.next();
            if (ship.equalsIgnoreCase("y")) {
                System.out.print("Enter payment information: ");
                ship = input.next();
                System.out.print("Place order? (y/n): ");
                ship = input.next();
                if (ship.equalsIgnoreCase("y")) {
                    cust.addUnshippedOrders(shoppingCart);
                    Database.placeOrder(shoppingCart, cust);
                    shoppingCart = new Order();
                }
            } else {
                System.out.println("Returning to menu");
            }
        }
    }

    /**
     * Option D in customer menu,
     * displays the current shopping cart
     */
    private void displayCart() {
        if (shoppingCart.isEmpty()) {
            System.out.println("No items in cart! :(");
        } else {
            System.out.println("Here is your shopping cart: ");
            System.out.println("****************************");
            System.out.println(shoppingCart.productString());
            System.out.println("****************************");
        }
    }

    /**
     * Option E in customer menu, allows user
     * to view their orders
     * 
     * @param cust
     */
    private void customerViewPurchases(Customer cust) {
        if (cust.getUnshippedOrders().getLength() == 0) {
            System.out.println("No unshipped orders\n");
        } else {
            System.out.println("Unshipped orders:");
            System.out.println(cust.getUnshippedOrders());
        }

        if (cust.getShippedOrders().getLength() == 0) {
            System.out.println("No shipped orders\n");
        } else {
            System.out.println("Shipped orders:");
            System.out.println(cust.getShippedOrders());
        }
    }

    /**
     * Takes in user input and handles the
     * appropriate function call
     * 
     * @param choice from menu option
     */
    private void handleManagerChoice(String choice) {
        String option;
        if (choice.equalsIgnoreCase("E")) {
            System.out.println("\n\tMenu:" +
                    "\n\tA. Add New Product"
                    + "\n\tB. Update an Existing Product Price, Description or Add More to Stock"
                    + "\n\tC. Remove a Product" + "\n\tD. Display All Products");
            System.out.print("Please select from one of the options: ");
            option = input.nextLine();
            input.nextLine();
            if (option.equalsIgnoreCase("A")) {
                managerAddProd();
            } else if (option.equalsIgnoreCase("B")) {
                managerUpdateProd();
            } else if (option.equalsIgnoreCase("C")) {
                managerRemoveProduct();
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
     * Option A in manager menu.
     * Adds new product to catalogue
     * 
     */

    private void managerAddProd() {
        System.out.print("Enter the name of the product: ");
        String pname = input.nextLine();
        System.out.print("Enter the type of the product: ");
        String ptype = input.nextLine();
        Product tfound = Product.searchType(ptype);
        Product nfound = Product.searchName(pname);
        if (tfound != null && nfound != null) {
            System.out.println("Updating the number of " + pname + " in stock!");
            System.out.print("Enter number of " + pname + " to add: ");
            int stock = input.nextInt();
            nfound.updateNumInStock(stock);

            System.out.print(nfound.toString());
            log.add("Updated number of " + pname + " in stock: " + stock + "\n");
        } else {
            System.out.println("It looks like we don't have this item in our database...Let's add it in!");
            System.out.print("Enter total calories of " + pname + ": ");
            int calories = input.nextInt();
            input.nextLine();
            System.out.print("Enter the best by date for " + pname + ": ");
            String bestby = input.nextLine();
            System.out.print("Enter price of " + pname + ": $");
            double price = input.nextDouble();
            input.nextLine();
            System.out.print("Enter your description for " + pname + ": ");
            String description = input.nextLine();
            System.out.print("Enter the number of " + pname + " in stock: ");
            int stock = input.nextInt();

            Product add = new Product(pname, ptype, calories, bestby, price, description, stock);
            Product.addProduct(add);

            System.out.print(add.toString());
            log.add("New product added: " + pname);

        }
    }

    /**
     * Option B in manager menu.
     * Updates an existing product
     * 
     */

    private void managerUpdateProd() {
        int search = 0;
        System.out.print("Enter the name of the product: ");
        String pname = input.nextLine();
        System.out.print("Enter the type of the product: ");
        String ptype = input.nextLine();
        Product tfound = Product.searchType(ptype);
        Product nfound = Product.searchName(pname);
        if (tfound != null && nfound != null) {
            while (search != 4) {
                System.out.print("\nUpdate an existing:\n\t1. Product Price"
                        + "\n\t2. Product Description\n\t3. Number in Stock\n\t4. Exit");
                System.out.print("\nPlease select from one of the options: ");
                search = input.nextInt();
                if (search == 1) {
                    System.out.print("Enter new price of " + pname + ": $");
                    double price = input.nextDouble();
                    nfound.setPrice(price);
                    System.out.println("\nUpdating the price of " + pname + "!");
                    System.out.print(nfound.toString());
                    log.add("Updated price of " + pname + ": $" + price);
                } else if (search == 2) {
                    input.nextLine();
                    System.out.print("Enter your new description for " + pname + ": ");
                    String description = input.nextLine();
                    nfound.setDescription(description);
                    System.out.println("\nUpdating the description for " + pname + "!");
                    System.out.print(nfound.toString());
                    log.add("Updated description of " + pname + ": " + description);
                } else if (search == 3) {
                    System.out.print("Enter the new number of " + pname + " in stock: ");
                    int stock = input.nextInt();
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
    }

    /**
     * Option C in manager menu.
     * Removes a product from the catalogue
     * 
     */
    private void managerRemoveProduct() {
        System.out.print("Enter the name of the product: ");
        String pname = input.nextLine();
        Product tfound = Product.searchName(pname);
        System.out.print("Enter the type of the product: ");
        String ptype = input.nextLine();
        Product nfound = Product.searchType(ptype);
        if (tfound != null && nfound != null) {
            Product.removeProduct(tfound);
            System.out.println("Removing " + pname + " from the database!");
            log.add("Removed product " + pname + " from catalogue.");
        } else {
            System.out.println("Sorry! Requested product not found in database.");
        }
    }

    /**
     * Handles user input with appropriate
     * method calls
     * 
     * 
     * @param choice from menu option
     */
    private void handleEmployeeChoice(String choice) {
        if (choice.equalsIgnoreCase("A")) { // search for order
            employeeSearchOrder();
        } else if (choice.equalsIgnoreCase("B")) { // view order w/ highest priority
            System.out.println("Order with Highest Priority: ");
            Order o = Database.priorityOrder();
            if (o != null) {
                System.out.println("\n" + o);
            } else {
                System.out.println("No orders to be found");
            }
        } else if (choice.equalsIgnoreCase("C")) { // view all orders
            System.out.println("Viewing all Orders by Highest Priority: " + "\n");
            System.out.println(Database.priorityOrdersStr());
        } else if (choice.equalsIgnoreCase("D")) { // ship order
            employeeShipOrder();
        } else if (!choice.equalsIgnoreCase("X")) {
            System.out.println("#####\nInvalid option. Please try again!");
        }
    }

    /**
     * Option A in employee menu
     * Searches for specific order by
     * id or customername
     * 
     */
    private void employeeSearchOrder() {
        System.out.println("Would you like to search your order by:"
                + "\nA.Order ID" + "\n" +
                "B. Customer First and Last name");

        String searchOption = input.nextLine();
        if (searchOption.equalsIgnoreCase("A")) {

            System.out.println("Please enter the order ID: ");
            int orderID = input.nextInt();

            Order oFound = Database.searchID(orderID);
            if (oFound == null) {
                System.out.println("Sorry, order not found!");
            } else {
                System.out.println(oFound);
            }
            // some method to accept the orderID and find order
        } else if (searchOption.equalsIgnoreCase("B")) {
            System.out.println("Please enter customer first and last name:");
            System.out.println("\tFirst name: ");
            String firstName = input.nextLine();
            System.out.println("\tLast name: ");
            String lastName = input.nextLine();
            Database.searchOrderCust(new Customer(firstName, lastName));

        } else {
            // invalid input?
        }
    }

    /**
     * Option D in employee menu.
     * Removes order from heap and
     * adds it to shipped orders of the
     * customer
     * 
     */
    private void employeeShipOrder() {
        System.out.println("Enter order id to ship order: ");
        int orderId = input.nextInt();
        Order oFound = Database.searchID(orderId);
        if (oFound == null) {
            System.out.println("Sorry, order not found!");
        } else {
            Database.shipOrder(oFound);
            log.add("Shipped Order: " + orderId + ".");
        }
    }

    /**
     * write employee/manager activity log and receipt to an outfile
     */
    private void printLog(ArrayList<String> log) {
        try {
            if (log.size() != 0) {
                FileWriter f = new FileWriter("Log.txt", true);
                PrintWriter output = new PrintWriter(f);

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
    private void printReceipt(ArrayList<String> receipt) {
        try {
            if (receipt.size() != 0) {
                File file = new File(String.format("Receipt%d.txt", receiptNum++));
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
