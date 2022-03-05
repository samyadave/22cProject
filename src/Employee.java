/**
 * MegaMart
 * Employee Class - 22C Course Project
 */

public class Employee extends User {
    private boolean isManager;
    private static HashTable<String> employeeCredentials;

    /**
     * 
     * @param firstName
     * @param lastName
     * @param login
     * @param password
     * @param isManager
     */
    public Employee(String firstName, String lastName, String login, String password, boolean isManager) {
        super(firstName, lastName, login, password);
        this.isManager = isManager;
    }

    /**
     * 
     * @return whether the Employee is a manager
     */
    public boolean isManager() {
        return isManager;
    }

    /**
     * 
     * @param isManager
     */
    public void setManager(boolean isManager) {
        this.isManager = isManager;
    }

    public void listLoginOptions() {

    }

    public static boolean authenticateLogin(String username, String password) {
        if (employeeCredentials.contains(username + password)) {
            return true;
        }

        return false;
    }

    public Employee(boolean isManager) {
        this.isManager = isManager;
    }

    public boolean isManager() {
        return isManager;
    }

    public void setManager(boolean isManager) {
        this.isManager = isManager;
    }

}
