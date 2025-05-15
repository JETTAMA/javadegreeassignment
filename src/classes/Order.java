package classes;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

public class Order {
    private String orderId;
    private String customerId;
    private String vendorId;
    private Map<MenuItem, Integer> menuItems; 
    private double totalAmount;
    private OrderStatus orderStatus;
    private DeliveryStatus deliveryStatus;
    private LocalDateTime orderTime;
    private int rating;
    private String review;
    private DiningOption diningOption; // New attribute for dining option
    private String classroomForDelivery;
    private int delRating;
    private String delReview;
    
    private String deliveryRunnerId;
    private List<String> rejectedDeliveryRunners;

   
    // Enum for Order Status
    public enum OrderStatus {
        PENDING, ACCEPTED, COMPLETED, CANCELLED
    }

    // Enum for Delivery Status
    public enum DeliveryStatus {
        NOT_AVAILABLE,PENDING, ASSIGNED, EN_ROUTE, DELIVERED, REJECTED
    }
    
    // Enum for Dining Option
    public enum DiningOption {
        DINE_IN, TAKEAWAY, DELIVERY
    }

//     Order order = new Order(orderId, customerId, vendorId, menuItems);
     public Order(String orderId, String customerId, String vendorId, Map<MenuItem, Integer> menuItems) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.vendorId = vendorId;
        this.menuItems = menuItems;
        this.rejectedDeliveryRunners = new ArrayList<>();
    }
     
    public Order(String orderId, String customerId, String vendorId, Map<MenuItem, Integer> menuItems, DiningOption diningOption, String classroomForDelivery) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.vendorId = vendorId;
        this.menuItems = menuItems;
        this.diningOption = diningOption;
        if (diningOption == DiningOption.DINE_IN || diningOption == DiningOption.TAKEAWAY) {
            // Set delivery status and classroom to default values for dine-in and takeaway
            this.deliveryStatus = DeliveryStatus.NOT_AVAILABLE;
            this.classroomForDelivery = null;
        } else if (diningOption == DiningOption.DELIVERY) {
            this.deliveryStatus = DeliveryStatus.PENDING;
            this.classroomForDelivery = classroomForDelivery;
            
        }
        this.totalAmount = calculateTotalAmount(); // Calculate the total amount based on the items and dining option
        this.orderStatus = OrderStatus.PENDING; // Default status
        this.orderTime = LocalDateTime.now(); // Set the current time as the order time
        this.rejectedDeliveryRunners = new ArrayList<>();
    }


    private double calculateTotalAmount() {
        double baseTotal = menuItems.entrySet()
                                .stream()
                                .mapToDouble(entry -> entry.getKey().getPrice() * entry.getValue())
                                .sum();

        if (diningOption == DiningOption.DELIVERY) {
            // Add 3 to the total amount for delivery
            baseTotal += 3.0;
        }

        return baseTotal;
    }


    // Getters and Setters
    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getVendorId() {
        return vendorId;
    }

    public void setVendorId(String vendorId) {
        this.vendorId = vendorId;
    }


    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }
    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public DeliveryStatus getDeliveryStatus() {
        return deliveryStatus;
    }

    public void setDeliveryStatus(DeliveryStatus deliveryStatus) {
        this.deliveryStatus = deliveryStatus;
    }

    public LocalDateTime getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(LocalDateTime orderTime) {
        this.orderTime = orderTime;
    }
    
    
    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }
    
    public DiningOption getDiningOption() {
        return diningOption;
    }

    public void setDiningOption(DiningOption diningOption) {
        this.diningOption = diningOption;
        if (diningOption == DiningOption.DINE_IN || diningOption == DiningOption.TAKEAWAY) {
            // Set delivery status and classroom to default values for dine-in and takeaway
            this.deliveryStatus = DeliveryStatus.NOT_AVAILABLE;
            this.classroomForDelivery = null;
        } else if (diningOption == DiningOption.DELIVERY) {
            this.deliveryStatus = DeliveryStatus.PENDING;
        }
    }
    
//    when customers need to update their diningoption when delivery is rejected
    public void set2ndDiningOption(DiningOption diningOption){
        this.diningOption = diningOption;
    }
    public String getClassroomForDelivery() {
        return classroomForDelivery;
    }

    public void setClassroomForDelivery(String classroomForDelivery) {
        this.classroomForDelivery = classroomForDelivery;
    }
    
    public Map<MenuItem, Integer> getMenuItems() {
    return menuItems;
}

    public void setMenuItems(Map<MenuItem, Integer> menuItems) {
        this.menuItems = menuItems;
        // Recalculate the total amount whenever menu items are updated
        this.totalAmount = calculateTotalAmount();
    }
    
    public String formatMenuItems(Map<MenuItem, Integer> menuItems) {
        StringBuilder sb = new StringBuilder();

        for (Map.Entry<MenuItem, Integer> entry : menuItems.entrySet()) {
            MenuItem menuItem = entry.getKey();
            int quantity = entry.getValue();

            // Append the item name and quantity to the StringBuilder
            sb.append(menuItem.getItemName()).append(": ").append(quantity).append("; ");
        }

        // Remove the trailing "; " from the StringBuilder
        if (sb.length() > 0) {
            sb.setLength(sb.length() - 2);
        }

        return sb.toString();
    }
    
     public String getDeliveryRunnerId() {
        return deliveryRunnerId;
    }

    public void setDeliveryRunnerId(String deliveryRunnerId) {
        this.deliveryRunnerId = deliveryRunnerId;
    }

    public List<String> getRejectedDeliveryRunners() {
        return rejectedDeliveryRunners;
    }

    
public void addRejectedDeliveryRunner(String runnerId) {
        this.rejectedDeliveryRunners.add(runnerId);
}


    public void setRejectedDeliveryRunners(List<String> rejectedRunnerIds) {
        this.rejectedDeliveryRunners = rejectedRunnerIds;
    }
  

    public int getDeliveryRating() {
        return delRating;
    }

    public void setDeliveryRating(int delRating) {
        this.delRating = delRating;
    }

    public String getDeliveryReview() {
        return delReview;
    }

    public void setDeliveryReview(String delReview) {
        this.delReview = delReview;
    }

     
}
    
    
