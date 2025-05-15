
package classes;


import java.util.ArrayList;
import java.util.List;

public class Menu {
    private Vendor vendor;
    private List<MenuItem> items;

    // Constructor
    public Menu(){};
    
    public Menu(Vendor vendor) {
        this.items = new ArrayList<>();
        this.vendor = vendor;   
    }

    // Method to add a menu item
    public void addItem(MenuItem item) {
        items.add(item);
    }

    // Method to remove a menu item
    public void removeItem(String itemId) {
        items.removeIf(item -> item.getItemId().equals(itemId));
    }

    // Getters and Setters
    public List<MenuItem> getItems() {
        return items;
    }

    public void setItems(List<MenuItem> items) {
        this.items = items;
    }
    public String getVendorId() {
        return vendor.getUserId();
    }
    public Vendor getVendor() {
        return vendor;
    }
    
    public int getAverageRatings(){
        List<Order> vendorOrders= vendor.getOrderHistory();
        int sumRating = 0;
        int orderCount = 0;
        for(Order order:vendorOrders){
            if (order.getOrderStatus().equals(Order.OrderStatus.COMPLETED)&&order.getRating()!= 0){
                sumRating += order.getRating();
                orderCount += 1;
            }
        }
        int avgRating = 0;
        try{
            avgRating = sumRating/orderCount;
        }catch(ArithmeticException e){
           avgRating = 0;
        }
        return avgRating;
    }
    
    public List<String> getAllReviews(){
        List<Order> vendorOrders= vendor.getOrderHistory();
        List<String> allReviews = new ArrayList();
        
        for(Order order:vendorOrders){
            if (order.getOrderStatus().equals(Order.OrderStatus.COMPLETED)&&!"null".equals(order.getReview())){
                allReviews.add(order.getReview()) ;
            }
        }
        
        return allReviews;
    }

    
}

