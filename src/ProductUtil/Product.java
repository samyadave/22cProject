/**
 * Product.java
 * @author Meigan Lu
 * @author Eileen Huynh
 * @author Sol Valdimarsdottir
 * @author Sam Yadav
 * @author Brandon Ho
 * CIS 22C Course Project
 */
package ProductUtil;

import java.text.DecimalFormat;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Product {
	private String name;
	private String type;
	private int calories;
	private String bestby;
	private double price;
	private String description;
	private int numInStock;
	private int quantity;
	private static BST<Product> itemsName = new BST<>();
	private static BST<Product> itemsType = new BST<>();

	/**
	 * Default constructor for Product. Calls the 7 argument constructor
	 * Sets name default to "name unknown"
	 * Sets type default to "type unknown"
	 * Sets calories default to 0
	 * Sets bestby to "00/00/00"
	 * Sets price to 0.0
	 * Sets description to "description unknown"
	 * Sets numInStock to 0
	 */
	public Product() {
		this("name unknown", "type unknown", 0, "00/00/00", 0.0, "description unknown", 0);
	}

	/**
	 * Two argument constructor for the Product class.
	 * Calls the 7 argument constructor
	 * 
	 * @param name name of product
	 * @param type type of product
	 */
	public Product(String name, String type) {
		this(name, type, 0, "00/00/00", 0.0, "description unknown", 0);
	}

	/**
	 * Constructor for Product
	 * 
	 * @param name        Sets name default to "name unknown"
	 * @param type        Sets type default to "type unknown"
	 * @param calories    Sets calories default to 0
	 * @param bestby      Sets bestby to "00/00/00"
	 * @param price       Sets price to 0.0
	 * @param description Sets description to "description unknown"
	 * @param numInStock  the current stock
	 */
	public Product(String name, String type, int calories, String bestby, double price, String description,
			int numInStock) {
		this.name = name;
		this.type = type;
		this.calories = calories;
		this.bestby = bestby;
		this.price = price;
		this.description = description;
		this.numInStock = numInStock;

	}

	/**
	 * Returns the name of the product
	 * 
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Returns the product type
	 * 
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * Returns the calories of the product
	 * 
	 * @return the calories
	 */
	public int getCalories() {
		return calories;
	}

	/**
	 * Returns the bestby date of the product
	 * 
	 * @return the bestby
	 */
	public String getBestBy() {
		return bestby;
	}

	/**
	 * Returns the price of the product
	 * 
	 * @return the price
	 */
	public double getPrice() {
		return price;
	}

	/**
	 * Returns the description of the product
	 * 
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Returns the total number of product
	 * 
	 * @return the value of numInStock
	 */
	public int getNumInStock() {
		return numInStock;
	}

	/**
	 * Set the price of the product
	 */
	public void setPrice(double price) {
		this.price = price;
	}

	/**
	 * Set the description of the product
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Set the number in stock of the product
	 */
	public void setNumInStock(int numInStock) {
		this.numInStock = numInStock;
	}

	/**
	 * Updates numInStock variable by a specified amount
	 * 
	 * @param n the number of products to add
	 */
	public void updateNumInStock(int n) {
		numInStock = numInStock + n;
	}

	/**
	 * 
	 * @return
	 */
	public int getQuantity() {
		return quantity;
	}

	/**
	 * 
	 * @param quantity
	 */
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	/**
	 * Overrides equals for Object using the formula given in class. we will
	 * consider two cards to be equal on the basis of name and type only
	 * 
	 * @return whether two Products have the same name and type
	 */
	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true; // compares memory address
		} else if (!(o instanceof Product)) {
			return false; // makes sure comparing 2 of same type
		} else {
			Product p = (Product) o;
			return this.getName().equalsIgnoreCase(p.getName()) && getType() == (p.getType());
		}
	}

	/**
	 * Displays all products stored in the BST
	 * sorted by primary key name
	 */
	public static void displaybyName() { // move to database
		System.out.println("\nProducts sorted by Name: " + itemsName.inOrderString());
	}

	/**
	 * Displays all products stored in the BST
	 * sorted by secondary key type
	 */
	public static void displaybyType() { // move to database
		System.out.println("\nProducts sorted by Type: " + itemsType.inOrderString());
	}

	/**
	 * Search for a product by primary key name
	 * 
	 * @param name of product to search
	 * @return Product found or null
	 */
	public static Product searchName(String name) { // move to database
		Product n = new Product(name, "");
		return itemsName.search(n, new nameComparator());
	}

	/**
	 * Search for a product by secondary key type
	 * 
	 * @param type the type of the product
	 * @return the Product found or null
	 */
	public static Product searchType(String type) { // move to database
		Product t = new Product("", type);
		return itemsType.search(t, new typeComparator());
	}

	/**
	 * Add a new product to the BST
	 * 
	 * @param p Product to remove
	 */
	public static void addProduct(Product p) {
		itemsName.insert(p, new nameComparator());
		itemsType.insert(p, new typeComparator());
	}

	/**
	 * Removes a new product to the BST
	 * 
	 * @param p Product to add
	 */
	public static void removeProduct(Product p) {
		itemsName.remove(p, new nameComparator());
		itemsType.remove(p, new typeComparator());
	}

	/**
	 * Creates a Product String in the following format
	 * Name: <name>
	 * Type: <year>
	 * Calories: <calories>
	 * Best By Date: <best by>
	 * Price: $<price to two decimal places>
	 * Num In Stock: <numInStock>
	 */
	@Override
	public String toString() {
		DecimalFormat df = new DecimalFormat("$0.00");
		return "\nName: " + this.getName()
				+ "\nType: " + type
				+ "\nCalories: " + calories
				+ "\nBest By Date: " + bestby
				+ "\nPrice: " + df.format(getPrice())
				+ "\nDescription: " + description
				+ "\nNum in Stock: " + getNumInStock() + "\n";

	}

	/**
	 * populate the store with products from Catalogue
	 * 
	 * @param input Scanner
	 * 
	 */
	public static void populateCatalogue() { // in database or no?
		try {
			Scanner input = new Scanner(new File("./src/Database/Catalogue.txt"));
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

				Product p = new Product(name, type, calories, bestby, price, description, numInStock);
				itemsName.insert(p, new nameComparator());
				itemsType.insert(p, new typeComparator());

			}
		} catch (IOException e) {
			// TODO: handle exception
		}

	}// populateCatalogue()

}