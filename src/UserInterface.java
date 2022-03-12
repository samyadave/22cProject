import java.io.File;
import java.util.Scanner;

import Database.Database;
import Database.Database.UserType;
import ProductUtil.Product;
import UserUtil.User;


public class UserInterface {
    public void run() {
        try {

        } catch (Exception e) {
            // TODO: handle exception
        }
        String first, last, username, password; // user details
        String pos = "X"; // to check for guests

        boolean account = false; // check for existing account
        String choice = " ";

        Database.populateCatalogue();

        Scanner input = new Scanner(System.in);
        System.out.println("Welcome to MegaMart!");
        System.out.print("Enter your username: ");
        username = input.next();
        System.out.print("Enter your password: ");
        password = input.next();
        User user = Database.setLogin(username, password, user); //FIX
        UserType position = Database.getPosition(user);
        // SEARCH HASH TABLE FOR EXISTING ACCOUNTS,
        // ex: return true found false not found CAN BE CHANGED
        
        if (account == false) {
            System.out.println("It looks like we don't have your account.");
            System.out.print("Would you like to create an account(Y) or continue in as a guest(N)? ");
            choice = input.next();

            if (choice.equalsIgnoreCase("Y")) {
                System.out.println("\nLet's create an account for you!");
                System.out.print("Enter your first name: ");
                first = input.next();
                System.out.println("Enter yor last name: ");
                last = input.next();
                // Customer c = new Customer(first, last, email, password);

                customerMenu();
            } else {
                System.out.println("...Continuing as a guest");
                // DOES GUEST HAVE THE SAME PRIVILEGES AS MEMBERS
                pos = "G";
                customerMenu();
            }
        } else {
            System.out.println("Welcome back " + user.getFirstName() + " " + user.getLastName());
        }

        if (position == UserType.Manager) {
            while (!choice.equalsIgnoreCase("X")) {
                managerMenu();
                System.out.print("\nPlease select from one of the options: ");
                choice = input.next(); // taking in choices from menu
                manager(input, choice);
            }
            printReceipt();
        } else if (position == UserType.Employee) {
            while (!choice.equalsIgnoreCase("X")) {
                employeeMenu();
                System.out.print("\nPlease select from one of the options: ");
                choice = input.next(); 
               employee(input, choice);
            }
            printReceipt();
        } else if (position == UserType.Customer|| pos == "G") {
            while (!choice.equalsIgnoreCase("X")) {
                customerMenu();
                System.out.print("\nPlease select from one of the options: ");
                choice = input.next(); 
                customer(input, choice);
            }
            printReceipt();
        }

        input.close();

    }

  

    public void customerMenu() {
        System.out.println("A. Search for Product" + "\nB. Display Database of Products" + "\nC. Place an Order"
                + "\nD. View your Shopping Cart" + "\nE. View Order Status" + "\nX. Exit\n");

    }

    public void employeeMenu() {

    }

    public void managerMenu() {
        employeeMenu();
        System.out.print("/nE. Update Products Catalogue By Primary Key " + "\nX. Exit\n");

    }

    public void printReceipt() {

    }

    public void customer(Scanner input, String choice) {
        String pname, ptype, search;
        int view;
        if (choice.equalsIgnoreCase("A")) {
            System.out.print("Would you like to search for your product by name or type: ");
        	search = input.next();
        	if(search.equalsIgnoreCase("Name")) {
        		input.nextLine();
        		System.out.print("Enter the name of the product you wish to search for: ");
                pname = input.nextLine();
                Product nfound = Product.searchName(pname); 
                if(nfound != null) {
                	System.out.println("One record of requested product found by name: " + nfound.toString());
                }else {
                	System.out.println("Sorry! Requested product not found in database.");
                }
        	}else if(search.equalsIgnoreCase("Type")) {
        		System.out.print("Enter the type of product: ");
        		ptype = input.next();
        		Product tfound = Product.searchType(ptype);
        		if(tfound != null) {
            		System.out.println("One record of requested product found by type: /n" + tfound.toString());
                } else {
                	System.out.println("Sorry! Requested product not found in database.");
                }
        	}else {
        		System.out.println("Invalid option.");
        	}
        } else if (choice.equalsIgnoreCase("B")) {
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

        } else if (choice.equalsIgnoreCase("C")) {

        } else if (choice.equalsIgnoreCase("D")) {

        } else if (choice.equalsIgnoreCase("E")) {

        } else {
            System.out.println("\nInvalid option!");
        }
  
    }

    public void manager(Scanner input, String choice, Product p) {
        employee(input, choice);
        String option = "";
        if (choice == "E") {
            System.out.println("\nA. Add New Product" + "\nB. Update an Existing Product Price, Description or Add More to Stock"
                + "\nC. Remove a Product");
            System.out.print("\nPlease select from one of the options: ");
            option = input.next();
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

    public void employee(Scanner input, String choice) {
        if (choice == "A") {

        } else if (choice == "B") {

        } else if (choice == "") {

        } else if (choice == "") {

        } else if (choice == "") {

        } else {
            System.out.println("\nInvalid option!");
        }
    }







}
