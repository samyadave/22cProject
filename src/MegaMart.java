
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
import UserUtil.Employee;

public class MegaMart {
    public static void main(String[] args) {
        Database.startUp();
        Product.populateCatalogue();
        Database.addUser(new Employee("firstName", "lastName", "yayayay", "password", false));
        UserInterface u = new UserInterface();
        u.run();
    }

}