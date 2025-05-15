
package classes;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

public class Vendor extends User {
    private Menu menu;
    private List<Order> orderHistory; // Assume this is populated from orders.txt
    
    // Constructor
    public Vendor(){}
    
    
    
    public Vendor(String userId, String password, String name, String email) {
        super(userId, password, name, email, User.Role.Vendor);
        this.orderHistory = new ArrayList<>();
    }
    
    
    public Vendor(String userId, String password, String name, String email,Menu menu) {
        super(userId, password, name, email, User.Role.Vendor);
        this.menu = menu;
        this.orderHistory = new ArrayList<>();
    }
    public Vendor(String userId, String password, String name, String email,Menu menu, Wallet wallet) {
        super(userId, password, name, email, User.Role.Vendor, wallet);
        this.menu = menu;
        this.orderHistory = new ArrayList<>();
    }
    
    // Getters and Setters for menu and orderHistory
    public Menu getMenu() {
        return menu;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }

    public List<Order> getOrderHistory() {
        return orderHistory;
    }

    public void setOrderHistory(List<Order> orderHistory) {
        this.orderHistory = orderHistory;
    }


    // Read customer reviews
     public List<String> readCustomerReviews() {
        List<String> reviews = new ArrayList<>();
        for (Order order : orderHistory) {
            if (order.getReview() != null && !order.getReview().isEmpty()) {
                reviews.add(order.getReview());
            }
        }
        return reviews;
    }

    // CRUD operations for menu items
    public void createMenuItem(MenuItem item) {
        this.menu.addItem(item);
    }

    public MenuItem readMenuItem(String itemId) {
        return this.menu.getItems().stream()
                .filter(item -> item.getItemId().equals(itemId))
                .findFirst()
                .orElse(null);
    }

    public void updateMenuItem(MenuItem updatedItem) {
        this.menu.getItems().removeIf(item -> item.getItemId().equals(updatedItem.getItemId()));
        this.menu.addItem(updatedItem);
    }

    public void deleteMenuItem(String itemId) {
        this.menu.getItems().removeIf(item -> item.getItemId().equals(itemId));
    }
    

    
    public boolean changeOrderStatus(String orderId, Order.OrderStatus orderStatus) {
        // Find the order in the order history
        Optional<Order> optionalOrder = orderHistory.stream()
                .filter(order -> order.getOrderId().equals(orderId))
                .findFirst();

        if (optionalOrder.isPresent()) {
            Order orderToChange = optionalOrder.get();
            FileManager fm = new FileManager();
            // Check if the vendor can change the status to the specified status
            if (canChangeOrderStatus(orderToChange, orderStatus)) {
                // Update the order status
                
                orderToChange.setOrderStatus(orderStatus);

                // Write the updated orders back to the "orders.txt" file
                List<Order> allOrders = fm.readOrders();
                for (Order order : allOrders) {
                    if (order.getOrderId().equals(orderToChange.getOrderId())) {
                        order.setOrderStatus(orderStatus);
                    }
                }
                fm.writeOrders(allOrders, false); // Overwrite the file

                // Create notifications for both vendor and customer
                String notificationMsg = "Order " + orderId + " has been " + orderStatus.toString().toLowerCase() + ".";
                String customerId = orderToChange.getCustomerId();

                Notification customerNotification = new Notification(
                        Controller.generateNotificationId(customerId),
                        customerId,
                        notificationMsg,
                        LocalDateTime.now(),
                        null // You can set the transaction to null for customer notification
                );

                String vendorId = getUserId();
                Notification vendorNotification = new Notification(
                        Controller.generateNotificationId(vendorId),
                        vendorId,
                        notificationMsg,
                        LocalDateTime.now(),
                        null // You can set the transaction to null for vendor notification
                );

                fm.writeNotification(customerNotification, true);
                fm.writeNotification(vendorNotification, true);
                
                // Handle refunds if necessary
                if (orderStatus == Order.OrderStatus.CANCELLED) {
                    // Refund the customer's wallet
                    double refundAmount = orderToChange.getTotalAmount();
                    Customer customer = (Customer) fm.getUserById(orderToChange.getCustomerId());
                    customer.getWallet().addBalance(refundAmount, orderToChange.getCustomerId());
                } else if (orderStatus == Order.OrderStatus.COMPLETED) {
                    // Order is completed, add balance to the vendor
                    double totalAmount = orderToChange.getTotalAmount();
                    double vendorBalanceToAdd = totalAmount;

                    // If the dining option is delivery, deduct RM3
                    if (orderToChange.getDiningOption() == Order.DiningOption.DELIVERY) {
                        vendorBalanceToAdd -= 3.0;
                    }

                    // Add the balance to the vendor's wallet
                    getWallet().addBalance(vendorBalanceToAdd, orderToChange.getVendorId());
                }

                return true; // Order status changed successfully
            }
        }

        return false; // Order not found or status change not allowed
    }

    private boolean canChangeOrderStatus(Order order, Order.OrderStatus newStatus) {
        // Check if the current order status is not "COMPLETED" or "CANCELLED"
        if (order.getOrderStatus() != Order.OrderStatus.COMPLETED && order.getOrderStatus() != Order.OrderStatus.CANCELLED) {
            return true; // Status change allowed
        }

        return false; // Status change not allowed if order is completed or cancelled
    }

}
