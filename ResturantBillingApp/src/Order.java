public class Order {
    private String orderName;
    private double price;

    public Order(String orderName, double price) {
        this.orderName = orderName;
        this.price = price;
    }

    public String getOrderName() {
        return orderName;
    }

    public double getPrice() {
        return price;
    }
}
