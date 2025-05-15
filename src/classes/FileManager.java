
package classes;

import classes.*;
import java.io.*;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;
import java.time.LocalDateTime;
import java.util.function.*;

public class FileManager {
    


    private Map<String, MenuItem> menuItemMap;
    
    private final String userFile = "src/resources/users.txt";
    private final String menuFile = "src/resources/MenuItem.txt";
    private final String walletsFile = "src/resources/wallets.txt";
    private final String transactionsFile = "src/resources/transactions.txt";
    private final String notificationsFile = "src/resources/notifications.txt";
    private final String ordersFile = "src/resources/orders.txt";
    

    public FileManager(){
        // Create a Map to store MenuItem objects by item ID
         menuItemMap = setMenuItemMap();
    }


// Read lines from a file
    private List<String> readFromFile(String filePath) {
        List<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lines;
    }

    // Write lines to a file
    private void writeToFile(List<String> lines, String filePath, boolean append) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, append))) {
            for (String line : lines) {
                writer.write(line);
                writer.newLine();
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    

    public List<User> readUsers() {
        List<String> lines = readFromFile(userFile);
        List<User> users = lines.stream().map(this::parseUserFromLine).collect(Collectors.toList());

        // Read wallet balances and transactions for each user
        Map<String, Double> walletBalances = readWalletBalances();
        for (User user : users) {
            if (!(user instanceof Admin)) { //  Admins do not have wallets
                double balance = walletBalances.getOrDefault(user.getUserId(), 0.0);
                List<Transaction> transactions = getTransactionsForUser(user.getUserId());
                List<Notification> notifications;
                List<Order> orderHistory;
                try {
                    notifications = getNotificationsForUser(user.getUserId());
                    if (notifications == null) {
                        notifications = new ArrayList<>(); // Set an empty list if notifications are null
                    }
                } catch (Exception e) {
                    notifications = new ArrayList<>(); // Handle exceptions by setting an empty list
                }
                
                               
                Wallet wallet = new Wallet();
                wallet.setBalance(balance);
                wallet.setTransactions(transactions);
                
                if (user instanceof Customer) {
                    try {
                        orderHistory = getOrdersForCustomer(user.getUserId());
                        if (orderHistory == null) {
                            orderHistory = new ArrayList<>(); // Set an empty list if notifications are null
                        }
                    } catch (Exception e) {
                        orderHistory = new ArrayList<>(); // Handle exceptions by setting an empty list
                    }
                    ((Customer) user).setWallet(wallet);((Customer) user).setNotifications(notifications);((Customer) user).setOrderHistory(orderHistory);
                
                } else if (user instanceof Vendor) {
                    try {
                        orderHistory = getOrdersForVendor(user.getUserId());
                        if (orderHistory == null) {
                            orderHistory = new ArrayList<>(); // Set an empty list if notifications are null
                        }
                    } catch (Exception e) {
                        orderHistory = new ArrayList<>(); // Handle exceptions by setting an empty list
                    }
                    ((Vendor) user).setWallet(wallet);((Vendor) user).setNotifications(notifications);((Vendor) user).setOrderHistory(orderHistory);
                
                } else if (user instanceof DeliveryRunner) {
                    ((DeliveryRunner) user).setWallet(wallet);
                    ((DeliveryRunner) user).setNotifications(notifications);

                }
            }
        }
        return users;
    }   
    
    
    public List<DeliveryRunner> getDeliveryRunners() {
        List<User> allUsers = readUsers();
        List<DeliveryRunner> deliveryRunners = new ArrayList<>();

        for (User user : allUsers) {
            if (user instanceof DeliveryRunner) {
                deliveryRunners.add((DeliveryRunner) user);
            }
        }

        return deliveryRunners;
    }

    
    public void writeUsers(List<User> users, boolean append) {
        List<String> lines = users.stream().map(this::formatUserToLine).collect(Collectors.toList());
        writeToFile(lines, userFile, append);
        
//        updateWalletsForUsers(users);
    }

    private User parseUserFromLine(String line) {

        String[] parts = line.split(",");
        String userId = parts[0];
        String password = parts[1];
        String name = parts[2];
        String email = parts[3];
        User.Role role = User.Role.valueOf(parts[4]);

        switch (role) {
        case Vendor:
            return new Vendor(userId, password, name, email);
        case Customer:
            return new Customer(userId, password, name, email);
        case DeliveryRunner:
            return new DeliveryRunner(userId, password, name, email);
        default:
            return new User(userId, password, name, email, role); // For Admin and other roles
        }
    }
    
    private String formatUserToLine(User user) {
        return user.getUserId() + "," + user.getPassword() + "," + user.getName() + "," + user.getEmail() + "," + user.getRole().toString();
    }
    

    public User getUserById(String userId) {
        List<User> users = readUsers();  // This includes wallet initialization
        User user = users.stream()
                         .filter(u -> u.getUserId().equals(userId))
                         .findFirst()
                         .orElse(null);

        if (user == null) {
            return null;
        }

        switch (user.getRole()) {
            case Vendor:
                Menu menu = readMenus().stream()
                               .filter(m -> m.getVendorId().equals(userId))
                               .findFirst()
                               .orElse(null); // Find the menu for this vendor
                Vendor vendor = (Vendor) user;
                vendor.setMenu(menu);
                return vendor;

            case Customer:
                 Customer customer = (Customer) user;
                 return customer;

            case DeliveryRunner:
                DeliveryRunner runner = (DeliveryRunner) user;
                return runner;

            default:
                return user;
        }
    }

    public List<Menu> readMenus() {
        List<String> lines = readFromFile(menuFile);
        Map<String, Menu> menus = new HashMap<>(); // Map to store menus by vendor ID
        List<User> users = readUsers(); // Retrieve user information

        for (String line : lines) {
            String[] parts = line.split(",");
            if (parts.length < 6) {
                continue; // Skip lines with insufficient data
            }

            String vendorId = parts[0];
            String itemCuisine = parts[2];
            String itemId = parts[1];
            String itemName = parts[3];
            String description = parts[4];
            double price = Double.parseDouble(parts[5]);

            // Check if a user exists for this vendor ID
            User vendorUser = users.stream()
                                   .filter(user -> user.getUserId().equals(vendorId))
                                   .findFirst()
                                   .orElse(null);

            if (vendorUser instanceof Vendor) {
                // Create a new MenuItem
                MenuItem menuItem = new MenuItem(itemId, itemCuisine, itemName, description, price);

                // Check if a menu exists for this vendor
                if (!menus.containsKey(vendorId)) {
                    // Create a Menu object for the vendor and add it to the menus map
                    Menu menu = new Menu((Vendor) vendorUser); // Cast the user to Vendor
                    menus.put(vendorId, menu);
                }

                // Add the MenuItem to the corresponding menu
                menus.get(vendorId).addItem(menuItem);
            }
        }

        return new ArrayList<>(menus.values());
    }

                                                                                    
    public void writeMenus(List<Menu> menus, boolean append) {
        List<String> lines = new ArrayList<>();
        for (Menu menu : menus) {
            String vendorId = menu.getVendorId(); // Get the vendor ID
            for (MenuItem item : menu.getItems()) {
                // Include vendorId at the start of each line
                lines.add(vendorId + "," + formatMenuItemToLine(item));
            }
        }
        writeToFile(lines, menuFile, append);
    }


    private String formatMenuItemToLine(MenuItem item) {
        return item.getItemId() + "," +item.getItemCuisine() + ","+ item.getItemName() + "," + item.getDescription() + "," + item.getPrice();
    }

    public List<Order> readOrders() {       
        List<String> lines = readFromFile(ordersFile);
        return lines.stream()
                    .map(line -> parseOrderFromLine(line))
                    .collect(Collectors.toList());
    }


    private Order parseOrderFromLine(String line) {
        String[] parts = line.split(",");
//        format: orderId,customerId,vendorId,menuItemsStr,totalAmount,orderStatus,diningOption,deliveryInfo,deliveryStatus,orderTime,rating,review,delRating,delReview
        String orderId = parts[0];
        String customerId = parts[1];
        String vendorId = parts[2];
        Map<MenuItem, Integer> menuItems = parseItemIds(parts[3]); // Convert string of menu items to a map of MenuItem objects
        double totalAmount = Double.parseDouble(parts[4]);
        Order.OrderStatus orderStatus = Order.OrderStatus.valueOf(parts[5]);
        Order.DiningOption diningOpt = Order.DiningOption.valueOf(parts[6]);
        
        String deliveryInfo = parts[7]; //format: ASSIGNED(deliveryRunnerId);REJECTED(rejectedRunners)
        String[] deliveryInfoParts = deliveryInfo.split(";");
        String deliveryRunnerId = deliveryInfoParts[0].substring(9, deliveryInfoParts[0].length() - 1); // Extract deliveryRunnerId from ASSIGNED(deliveryRunnerId)
        
        // Extract rejectedRunners from REJECTED(rejectedRunners) and split by '&'
        String rejectedRunnersString = deliveryInfoParts[1].substring(9, deliveryInfoParts[1].length() - 1);
        List<String> rejectedDeliveryRunners = new ArrayList<>(Arrays.asList(rejectedRunnersString.split("&")));

        
        Order.DeliveryStatus deliveryStatus = Order.DeliveryStatus.valueOf(parts[8]);
        String classroom = parts[9];
        LocalDateTime orderTime = LocalDateTime.parse(parts[10]);
        int rating = Integer.parseInt(parts[11]);
        String review = parts.length > 11 ? parts[12] : "";
        int delRating = Integer.parseInt(parts[13]);
        String delReview = parts.length > 11 ? parts[14] : "";

        Order order = new Order(orderId, customerId, vendorId, menuItems);
        order.setTotalAmount(totalAmount);
        order.setOrderStatus(orderStatus);
        order.setDiningOption(diningOpt);
        order.setDeliveryRunnerId(deliveryRunnerId);
        order.setRejectedDeliveryRunners(rejectedDeliveryRunners);
        order.setDeliveryStatus(deliveryStatus);
        order.setClassroomForDelivery(classroom);
        order.setOrderTime(orderTime);
        order.setRating(rating);
        order.setReview(review);
        order.setDeliveryRating(delRating);
        order.setDeliveryReview(delReview);

        return order;
    }

    private Map<MenuItem, Integer> parseItemIds(String itemIdsStr) {
        Map<MenuItem, Integer> menuItems = new HashMap<>();
        String[] itemPairs = itemIdsStr.split(";"); // Split into pairs of itemId and quantity

        for (String itemPair : itemPairs) {
            String[] parts = itemPair.split("\\("); // Split into itemId and quantity
            if (parts.length == 2) {
                String itemId = parts[0];
                String quantityStr = parts[1].replace(")", ""); // Remove the closing parenthesis
                try {
                    int quantity = Integer.parseInt(quantityStr);
                    MenuItem item = menuItemMap.get(itemId);
                    if (item != null) {
                        menuItems.put(item, quantity);
                    }
                } catch (NumberFormatException e) {
                }
            }
        }

        return menuItems;
    }

    public void writeOrders(List<Order> orders, boolean append) {
        List<String> lines = orders.stream().map(this::formatOrderToLine).collect(Collectors.toList());
        writeToFile(lines, ordersFile, append);
    }

    private String formatOrderToLine(Order order) {
        String menuItemsStr = formatMenuItems(order.getMenuItems()); // Convert map of MenuItem objects to a formatted string

        // Create an Optional object from the nullable value
        Optional<List<String>> rejectedRunners = Optional.ofNullable(order.getRejectedDeliveryRunners());

        String rejectedRunnersStr = String.join("&", rejectedRunners.orElse(Collections.emptyList()));
        String deliveryInfo = "ASSIGNED(" + order.getDeliveryRunnerId() + ");" + "REJECTED(" + rejectedRunnersStr + ")";

        return order.getOrderId() + "," + order.getCustomerId() + "," + order.getVendorId() + "," +
               menuItemsStr + "," + order.getTotalAmount() + "," + order.getOrderStatus().name() + "," +
               order.getDiningOption() + ","+deliveryInfo +","+
               order.getDeliveryStatus() +","+order.getClassroomForDelivery()+","+
               order.getOrderTime().toString() + "," +
               order.getRating() + "," + order.getReview()+ "," +
               order.getDeliveryRating() + "," + order.getDeliveryReview();
    }


    private String formatMenuItems(Map<MenuItem, Integer> menuItems) {
        // Format menu items as "ItemName(Quantity);ItemName(Quantity);..."
        return menuItems.entrySet().stream()
                         .map(entry -> entry.getKey().getItemId() + "(" + entry.getValue() + ")")
                         .collect(Collectors.joining(";"));
    }

    public List<Order> getOrdersForCustomer(String customerId) {
        List<Order> allOrders = readOrders(); 
        List<Order> customerOrders = new ArrayList<>(); // Initialize an empty list

        for (Order order : allOrders) {
            if (order.getCustomerId().equals(customerId)) {
                customerOrders.add(order); // Add matching orders to the list
            }
        }

        return customerOrders;
    }
    public List<Order> getOrdersForVendor(String vendorId) {
        List<Order> allOrders = readOrders(); 
        List<Order> vendorOrders = new ArrayList<>(); // Initialize an empty list

        for (Order order : allOrders) {
            if (order.getVendorId().equals(vendorId)) {
                vendorOrders.add(order); // Add matching orders to the list
            }
        }

        return vendorOrders;
    }
    public List<Order> getOrdersForDeliveryRunner(String DeliveryRunnerID) {
        List<Order> allOrders = readOrders(); 
        List<Order> delROrders = new ArrayList<>(); // Initialize an empty list

        for (Order order : allOrders) {
            if (order.getDeliveryRunnerId().equals(DeliveryRunnerID)) {
                delROrders.add(order); // Add matching orders to the list
            }
        }

        return delROrders;
    }

    public Order getOrderByOrderId(String orderId) {
            List<Order> allOrders = readOrders();
            return allOrders.stream()
                                     .filter(t -> t.getOrderId().equals(orderId))
                                     .findFirst()
                                     .orElse(null);            
    }
    
    public Map<String, MenuItem> getMenuItemMap(){
         return menuItemMap;
    }
    
    public  Map<String, MenuItem> setMenuItemMap(){
        Map<String, MenuItem> menuItemMap1 = new HashMap<>();

        List<Menu> menus = readMenus();

        // Iterate through each menu
        for (Menu menu : menus) {
            List<MenuItem> menuItems = menu.getItems(); // Get the list of menu items for the current menu

            // Iterate through each MenuItem and add it to menuItemMap
            for (MenuItem menuItem : menuItems) {
                menuItemMap1.put(menuItem.getItemId(), menuItem);
            }
        }
        this.menuItemMap = menuItemMap1;
        return menuItemMap1;
    }
     
    
//  WALLET
    public Map<String, Double> readWalletBalances() {
        List<String> lines = readFromFile(walletsFile);
        return lines.stream()
                    .collect(Collectors.toMap(
                        line -> line.split(",")[0],  // UserID
                        line -> Double.parseDouble(line.split(",")[1])  // Balance
                    ));
    }

    public void writeWalletBalances(Map<String, Double> walletBalances, boolean append) {
        List<String> lines = walletBalances.entrySet().stream()
                                    .map(entry -> entry.getKey() + "," + entry.getValue())
                                    .collect(Collectors.toList());
        writeToFile(lines, walletsFile, append);
    }
   
    public double getWalletAmountByUserId(String userId) {
        Map<String, Double> walletBalances = readWalletBalances();
        return walletBalances.getOrDefault(userId, 0.0); // Returns 0.0 if the user ID is not found
    }
   
   
   
    // Method to read transactions from a file
    public List<Transaction> readTransactions() {
        List<String> lines = readFromFile(transactionsFile);
        return lines.stream().map(this::parseTransactionFromLine).collect(Collectors.toList());
    }
    
    public List<Transaction> getTransactionsForUser(String userId) {
        List<Transaction> allTransactions = readTransactions();
        return allTransactions.stream()
                              .filter(transaction -> transaction.getUserId().equals(userId))
                              .collect(Collectors.toList());
    }
    public Transaction getTransactionByTransactionId(String TransactionId) {
            List<Transaction> allTransactions = readTransactions();
            return allTransactions.stream()
                                     .filter(t -> t.getTransactionId().equals(TransactionId))
                                     .findFirst()
                                     .orElse(null);            
    }

    // Method to write transactions to a file
    public void writeTransactions(List<Transaction> transactions, boolean append) {
        List<String> lines = transactions.stream().map(this::formatTransactionToLine).collect(Collectors.toList());
        writeToFile(lines, transactionsFile, append);
    }
    public void writeTransaction(Transaction transaction, boolean append) {
        List<String> tmpTransaction = new ArrayList();
        tmpTransaction.add(formatTransactionToLine(transaction));
        writeToFile(tmpTransaction, transactionsFile, append);
    }

    // Method to parse a line of text into a Transaction object
    private Transaction parseTransactionFromLine(String line) {
        String[] parts = line.split(",");
        //  format: transactionId,userId,orderId,amount,type,dateTime,status
        String transactionId = parts[0];
        String userId = parts[1];
        String orderId = parts[2]; // Can be empty for non-order transactions
        double amount = Double.parseDouble(parts[3]);
        Transaction.TransactionType type = Transaction.TransactionType.valueOf(parts[4]);
        LocalDateTime dateTime = LocalDateTime.parse(parts[5]);
        Transaction.TransactionStatus status = Transaction.TransactionStatus.valueOf(parts[6]);
        
        return new Transaction(transactionId, userId, orderId, amount, type, dateTime, status);
    }

    // Method to format a Transaction object into a line of text
    private String formatTransactionToLine(Transaction transaction) {
        return transaction.getTransactionId() + "," + transaction.getUserId() + "," + 
               (transaction.getOrderId() != null ? transaction.getOrderId() : "") + "," + 
               transaction.getAmount() + "," + transaction.getType().name() + "," + 
               transaction.getDateTime().toString() + "," + transaction.getStatus().name();
    }
    
       


// Notification-related methods
    public List<Notification> readNotifications() {
        List<String> lines = readFromFile(notificationsFile);
        return lines.stream().map(this::parseNotificationFromLine).collect(Collectors.toList());
    }

    public void writeNotifications(List<Notification> notifications, boolean append) {
        List<String> lines = notifications.stream().map(this::formatNotificationToLine).collect(Collectors.toList());
        writeToFile(lines, notificationsFile, append);
    }

    public void writeNotification(Notification notification, boolean append) {
        List<String> tmpNoti = new ArrayList<>();
        tmpNoti.add(formatNotificationToLine(notification));
        writeToFile(tmpNoti, notificationsFile, append);
    }

    private Notification parseNotificationFromLine(String line) {
        // data is split using commas
        String[] parts = line.split(",");
        // Example: notificationId,userId,message,dateTime,transactionId
        if (parts.length == 5) {
            return new Notification(parts[0], parts[1], parts[2], LocalDateTime.parse(parts[3]), getTransactionByTransactionId(parts[4]));
        } else if (parts.length == 4) {
            return new Notification(parts[0], parts[1], parts[2], LocalDateTime.parse(parts[3]), null); // Transaction is null
        } else {
            return null;
        }
    }

    private String formatNotificationToLine(Notification notification) {
        String transactionId = notification.getTransaction() != null ? notification.getTransaction().getTransactionId() : "";
        return notification.getNotificationId() + "," + notification.getUserId() + "," + notification.getMessage() + "," + notification.getDateTime().toString() + "," + transactionId;
    }

    public List<Notification> getNotificationsForUser(String userId) {
        List<Notification> allNotifications = readNotifications();
        return allNotifications.stream()
                              .filter(notification -> notification.getUserId().equals(userId))
                              .collect(Collectors.toList());
    }

    public Notification getNotificationByNotificationId(String notificationId) {
        List<Notification> allNotifications = readNotifications();
        return allNotifications.stream()
                               .filter(t -> t.getNotificationId().equals(notificationId))
                               .findFirst()
                               .orElse(null);
    }

}
