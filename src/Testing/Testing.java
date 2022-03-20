package Testing;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Comparator;

import org.junit.jupiter.api.Test;

import Database.Database;
import OrderUtil.Heap;
import OrderUtil.Order;
import OrderUtil.Order.ShippingSpeed;
import UserUtil.Customer;
import UserUtil.Employee;

public class Testing {

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
                assertEquals(Database.Status.Success, Database.login("login", "password"));
        }

        @Test
        void testViewOrderHighestPri() {
                Customer c = new Customer("firstName", "lastName", "login", "password", "address",
                                "city", "state",
                                "2222");
                Order o = new Order(c, ShippingSpeed.OVERNIGHT);
                Database.placeOrder(o, c);
                assertEquals(o.toString(), Database.priorityOrder().toString());
        }

        @Test
        void testViewAllOrders() {
                Customer c = new Customer("firstName", "lastName", "login", "password", "address",
                                "city", "state",
                                "2222");
                Order o = new Order(c, ShippingSpeed.OVERNIGHT);
                Order o1 = new Order(c, ShippingSpeed.RUSH);
                Database.placeOrder(o, c);
                Database.placeOrder(o1, c);
                assertEquals(o.toString() + o1.toString(), Database.priorityOrdersStr());
        }

        @Test
        void testShipOrder() {
                Customer c = new Customer("firstName", "lastName", "login", "password", "address",
                                "city", "state",
                                "2222");
                Order o = new Order(c, ShippingSpeed.OVERNIGHT);
                Database.placeOrder(o, c);
                assertEquals(o.toString(), Database.priorityOrdersStr());
                Database.shipOrder(o); //
                assertEquals("", Database.priorityOrdersStr());

        }

        @Test
        void testHeapSearch() {
                Heap<Order> orders = new Heap<>(100);
                Order o = new Order(new Customer(), ShippingSpeed.OVERNIGHT);
                orders.insert(o, new IDcomparator());
                assertFalse(orders.isEmpty());
                // assertEquals(o, orders.search(new Order(), new IDcomparator()));
                assertEquals(o, orders.search(new Order(1001), new IDcomparator()));
        }

        @Test
        void testHeapRemove() {
                Heap<Order> orders = new Heap<>(100);
                Order o = new Order(new Customer(), ShippingSpeed.OVERNIGHT);
                orders.insert(o, new IDcomparator());
                assertFalse(orders.isEmpty());
                orders.remove(1, new IDcomparator());
                assertTrue(orders.isEmpty());
        }

        @Test
        void testHeapGetIndex() {
                Heap<Order> orders = new Heap<>(100);
                Order o = new Order(new Customer(), ShippingSpeed.OVERNIGHT);
                Order o1 = new Order(new Customer(), ShippingSpeed.OVERNIGHT);
                Order o2 = new Order(new Customer(), ShippingSpeed.OVERNIGHT);
                assertEquals(-1, orders.getIndex(o, new IDcomparator()));
                orders.insert(o, new IDcomparator());
                orders.insert(o1, new IDcomparator());
                orders.insert(o2, new IDcomparator());
                assertEquals(1, orders.getIndex(o, new IDcomparator()));
                assertEquals(2, orders.getIndex(o1, new IDcomparator()));
                assertEquals(3, orders.getIndex(o2, new IDcomparator()));
        }

        @Test
        void testHeapInsert() {
                Heap<Order> orders = new Heap<>(100);
                Order o = new Order(new Customer(), ShippingSpeed.OVERNIGHT);
                Order o1 = new Order(new Customer(), ShippingSpeed.OVERNIGHT);
                Order o2 = new Order(new Customer(), ShippingSpeed.OVERNIGHT);
                orders.insert(o, new IDcomparator());
                assertEquals(1, orders.getHeapSize());
                assertEquals(o.toString(), orders.toString());
                orders.insert(o1, new IDcomparator());
                assertEquals(2, orders.getHeapSize());
                assertEquals(o.toString() + o1.toString(), orders.toString());
                orders.insert(o2, new IDcomparator());
                assertEquals(o.toString() + o1.toString() + o2.toString(),
                                orders.toString());
        }

        @Test
        void testPrioritySort() {
                Heap<Order> orders = new Heap<>(100);
                Order o = new Order(new Customer(), ShippingSpeed.OVERNIGHT);
                Order o1 = new Order(new Customer(), ShippingSpeed.RUSH);
                Order o2 = new Order(new Customer(), ShippingSpeed.STANDARD);
                orders.insert(o1, new PriorityComparator());
                assertEquals(1, orders.getHeapSize());
                assertEquals(o1.toString(), orders.toString());
                orders.insert(o, new PriorityComparator());
                assertEquals(2, orders.getHeapSize());
                assertEquals(o.toString() + o1.toString(), orders.toString());
                orders.insert(o2, new PriorityComparator());
                assertEquals(o.toString() + o1.toString() + o2.toString(),
                                orders.toString());
        }

        @Test
        void testPlaceOrder() {
                Customer c = new Customer("firstName", "lastName", "login", "password", "address",
                                "city", "state",
                                "2222");
                Order o = new Order(c, ShippingSpeed.OVERNIGHT);
                Order o1 = new Order(c, ShippingSpeed.OVERNIGHT);
                Order o2 = new Order(c, ShippingSpeed.OVERNIGHT);
                Database.placeOrder(o, c);
                assertEquals(o.toString(), Database.priorityOrdersStr());
                Database.placeOrder(o1, c);
                assertEquals(o.toString() + o1.toString(), Database.priorityOrdersStr());
                Database.placeOrder(o2, c);
                assertEquals(o.toString() + o1.toString() + o2.toString(),
                                Database.priorityOrdersStr());
        }

        @Test
        void testSearchBYName() {
                Customer c = new Customer("firstName", "lastName", "login", "password", "address",
                                "city", "state",
                                "2222");
                Order o = new Order(c, ShippingSpeed.OVERNIGHT);
                Order o1 = new Order(c);
                assertEquals("firstName lastName", o1.getCName());
                Database.placeOrder(o, c);
                assertEquals("firstName lastName", o.getCName());
                assertEquals(o.toString(), Database.searchOrderCust(new Customer("firstName", "lastName", true)));

                // assertEquals(o.toString(), Database.priorityOrdersStr());
        }

        class PriorityComparator implements Comparator<Order> {
                @Override
                public int compare(Order p1, Order p2) {
                        if (p1.equals(p2)) {
                                return 0;
                        }
                        return Integer.compare(p1.getPriority(), (p2.getPriority()));
                }
        }

        class CustomerComparator implements Comparator<Order> {

                @Override
                public int compare(Order o1, Order o2) {
                        if (o1.equals(o2)) {
                                return 0;
                        }

                        return o1.getCName().compareTo(o2.getCName());
                }
        }

        /**
         * Comparator for Heap class
         */
        class IDcomparator implements Comparator<Order> {

                @Override
                public int compare(Order o1, Order o2) {
                        if (o1 == null && o2 != null) {
                                return -1;
                        } else if (o1 != null && o2 == null) {
                                return 1;
                        } else if (o1 == null && o2 == null) {
                                return 0;
                        }
                        if (o1.equals(o2)) {
                                return 0;
                        }
                        return Integer.compare(o1.getOrderID(), o2.getOrderID());
                }
        }
}
