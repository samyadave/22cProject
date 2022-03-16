
import java.util.Scanner;

import Database.Database;
import Database.Database.Status;
import Database.Database.UserType;
import ProductUtil.Product;
import UserUtil.Customer;
import UserUtil.Employee;

public class UserInterface {
    private static Scanner input = new Scanner(System.in);

    public UserInterface() {

    }

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

        printReceipt();
    }

    /**
     * 
     */
    public void signIn() {
        System.out.print("Enter your email: ");
        String username = input.next();
        System.out.print("Enter your password: ");
        String password = input.next();

        Status t = Database.login(username, password);

        if (t == Status.Success) {
            UserType position = Database.getPosition(username, password);
            System.out.println("Successfully logged in");
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
     * 
     */
    public void signUp() {
        System.out.println("Enter your first name:");
        String firstName = input.next();
        System.out.println("Enter your last name:");
        String lastName = input.next();
        System.out.println("Enter your email:");
        String email = input.next();
        System.out.println("Enter your password:");
        String password = input.next();
        System.out.println("Enter your street address:");
        String address = input.next();
        System.out.println("Enter your city:");
        String city = input.next();
        System.out.println("Enter your state:");
        String state = input.next();
        System.out.println("Enter your ZIP code:");
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
     * 
     * @param cust
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
        // TODO: B & S add option to add to cart (add product to shopping cart)
        while (!choice.equalsIgnoreCase("X")) {
            System.out.println("A. Search for Product" + "\nB. Display Database of Products" + "\nC. Place an Order"
                    + "\nD. View your Shopping Cart" + "\nE. View Purchases" + "\nX. Exit\n");
            System.out.print("\nPlease select from one of the options: ");
            choice = input.next(); // taking in choices from menu
            customer(choice, cust);
        }

    }

    /**
     * 
     * @param emp
     */
    public void employeeMenu(Employee emp) { // looks good
        String choice = "";

        System.out.printf("Welcome, %s!", emp.getFirstName());
        while (!choice.equalsIgnoreCase("X")) {
            System.out.printf("\n\t%s\n\t%s\n\t%s\n\t%s", "A. Search for an order",
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
    private void customer(String choice, Customer cust) {
        String pname, ptype, search;
        int view;
        if (choice.equalsIgnoreCase("A")) {
            System.out.print("Search by:\n\tA. Name\n\tB. Product type");
            System.out.println("\nSelect a choice: ");
            search = input.next();
            if (search.equalsIgnoreCase("A")) {
                input.nextLine();
                System.out.print("Enter the name of the product you wish to search for: ");
                pname = input.nextLine();
                // TODO: FIX product fetching
                Product nfound = Product.searchName(pname);
                if (nfound != null) {
                    System.out.println("One record of requested product found by name: " + nfound.toString());
                } else {
                    System.out.println("Sorry! Requested product not found in database.");
                }
            } else if (search.equalsIgnoreCase("B")) {
                // TODO: Update from eclipse = Meigan and Eileen
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
                System.out.println("Invalid option. Please try again!"); // change to loop
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
            // Product toAdd = whatever the customer chose
            // Database.shoppingCart.add(toAdd)
        } else if (choice.equalsIgnoreCase("D")) {
            // Place an order
            // Database.orders.add(Database.shoppingCart)
        } else if (choice.equalsIgnoreCase("E")) {
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
     * 
     * @param choice
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
            System.out.println(
                    "\nA. Add New Product" + "\nB. Update an Existing Product Price, Description or Add More to Stock"
                            + "\nC. Remove a Product");
            System.out.print("\nPlease select from one of the options: ");
            option = input.next();
            input.nextLine();
            // Meigan and Eileen's TODO
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
                    // TESTING PURPOSES REMOVE AFTER
                    // TODO: Display new object added
                    Product.displaybyName();
                    Product.displaybyType();
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

                    // TESTING PURPOSES REMOVE AFTER
                    Product.displaybyName();
                    Product.displaybyType();

                }
            } else if (option.equalsIgnoreCase("B")) {
                System.out.println("Enter the name of the product: ");
                pname = input.nextLine();
                System.out.print("Enter the type of the product: ");
                ptype = input.nextLine();
                tfound = Product.searchName(pname);
                nfound = Product.searchType(ptype);
                if (tfound != null && nfound != null) {
                    while (search != 4) {
                        System.out.print("Update an existing:\n\t1. Product Price"
                                + "\n\t2. Product Description\n\t3. Number in Stock\n\t4. Exit");
                        System.out.print("\nPlease select from one of the options: ");
                        search = input.nextInt();
                        if (search == 1) {
                            System.out.print("Enter new price of " + pname + ": $");
                            price = input.nextDouble();
                            nfound.setPrice(price);
                            System.out.println("Updating the price of " + pname + "!");
                        } else if (search == 2) {
                            input.nextLine();
                            System.out.print("Enter your new description for " + pname + ": ");
                            description = input.nextLine();
                            nfound.setDescription(description);
                            System.out.println("Updating the description for " + pname + "!");
                        } else if (search == 3) {
                            System.out.print("Enter the number of " + pname + " you want to add: ");
                            stock = input.nextInt();
                            nfound.updateNumInStock(stock);
                            System.out.println("Updating the number of " + pname + " in stock!");
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
                } else {
                    System.out.println("Sorry! Requested product not found in database.");
                }
            } else {
                System.out.println("Invalid option. Please try again!");
            }
        } else {
            System.out.println("\nInvalid option. Please try again!"); // loop(?)
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
