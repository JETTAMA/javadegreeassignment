package classes;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class Customer extends User {
    private List<Order> orderHistory;

    // Enum for Order Type
    public enum OrderType {
        DINE_IN, TAKEAWAY, DELIVERY
    }
    
    // Constructor
    public Customer(String userId, String password, String name, String email) {
        super(userId, password, name, email, User.Role.Customer);
        this.orderHistory = new ArrayList<>();
    }
    public Customer(String userId, String password, String name, String email, Wallet wallet) {
        super(userId, password, name, email, User.Role.Customer, wallet);
        this.orderHistory = new ArrayList<>();
    }
    public Customer(String userId, String password, String name, String email, Wallet wallet, List<Order> orderHistory) {
        super(userId, password, name, email, User.Role.Customer, wallet);
        this.orderHistory = orderHistory;
    }

    // Getters and Setters
    public List<Order> getOrderHistory() {
        return orderHistory;
    }

    public void setOrderHistory(List<Order> orderHistory) {
        this.orderHistory = orderHistory;
    }

    // Methods
    public void placeOrder(Order order){
        if(getWallet().deductBalance(order, getUserId())){
            orderHistory.add(order);
            Controller controller = new Controller();
            FileManager fm = new FileManager();
            String notificationMsg = "Order has been placed.";
            Notification orderPlacedNotification = new Notification(controller.generateNotificationId(getUserId()), getUserId(), notificationMsg, getWallet().getLastTransaction().getDateTime(), getWallet().getLastTransaction());
            Notification vendorOrderPlacedNotification = new Notification(controller.generateNotificationId(order.getVendorId()), order.getVendorId(),notificationMsg, getWallet().getLastTransaction().getDateTime(), null);
            
            fm.writeNotification(orderPlacedNotification, true);
            fm.writeNotification(vendorOrderPlacedNotification, true);
            setNotifications(fm.getNotificationsForUser(getUserId()));
        }
        
    }

    public boolean cancelOrder(String orderId) {
        Optional<Order> optionalOrder = orderHistory.stream()
            .filter(order -> order.getOrderId().equals(orderId))
            .findFirst();

        if (optionalOrder.isPresent()) {
            Order orderToCancel = optionalOrder.get();

            // Check if the order is in a cancellable state (e.g., not already canceled or delivered)
            if (orderToCancel.getOrderStatus() != Order.OrderStatus.CANCELLED
                && orderToCancel.getDeliveryStatus() != Order.DeliveryStatus.DELIVERED) {

                // Refund the order amount to the customer's wallet
                double refundAmount = orderToCancel.getTotalAmount();
                getWallet().addBalance(refundAmount, getUserId());
                    // Update the order status to CANCELLED
                    orderToCancel.setOrderStatus(Order.OrderStatus.CANCELLED);

                    // Write the updated orders back to the "orders.txt" file
                    FileManager fm = new FileManager();
                    List<Order> allOrders = fm.readOrders();
                    for (Order order : allOrders) {
                        if (order.getOrderId().equals(orderToCancel.getOrderId())) {
                            order.setOrderStatus(Order.OrderStatus.CANCELLED);
                        }
                    }
                    fm.writeOrders(allOrders, false); // Overwrite the file

                    // Create notifications for both vendor and customer
                    String notificationMsg = "Order " + orderId + " has been canceled.";
                    String customerId = getUserId();
                    String vendorId = orderToCancel.getVendorId();

                    Notification customerNotification = new Notification(
                        Controller.generateNotificationId(customerId),
                        customerId,
                        notificationMsg,
                        LocalDateTime.now(),
                        null // You can set the transaction to null for customer notification
                    );

                    Notification vendorNotification = new Notification(
                        Controller.generateNotificationId(vendorId),
                        vendorId,
                        notificationMsg,
                        LocalDateTime.now(),
                        null // You can set the transaction to null for vendor notification
                    );

                    fm.writeNotification(customerNotification, true);
                    fm.writeNotification(vendorNotification, true);

                    return true; // Order canceled successfully
                }
            }
        

        return false; // Order not found, not cancellable, or refund failed
    }


    public void rateAndReviewOrder(Order feedbackOrder, int rating, String review,int delrating, String delreview) {
        feedbackOrder.setRating(rating);
        feedbackOrder.setReview(review);
        feedbackOrder.setDeliveryRating(delrating);
        feedbackOrder.setDeliveryReview(delreview);
        FileManager fm = new FileManager();
        List<Order> allOrders = fm.readOrders();
                for (Order order : allOrders) {
                    if (order.getOrderId().equals(feedbackOrder.getOrderId())) {
                        order.setRating(feedbackOrder.getRating());
                        order.setReview(feedbackOrder.getReview());
                        order.setDeliveryReview(feedbackOrder.getDeliveryReview());
                        order.setDeliveryRating(feedbackOrder.getDeliveryRating());
                    }
                }
                fm.writeOrders(allOrders, false); // Overwrite the file
        setOrderHistory(getOrderHistory());
    }

     
    public void reorder(Order selectedOrder, Order.DiningOption selectedDiningOption, String classroomForDelivery) {

            Order newOrder = new Order(Controller.generateOrderId(getUserId()), getUserId(), selectedOrder.getVendorId(),
                                selectedOrder.getMenuItems(), selectedDiningOption, classroomForDelivery);
            placeOrder(newOrder);

            FileManager fm = new FileManager();
            List<Order> orders2 = new ArrayList<>();
            orders2.add(newOrder);
            fm.writeOrders(orders2, true);
            orderHistory.add(newOrder);
    }

}
