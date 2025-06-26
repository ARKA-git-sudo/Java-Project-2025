import java.util.ArrayList;
import java.util.List;

public class Invoice {
    private Customer customer;
    private List<Order> orderList;
    private double totalAmount;

    public Invoice(Customer customer) {
        this.customer = customer;
        this.orderList = new ArrayList<>();
        this.totalAmount = 0;
    }

    public void addOrder(Order order) {
        orderList.add(order);
        totalAmount += order.getPrice();
    }

    public void printInvoice() {
        System.out.println("\n____________________________ Invoice ____________________________");
        System.out.println("Customer name: " + customer.getName() + " | Phone no: " + customer.getPhone());
        System.out.println("Total Orders:");

        for (Order order : orderList) {
            System.out.println("=> " + order.getOrderName() + " | ₹" + order.getPrice());
        }
        System.out.println("Total amount: ₹" + totalAmount);
        System.out.println("____________________ Thank You | Please visit again ____________________\n");
    }
}
