
package classes;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class DeliveryRunner extends User {
    private List<Order> assignedOrders;
    private List<Order> completedOrders;

    // Constructor
    public DeliveryRunner(){
        
    }
    
    public DeliveryRunner(String userId, String password, String name, String email) {
        super(userId, password, name, email, User.Role.DeliveryRunner);
        this.assignedOrders = new ArrayList<>();
        this.completedOrders = new ArrayList<>();
        
    }
    public DeliveryRunner(String userId, String password, String name, String email, Wallet wallet) {
        super(userId, password, name, email, User.Role.DeliveryRunner, wallet);
        this.assignedOrders = new ArrayList<>();
        this.completedOrders = new ArrayList<>();
    }

    // Getters and Setters for assignedOrders and completedOrders
    public List<Order> getAssignedOrders() {
        return assignedOrders;
    }

    public void setAssignedOrders(List<Order> assignedOrders) {
        this.assignedOrders = assignedOrders;
    }

    public List<Order> getCompletedOrders() {
        return completedOrders;
    }

    public void setCompletedOrders(List<Order> completedOrders) {
        this.completedOrders = completedOrders;
    }

    // Method to view assigned orders
    public List<Order> viewAssignedOrders() {
        return new ArrayList<>(assignedOrders);
    }

    public void changeDeliveryStatus(String orderId, Order.DeliveryStatus deliveryStatus) {
        FileManager fm = new FileManager();
        Order currentOrder = fm.getOrderByOrderId(orderId);

        // Update the delivery status of the current order
        currentOrder.setDeliveryStatus(deliveryStatus);

        if (deliveryStatus == Order.DeliveryStatus.PENDING) {
            // Add the current delivery runner to the rejected list
            currentOrder.addRejectedDeliveryRunner(getUserId());

            // Auto-assign a new delivery runner
            String newRunnerId = Controller.autoAssignDeliveryRunner(currentOrder);
            currentOrder.setDeliveryRunnerId(newRunnerId);
            if (newRunnerId == null){
                deliveryStatus = Order.DeliveryStatus.REJECTED;
                currentOrder.setDeliveryStatus(deliveryStatus);
            }
        }

        // Retrieve all orders from the file
        List<Order> allOrders = fm.readOrders();

        // Update the delivery status of all orders with the same order ID
        for (Order order : allOrders) {
            if (order.getOrderId().equals(currentOrder.getOrderId())) {
                order.setDeliveryStatus(currentOrder.getDeliveryStatus());
                order.setDeliveryRunnerId(currentOrder.getDeliveryRunnerId());
                order.setRejectedDeliveryRunners(currentOrder.getRejectedDeliveryRunners());
            }
        }

        // Write all orders back to the file
        fm.writeOrders(allOrders, false); // Overwrite the file

        // Create notifications and handle refunds as before
//        Create notifications for both deliveryrunner and customer
        if (deliveryStatus != Order.DeliveryStatus.PENDING && deliveryStatus != Order.DeliveryStatus.REJECTED ){        
            String notificationMsg = "Order " + orderId + " delivery status is now: " + deliveryStatus.toString() + ".";
            String customerId = currentOrder.getCustomerId();
            Notification customerNotification = new Notification(
                    Controller.generateNotificationId(customerId),
                    customerId,
                    notificationMsg,
                    LocalDateTime.now(),
                    null
            );

            String deliveryRunnerId = getUserId();
            Notification runnerNotification = new Notification(
                    Controller.generateNotificationId(deliveryRunnerId),
                    deliveryRunnerId,
                    notificationMsg,
                    LocalDateTime.now(),
                    null
            );

            fm.writeNotification(customerNotification, true);
            fm.writeNotification(runnerNotification, true); 
        }else if (deliveryStatus == Order.DeliveryStatus.REJECTED){
            String notificationMsg = "ACTION NEEDED: Your order " + orderId + " cannot be delivered. Please choose to dine-in or takeaway. Take note that a refund for the delivery charges is made";
            String customerId = currentOrder.getCustomerId();
            Notification customerNotification = new Notification(
                    Controller.generateNotificationId(customerId),
                    customerId,
                    notificationMsg,
                    LocalDateTime.now(),
                    null
            );
            fm.writeNotification(customerNotification, true);
        }
        // Handle refunds if necessary
        if (deliveryStatus == Order.DeliveryStatus.REJECTED) {
            // Refund the customer's wallet, RM3 because standard delivery charges
            double refundAmount = 3;
            Customer customer = (Customer) fm.getUserById(currentOrder.getCustomerId());
            customer.getWallet().addBalance(refundAmount, currentOrder.getCustomerId());
        } else if (deliveryStatus == Order.DeliveryStatus.DELIVERED) {
            // Order is completed, add balance to the runner
            double totalAmount = 3;
            // Add the balance to the runner's wallet
            getWallet().addBalance(totalAmount, currentOrder.getDeliveryRunnerId());
        }
    }

    // Method to view completed orders
    public List<Order> viewCompletedOrders() {
        return new ArrayList<>(completedOrders);
    }

    // Method to calculate earnings from completed deliveries
    public double calculateEarnings() {
        double earningPerDelivery = 3.0; // Fixed rate per delivery
        return completedOrders.size() * earningPerDelivery;
    }

}

