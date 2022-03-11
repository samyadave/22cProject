package UserUtil;

/**
 * MegaMart
 * User abstract Class - 22C Course Project
 */

public abstract class User {
    protected String firstName;
    protected String lastName;
    protected String userName;
    protected String password;

    public User() {

    }

    public User(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public User(String firstName, String lastName, String username, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = username;
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public final void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public final String getLastName() {
        return lastName;
    }

    public final void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public final String getLogin() {
        return userName;
    }

    public final void setLogin(String login) {
        this.userName = login;
    }

    public final String getPassword() {
        return password;
    }

    public final void setPassword(String password) {
        this.password = password;
    }

    /**
     * Hashcode based on username and password to
     * allow access to same object with only
     * correct username and password
     */
    @Override
    public int hashCode() {
        final int prime = 31;

        return prime * userName.hashCode() + password.hashCode();
    }

}
