
/**
 * MegaMart.java
 * @author Meigan Lu
 * @author Eileen Huynh
 * @author Sol Valdimarsdottir
 * @author Sam Yadav
 * @author Brandon Ho
 * CIS 22C Course Project
 */

import Database.Database;
import ProductUtil.Product;

public class MegaMart {
    public static void main(String[] args) {
        Database.startUp();
        Product.populateCatalogue();
        UserInterface u = new UserInterface();
        u.run();
    }

}