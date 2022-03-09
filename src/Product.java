import java.text.DecimalFormat;
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

    BST<Product> items = new BST<>();

    /**
     * populate the store with products from Catalogue
     * @param input Scanner
     * @throws IOException
     */
    public void populateCatalogue(Scanner input) throws IOException {
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
			bestby = input.nextLine();
			price = input.nextDouble();
            description = input.nextLine();
			numInStock = input.nextInt();

			if (input.hasNextLine()) {
				input.nextLine();
				input.nextLine();
			}

            Product p = new Product(name, type, calories, bestby, price, description, numInStock);
			items.insert(p);

            //why two BSTS

		}

	}// populateCatalogue()

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
	 * @param name  name of product
	 * @param type  type of product
	 */
	public Product(String name, String type) {
		this(name, type, 0, "00/00/00", 0.0, "description unknown", 0);
	}

	/**
	 * Constructor for Product
	 * 
	 * @param name       Sets name default to "name unknown" 
     * @param type       Sets type default to "type unknown"
     * @param calories   Sets calories default to 0
     * @param bestby     Sets bestby to "00/00/00"
     * @param price      Sets price to 0.0 
     * @param description Sets description to "description unknown"
	 * @param numInStock  the current stock
	 */
	public Product(String name, String type, int calories, String bestby, double price, String description, int numInStock) {
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

	/*
	 * returns the BST of products
     * //ask the ppl
	 */
	public BST<Product> getBST() {
		return items;
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
		return "Name: " + this.getName() 
            + "\nType: " + type 
            + "\nCalories: " + calories 
            + "\nBest By Date: " + bestby 
            + "\nPrice: " + df.format(getPrice()) 
            + "\nDescription: " + description
            + "\nNum in Stock: " + getNumInStock();

	}

}