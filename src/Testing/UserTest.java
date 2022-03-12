package Testing;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import Database.Database;
import UserUtil.Customer;

public class UserTest {

        @Test
        void testAddUser() {
                assertEquals(Database.Status.Success,
                                Database.addUser(
                                                new Customer("firstName", "lastName", "login", "password", "address",
                                                                "city", "state",
                                                                "2222")));
                assertEquals(Database.Status.Failed, Database.addUser(
                                new Customer("firstName", "lastName", "login", "password", "address", "city", "state",
                                                "zip")));

                Customer cust1 = (Customer) Database.login("login", "password",
                                Database.UserType.Customer);
                // assertEquals("Customer [address=address, city=city, state=state, zip=2222]",
                // cust1.toString());
        }
}
