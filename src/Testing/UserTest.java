package Testing;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import Database.Database;
import UserUtil.Customer;
import UserUtil.Employee;

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
                assertEquals(Database.Status.Success,
                                Database.addUser(new Employee("firstName", "lastName", "yayayay", "password", false)));

                // assertEquals("Customer [address=address, city=city, state=state, zip=2222]",
                // cust1.toString());
        }

        @Test
        void testLogin() {
                assertEquals(Database.Status.Success,
                                Database.addUser(
                                                new Customer("firstName", "lastName", "login", "password", "address",
                                                                "city", "state",
                                                                "2222")));
                Database.startUp();
                assertEquals(Database.Status.Success, Database.login("manguy1", "password"));
        }
}
