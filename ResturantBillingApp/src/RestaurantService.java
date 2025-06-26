import java.util.*;

public class RestaurantService {
    private List<Customer> customers;
    private List<Order> availableOrders;

    public RestaurantService() {
        this.customers = new ArrayList<>();
        this.availableOrders = new ArrayList<>();
        addServices();
    }

    private void addServices() {
        availableOrders.add(new Order("Cheese Pizza", 150));
        availableOrders.add(new Order("Veggie Pizza", 120));
        availableOrders.add(new Order("Large Pizza", 300));
        availableOrders.add(new Order("Small Pizza", 200));
        availableOrders.add(new Order("Garlic Pizza", 100));
    }

    public void addCustomer(String name, String phone) {
        Customer customer = new Customer(name, phone);
        customers.add(customer);
        System.out.println("Customer added successfully.");
    }

    public void createInvoice(String phone) {
        Customer customer = findCustomerByPhone(phone);
        if (customer == null) {
            System.out.println("No customer found with this phone number, please try again!");
            return;
        }

        Scanner sc = new Scanner(System.in);
        Invoice invoice = new Invoice(customer);

        System.out.println("Available Services:");
        for (int i = 0; i < availableOrders.size(); i++) {
            System.out.println((i + 1) + ". " + availableOrders.get(i).getOrderName() + " - " + availableOrders.get(i).getPrice());
        }

        while (true) {
            System.out.print("Choose an item number or press 0 to print Invoice: ");
            if (!sc.hasNextInt()) {
                System.out.println("Invalid input. Please enter a number.");
                sc.next();
                continue;
            }
            int choice = sc.nextInt();
            if (choice == 0) {
                break;
            }
            if (choice > 0 && choice <= availableOrders.size()) {
                invoice.addOrder(availableOrders.get(choice - 1));
                System.out.println("Order added.");
            } else {
                System.out.println("Invalid choice. Try again.");
            }
        }
        invoice.printInvoice();
    }

    private Customer findCustomerByPhone(String phone) {
        for (Customer customer : customers) {
            if (customer.getPhone().equals(phone)) {
                return customer;
            }
        }
        return null;
    }
}
