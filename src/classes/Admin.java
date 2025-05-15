package classes;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;


public class Admin extends User {

    // Constructor
    
    public Admin() {
    }

    public Admin(String userId, String password, String name, String email) {
        super(userId, password, name, email, User.Role.Admin);
    }

    // Method to register a new user (either Customer or Vendor)
    public boolean registerUser(String userId, String password, String name, String email, User.Role role) {
        FileManager fileManager = new FileManager();
        List<User> users = fileManager.readUsers();
        if (users.stream().anyMatch(user -> user.getUserId().equals(userId))) {
            return false; // User already exists
        }
        User newUser = new User(userId, password, name, email, role);
        users.add(newUser);
        fileManager.writeUsers(users, false); // Update user data file

        if (!(newUser instanceof Admin)) {
            Map<String, Double> walletBalances = fileManager.readWalletBalances();
            walletBalances.put(userId, 0.0); // Initialize with 0 balance for new user
            fileManager.writeWalletBalances(walletBalances, false);
        }
        return true;
    }   

    // Method to top-up a customer's wallet balance
    public boolean topUpCustomerWallet(Customer customer, double amount) {
        if (amount <= 0) {
            return false; // Invalid amount
        }

        // Add balance to the customer's wallet// and added transaction
        customer.getWallet().addBalance(amount, customer.getUserId());
           
        FileManager fm = new FileManager();

        // Update wallet balance in file
        Map<String, Double> walletBalances = fm.readWalletBalances();
        walletBalances.put(customer.getUserId(), customer.getWallet().getBalance());
        fm.writeWalletBalances(walletBalances, false);

        return true;
    }
    
    // Method to cash out wallet balance
    public boolean cashOut(User user, double amount) {
        if (amount <= 0) {
            return false; // Invalid amount
        }

        // Add balance to the customer's wallet// and added transaction
        user.getWallet().deductBalance(amount, user.getUserId());
           
        FileManager fm = new FileManager();

        // Update wallet balance in file
        Map<String, Double> walletBalances = fm.readWalletBalances();
        walletBalances.put(user.getUserId(), user.getWallet().getBalance());
        fm.writeWalletBalances(walletBalances, false);

        return true;
    }
    
    public String generateReceipt(Transaction transaction){
        
        LocalDateTime datetime= transaction.getDateTime();
        LocalDate date = datetime.toLocalDate();
        LocalTime time = datetime.toLocalTime();
        String textBlock = """

        Do you want to send transactional receipt?

        Transaction Receipt

        Transaction ID: """ + transaction.getTransactionId() + """
        
        User ID: """ + transaction.getUserId() + """
        
        Amount: RM """ + transaction.getAmount() + """
        
        Type: """ + transaction.getType() + """
        
        Date: """ + date + """
        
        Time: """ + time + """
        
        Status: """ + transaction.getStatus() + """
        """;
        return textBlock;
    }


    // Method to send a transaction receipt through notifications
    public void sendReceipt(User user, Transaction transaction, String message) {
        String notificationId = Controller.generateNotificationId(user.getUserId());// Generate a unique ID for the notification
        String userId = user.getUserId(); // User ID for whom the notification is intended
        LocalDateTime dateTime = LocalDateTime.now(); // Current time as the notification time

        Notification notification = new Notification(notificationId, userId, message, dateTime, transaction);
        user.getNotifications().add(notification);
        FileManager fm = new FileManager();
//            existing notifications
            List<Notification> existingNotifications;

            try {
                existingNotifications = fm.readNotifications();
            } catch (Exception e) {
                existingNotifications = new ArrayList<>(); // Initialize as an empty list in case of an error
            }

            // Retrieve new notifications related to the customer
            List<Notification> newNotifications = user.getNotifications();

            // Create a new list to store combined notifications
            List<Notification> combinedNotifications = new ArrayList<>(existingNotifications);

            // Iterate through new notifications and add only those that are not already in the existing notifications
            for (Notification newNotification : newNotifications) {
                boolean isDuplicate = existingNotifications.stream()
                        .anyMatch(existingNotification -> existingNotification.getNotificationId().equals(newNotification.getNotificationId()));
                if (!isDuplicate) {
                    combinedNotifications.add(newNotification);
                }
            }

            // Write the combined notifications to the file
            fm.writeNotifications(combinedNotifications, false);

    }


    // Method to update user information
   public boolean updateUser(String userId, String newId,String newName, String newEmail) {
        FileManager fm = new FileManager();
        List<User> users = fm.readUsers();
        for (User user : users) {
            if (user.getUserId().equals(userId)) {
                user.setUserId(newId);
                user.setName(newName);
                user.setEmail(newEmail);
                fm.writeUsers(users, false);
                return true;
            }
        }
        return false; // User not found
    }

    // Method to delete a user
    public boolean deleteUser(String userId) {
        FileManager fm = new FileManager();
        List<User> users = fm.readUsers();
        if (users.removeIf(u -> u.getUserId().equals(userId))){
            fm.writeUsers(users, false);
            return true;
        }
        return false;
    }

}
