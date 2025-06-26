import java.util.Scanner;

public class RestaurantBillingApp {
    public static void main(String[] args) {
        RestaurantService restaurantService = new RestaurantService();
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\n__________________________ GenZ Food Hub __________________________");
            System.out.println("1. Add Customer");
            System.out.println("2. Create Invoice");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");

            String input = sc.nextLine();
            int choice;
            try {
                choice = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number between 1 and 3.");
                continue;
            }

            switch (choice) {
                case 1:
                    System.out.print("Enter customer name: ");
                    String name = sc.nextLine();
                    System.out.print("Enter customer phone no: ");
                    String phone = sc.nextLine();
                    restaurantService.addCustomer(name, phone);
                    break;

                case 2:
                    System.out.print("Enter customer phone no: ");
                    String phoneNumber = sc.nextLine();
                    restaurantService.createInvoice(phoneNumber);
                    break;

                case 3:
                    System.out.println("Exiting... Thanks for visiting!");
                    return;

                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}
