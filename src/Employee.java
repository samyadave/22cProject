public class Employee extends User {

    // note user is an abstract class containing first name, last name, login, and
    // password

    private boolean isManager;

    // getters, setters, constructors go here

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
