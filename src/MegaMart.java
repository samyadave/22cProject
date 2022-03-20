
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

public class MegaMart {
    public static void main(String[] args) {
        Database.startUp();
        UserInterface u = new UserInterface();
        u.run();
    }

}