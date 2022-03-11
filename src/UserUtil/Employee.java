package UserUtil;

/**
 * MegaMart
 * Employee Class - 22C Course Project
 */

public class Employee extends User {
    private boolean isManager;

    public Employee() {

    }

    public Employee(String[] data) {
        super(data[0], data[1], data[2], data[3]);
        this.isManager = data[4].equals("true");
    }

    public Employee(String userName, String password) {
        super(userName, password);
    }

    public Employee(String firstName, String lastName, String login, String password, boolean isManager) {
        super(firstName, lastName, login, password);
        this.isManager = isManager;
    }

    public boolean isManager() {
        return isManager;
    }

    public void setManager(boolean isManager) {
        this.isManager = isManager;
    }

    public void listLoginOptions() {

    }

    @Override
    public String toString() {
        String manager = isManager ? "true" : "false";
        return String.format("%s\n%s\n%s\n%s\n%s", firstName, lastName, userName, password, manager);
    }

}
