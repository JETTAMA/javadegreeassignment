package classes;

public class MenuItem {
    private String itemId;       // Unique identifier for the menu item
    private String itemCuisine;
    private String itemName;     // Name of the menu item
    private String description;  // Description of the menu item
    private double price;        // Price of the menu item

    // Constructor to initialize a menu item
    public MenuItem(String itemId,String itemCuisine, String itemName, String description, double price) {
        this.itemId = itemId;
        this.itemCuisine = itemCuisine;
        this.itemName = itemName;
        this.description = description;
        this.price = price;
    }

    // Getter and Setter for itemId
    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getItemCuisine() {
        return itemCuisine;
    }

    public void setItemCuisine(String itemCuisine) {
        this.itemCuisine = itemCuisine;
    }
    
    // Getter and Setter for itemName
    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    // Getter and Setter for description
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    // Getter and Setter for price
    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

}
