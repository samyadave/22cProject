/**
 * MegaMart
 * User abstract Class - 22C Course Project
 */

public abstract class User {
    protected String firstName;
    protected String lastName;
    protected String userName;
    protected String password;

    public User(String firstName, String lastName, String login, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = login;
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
     * Authenticates credentials and returns the proper User object
     * 
     * @return the logged in User
     */
    public static User login(String userName, String password) {
        return null;
    }

    /**
     * Helper method to check if credentials are valid
     * 
     * @return if credentials are valid
     */
    static boolean authenticateLogin(String userName, String password) {
        return false;
    }

}
