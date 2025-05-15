package classes;

import classes.*;
import java.io.*;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.swing.table.DefaultTableModel;

public class Controller {

    private static final String USERS_FILE = "src/resources/users.txt";
    private static final String menufile = "src/resources/MenuItem.txt";
    private static final String notificationsFile = "src/resources/notifications.txt";
    private List<User> users; // List of users
    private FileManager fileManager; // File manager to handle user data file

    public Controller() {
        // Constructor - can be left empty for now.
        this.fileManager = new FileManager();
        this.users = fileManager.readUsers();
        
    }

    
        // Method to login a user
    public Optional<User> login(String userId, String password) {
        return users.stream()
                    .filter(user -> user.getUserId().equals(userId) && user.getPassword().equals(password))
                    .findFirst();
    }

    // Method to check user role
    public boolean isUserInRole(String userId, User.Role role) {
        return users.stream()
                    .anyMatch(user -> user.getUserId().equals(userId) && user.getRole() == role);
    }
    
//    FOR ADMIN TO VIEW IN DASHBOARD
    public DefaultTableModel populateUsersTableModel() {
        DefaultTableModel model = new DefaultTableModel(new String[]{"UserID", "Role", "Full Name", "Email"}, 0);

        try (BufferedReader reader = new BufferedReader(new FileReader(USERS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length >= 4) { // Check if the line has enough data parts
                    model.addRow(new Object[]{data[0], data[4], data[2], data[3]}); // Add the data to the model
                }
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return model;
    }
    
//    FOR ADMIN TO VIEW IN ROLE CRUD
    public DefaultTableModel populateUsersTableModel(User.Role role) {
        DefaultTableModel model = new DefaultTableModel(new String[]{"UserID", "Role", "Full Name", "Email"}, 0);

        try (BufferedReader reader = new BufferedReader(new FileReader(USERS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length >= 4 && data[4].equals(role.name())) { // Check if the line has enough data parts
                    model.addRow(new Object[]{data[0], data[4], data[2], data[3]}); // Add the data to the model
                }
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return model;
    }
    
    public DefaultTableModel populateAdminNotificationsTableModel() {
        DefaultTableModel model = new DefaultTableModel(new String[]{"NotificationsID","Date", "Time", "UserID","Message",}, 0);

        try (BufferedReader reader = new BufferedReader(new FileReader(notificationsFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length >= 4) { // Check if the line has enough data parts
                    LocalDateTime dateTime = LocalDateTime.parse(data[3]);
                    LocalDate date = dateTime.toLocalDate();
                    LocalTime time = dateTime.toLocalTime();
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
                    String ftime = time.format(formatter);
                    model.addRow(new Object[]{data[0],String.valueOf(date), ftime, data[1], data[2]}); // Add the data to the model
                }
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return model;
    }
    
    
    public DefaultTableModel populateVendorMenuTableModel(String vendorId) {
        DefaultTableModel model = new DefaultTableModel(new String[]{"ItemID", "Name", "Description", "Price"}, 0);

        try (BufferedReader reader = new BufferedReader(new FileReader(menufile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length >= 4 && data[0].equals(vendorId)){
                    model.addRow(new Object[]{data[1], data[2], data[3], data[4]}); // Add the data to the model
                                       
                }
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return model;
    }
    
    
    
    public DefaultTableModel populateCustomerMenuTableModel(String cuisine) {
        DefaultTableModel model = new DefaultTableModel(new String[]{"ItemID", "Cuisine", "Name","Description", "Price"}, 0);

        try (BufferedReader reader = new BufferedReader(new FileReader(menufile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length >= 4 && (data[2] == null ? cuisine == null : data[2].equals(cuisine))){
                    model.addRow(new Object[]{data[1], data[2], data[3], data[4], data[5]}); // Add the data to the model
                                       
                }
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return model;
    }
    
    public DefaultTableModel populateCustomerOrderTableModel(Customer customer, Order.OrderStatus orderStatus) {
        DefaultTableModel model = new DefaultTableModel(new String[]{"Date","Time", "Order ID", "Items","Total Price", "Order Status", "Dining Opt.","Delivery Status", "Classroom"}, 0);
        
        model.getDataVector().removeAllElements();
        model.fireTableDataChanged();

        List<Order> orderHistory = customer.getOrderHistory();

        // Iterate through the orders in the order history
        for (Order order : orderHistory) {
            // Check if the order status matches the desired orderStatus
            if (order.getOrderStatus() == orderStatus) {
                LocalDateTime orderTime = order.getOrderTime();
                LocalDate date = orderTime.toLocalDate();
                LocalTime time = orderTime.toLocalTime();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
                String ftime = time.format(formatter);
                String diningOpt = String.valueOf(order.getDiningOption());
                String orderId = order.getOrderId(); // Replace with actual orderId property
                String items = order.formatMenuItems(order.getMenuItems()); // Replace with a method to get items as a string
                double totalPrice = order.getTotalAmount(); // Replace with actual totalPrice property
//                String orderStatus = order.getOrderStatus().toString(); // Replace with actual order status property
                String deliveryStatus = order.getDeliveryStatus().toString(); // Replace with actual delivery status property
                String classroom = order.getClassroomForDelivery(); // Replace with actual classroom property

                // Create a row and add it to the table model
                Object[] row = {date, ftime, orderId, items, totalPrice, orderStatus, diningOpt,deliveryStatus, classroom};
                model.addRow(row);
            }
        }
        return model;
    }
    
    public DefaultTableModel populateCustomerOrderTableModel(Customer customer, List<Order.OrderStatus> orderStatuses) {
        DefaultTableModel model = new DefaultTableModel(new String[]{"Date","Time", "Order ID", "Items","Total Price", "Order Status", "Dining Opt.","Delivery Status", "Classroom"}, 0);

        List<Order> orderHistory = customer.getOrderHistory();

        // Iterate through the orders in the order history
        for (Order order : orderHistory) {
            // Check if the order status matches the desired orderStatus
            for (Order.OrderStatus status : orderStatuses) {
                if (order.getOrderStatus() == status) {
                    LocalDateTime orderTime = order.getOrderTime();
                    LocalDate date = orderTime.toLocalDate();
                    LocalTime time = orderTime.toLocalTime();
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
                    String ftime = time.format(formatter);
                    String diningOpt = String.valueOf(order.getDiningOption());
                    String orderId = order.getOrderId(); // Replace with actual orderId property
                    String items = order.formatMenuItems(order.getMenuItems()); // Replace with a method to get items as a string
                    double totalPrice = order.getTotalAmount(); // Replace with actual totalPrice property
                    String deliveryStatus;
                    if (order.getDeliveryStatus() == null){
                        deliveryStatus = "-";
                    }else{
                        deliveryStatus = order.getDeliveryStatus().toString();
                    
                    } 
                    String classroom = order.getClassroomForDelivery(); // Replace with actual classroom property

                    // Create a row and add it to the table model
                    Object[] row = {date, ftime, orderId, items, totalPrice, status, diningOpt, deliveryStatus, classroom};
                    model.addRow(row);
                }
            }
        }
        return model;
    }
    public DefaultTableModel populateCustomerTransactionTable(Customer customer) {
        DefaultTableModel model = new DefaultTableModel(new String[]{"Date","Time", "Order ID", "Type", "Amount"}, 0);
        List<Transaction> transactionHistory = customer.getWallet().getTransactions();

        for (Transaction transaction : transactionHistory) {
            LocalDateTime transactionTime = transaction.getDateTime();
            LocalDate date = transactionTime.toLocalDate();
            LocalTime time = transactionTime.toLocalTime();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
            String ftime = time.format(formatter);
            String orderId;
            if (transaction.getOrderId().isEmpty()){
                orderId = "-";
            }else{
                orderId =transaction.getOrderId();
            }
            double amount = transaction.getAmount(); 
            String type = String.valueOf(transaction.getType());

            Object[] row = {date, ftime, orderId,type, amount};
            model.addRow(row);
        }
        return model;
    }
    
    public static DefaultTableModel populateVendorOrderTableModel(Vendor vendor, Order.OrderStatus orderStatus) {
        DefaultTableModel model = new DefaultTableModel(new String[]{"Date","Time", "Order ID", "Items","Total Price", "Order Status"}, 0);

        List<Order> orderHistory = vendor.getOrderHistory();

        // Iterate through the orders in the order history
        for (Order order : orderHistory) {
            // Check if the order status matches the desired orderStatus
            if (order.getOrderStatus() == orderStatus) {
                LocalDateTime orderTime = order.getOrderTime();
                LocalDate date = orderTime.toLocalDate();
                LocalTime time = orderTime.toLocalTime();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
                String ftime = time.format(formatter);
            
                String orderId = order.getOrderId(); // Replace with actual orderId property
                String items = order.formatMenuItems(order.getMenuItems()); // Replace with a method to get items as a string
                double totalPrice = order.getTotalAmount(); // Replace with actual totalPrice property

                // Create a row and add it to the table model
                Object[] row = {date, ftime, orderId, items, totalPrice, orderStatus};
                model.addRow(row);
            }
        }
        return model;
    }
    
    public DefaultTableModel populateVendorOrderTableModel(Vendor vendor, List<Order.OrderStatus> orderStatuses) {
        DefaultTableModel model = new DefaultTableModel(new String[]{"Date","Time", "Order ID", "Items","Total Price", "Order Status", "Dining Opt.","Delivery Status", "Classroom"}, 0);

        List<Order> orderHistory = vendor.getOrderHistory();

        // Iterate through the orders in the order history
        for (Order order : orderHistory) {
            // Check if the order status matches the desired orderStatus
            for (Order.OrderStatus status : orderStatuses) {
                if (order.getOrderStatus() == status) {
                    LocalDateTime orderTime = order.getOrderTime();
                    LocalDate date = orderTime.toLocalDate();
                    LocalTime time = orderTime.toLocalTime();
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
                    String ftime = time.format(formatter);
                    String diningOpt = String.valueOf(order.getDiningOption());
                    String orderId = order.getOrderId(); // Replace with actual orderId property
                    String items = order.formatMenuItems(order.getMenuItems()); // Replace with a method to get items as a string
                    double totalPrice = order.getTotalAmount(); // Replace with actual totalPrice property
          // Create a row and add it to the table model
                    Object[] row = {date, ftime, orderId, items, totalPrice, status, diningOpt};
                    model.addRow(row);
                }
            }
        }
        return model;
    }
    
   // Controller class method to filter orders for a vendor based on status and time period
    public static DefaultTableModel getFilteredVendorOrderTableModelByDate(
            Vendor vendor, 
            List<Order.OrderStatus> orderStatuses, 
            Date startDate, 
            Date endDate) {

        // Convert Date to LocalDate
        LocalDate startLocalDate = startDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate endLocalDate = endDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        // Initialize the table model with column headers
        DefaultTableModel model = new DefaultTableModel(
            new String[]{"Date", "Time", "Order ID", "Items", "Total Price", "Order Status"}, 0);

        // Retrieve the filtered orders based on the selected time period
        List<Order> filteredOrders = vendor.getOrderHistory().stream()
            .filter(order -> orderStatuses.contains(order.getOrderStatus()))
            .filter(order -> {
                LocalDate orderDate = order.getOrderTime().toLocalDate();
                return !orderDate.isBefore(startLocalDate) && !orderDate.isAfter(endLocalDate);
            })
            .collect(Collectors.toList());

        // Populate model with data
        double totalPrice;
        for (Order order : filteredOrders) {
            LocalDateTime orderTime = order.getOrderTime();
            LocalDate date = orderTime.toLocalDate();
            LocalTime time = orderTime.toLocalTime();
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
            String formattedDate = date.format(dateFormatter);
            String formattedTime = time.format(timeFormatter);

            String orderId = order.getOrderId();
            String items = order.formatMenuItems(order.getMenuItems());
            if(order.getDiningOption() == Order.DiningOption.DELIVERY){
                totalPrice = order.getTotalAmount()-3;
            }else{
                totalPrice = order.getTotalAmount();
            }
            String orderStatus = order.getOrderStatus().toString();

            model.addRow(new Object[]{formattedDate, formattedTime, orderId, items, totalPrice, orderStatus});
        }

        return model;
    }
    
    public static DefaultTableModel getFilteredRunnerOrderTableModelByDate(
            DeliveryRunner deliveryRunner, 
            List<Order.DeliveryStatus> deliveryStatuses, 
            Date startDate, 
            Date endDate) {

        // Convert Date to LocalDate
        LocalDate startLocalDate = startDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate endLocalDate = endDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        // Initialize the table model with column headers
        DefaultTableModel model = new DefaultTableModel(
            new String[]{"Date", "Time", "Order ID", "Customer Name","Items", "Order Status","Delivery Status", "Classroom"}, 0);


        // Retrieve the filtered orders based on the selected time period
        FileManager fm = new FileManager();
        List<Order> filteredOrders = fm.getOrdersForDeliveryRunner(deliveryRunner.getUserId()).stream()
            .filter(order -> deliveryStatuses.contains(order.getDeliveryStatus()))
            .filter(order -> {
                LocalDate orderDate = order.getOrderTime().toLocalDate();
                return !orderDate.isBefore(startLocalDate) && !orderDate.isAfter(endLocalDate);
            })
            .collect(Collectors.toList());

        // Populate model with data
        double totalPrice;
        for (Order order : filteredOrders) {
            LocalDateTime orderTime = order.getOrderTime();
            LocalDate date = orderTime.toLocalDate();
            LocalTime time = orderTime.toLocalTime();
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
            String formattedDate = date.format(dateFormatter);
            String formattedTime = time.format(timeFormatter);

            String orderId = order.getOrderId();
            String cusName = fm.getUserById(order.getCustomerId()).getName();
            String items = order.formatMenuItems(order.getMenuItems());
            
            String orderStatus = order.getOrderStatus().toString();
            String deliveryStatus = order.getDeliveryStatus().toString();
            String classroom = order.getClassroomForDelivery();
            

            model.addRow(new Object[]{formattedDate, formattedTime, orderId, cusName,items, orderStatus,deliveryStatus,classroom});
        }

        return model;
    }

    
    public static DefaultTableModel populateDeliveryRunnerTableModel(DeliveryRunner deliveryRunner, Order.DeliveryStatus deliveryStatus) {
        DefaultTableModel model = new DefaultTableModel(new String[]{"Date","Time", "Order ID", "Customer Name","Items", "Order Status","Delivery Status", "Classroom"}, 0);

        FileManager fm = new FileManager();
        List<Order> assignedOders = fm.getOrdersForDeliveryRunner( deliveryRunner.getUserId());

        // Iterate through the orders in the order history
        for (Order order : assignedOders) {
            
            // Check if the order status matches the desired orderStatus
            if (order.getDeliveryStatus() == deliveryStatus) {
                LocalDateTime orderTime = order.getOrderTime();
                LocalDate date = orderTime.toLocalDate();
                LocalTime time = orderTime.toLocalTime();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
                String ftime = time.format(formatter);
                String orderId = order.getOrderId(); // Replace with actual orderId property
                String cusName = fm.getUserById(order.getCustomerId()).getName();
                String items = order.formatMenuItems(order.getMenuItems());
      // Create a row and add it to the table model
                Object[] row = {date, ftime, orderId, cusName,items,order.getOrderStatus(),deliveryStatus,order.getClassroomForDelivery()};
                model.addRow(row);
            }
            
        }
        return model;
    }
    
    public static DefaultTableModel populateDeliveryRunnerRejectedTableModel(DeliveryRunner deliveryRunner) {
        DefaultTableModel model = new DefaultTableModel(new String[]{"Date","Time", "Order ID", "Customer Name","Items", "Order Status", "Classroom"}, 0);
        
        FileManager fm = new FileManager();
        List<Order> allOrders = fm.readOrders();
                       
        // Iterate through the orders in the order history
        for (Order order : allOrders) {
            List<String> rejectedRunners = order.getRejectedDeliveryRunners();
            for (String id : rejectedRunners){
            // Check if the order status matches the desired orderStatus
            
                if (deliveryRunner.getUserId().equals(id)) {
                    LocalDateTime orderTime = order.getOrderTime();
                    LocalDate date = orderTime.toLocalDate();
                    LocalTime time = orderTime.toLocalTime();
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
                    String ftime = time.format(formatter);
                    String orderId = order.getOrderId(); // Replace with actual orderId property
                    String cusName = fm.getUserById(order.getCustomerId()).getName();
                    String items = order.formatMenuItems(order.getMenuItems());
          // Create a row and add it to the table model
                    Object[] row = {date, ftime, orderId, cusName,items,order.getOrderStatus(),order.getClassroomForDelivery()};
                    model.addRow(row);
                }
            
            }
        }
        return model;   
    }
    
    public static DefaultTableModel populateDeliveryRunnerTableModel(DeliveryRunner deliveryRunner,List<Order.DeliveryStatus> deliveryStatuses) {
        DefaultTableModel model = new DefaultTableModel(new String[]{"Date","Time", "Order ID", "Customer Name","Items", "Order Status","Delivery Status", "Classroom"}, 0);

        FileManager fm = new FileManager();
        List<Order> assignedOders = fm.getOrdersForDeliveryRunner( deliveryRunner.getUserId());

        // Iterate through the orders in the order history
        for (Order order : assignedOders) {
            for (Order.DeliveryStatus deliveryStatus : deliveryStatuses) {
            // Check if the order status matches the desired orderStatus
                if (order.getDeliveryStatus().equals(deliveryStatus)) {
                    LocalDateTime orderTime = order.getOrderTime();
                    LocalDate date = orderTime.toLocalDate();
                    LocalTime time = orderTime.toLocalTime();
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
                    String ftime = time.format(formatter);
                    String orderId = order.getOrderId(); // Replace with actual orderId property
                    System.out.println(orderId);
                    String cusName = fm.getUserById(order.getCustomerId()).getName();
                    String items = order.formatMenuItems(order.getMenuItems());
          // Create a row and add it to the table model
                    Object[] row = {date, ftime, orderId, cusName,items,order.getOrderStatus(),deliveryStatus,order.getClassroomForDelivery()};
                    model.addRow(row);
                }
            }
        }
        return model;
    }
    
    public static DefaultTableModel populateNotificationTable(String userid) {
        DefaultTableModel model = new DefaultTableModel(new String[]{"Date","Time", "Notification", "TransactionId"}, 0);

        FileManager fm = new FileManager();
        User user =fm.getUserById(userid);
        List<Notification> notifications = user.getNotifications();
        // Iterate through the orders in the order history
        for (Notification notification : notifications) {
            // Check if the order status matches the desired orderStatus
                LocalDateTime notiTime = notification.getDateTime();
                String date = String.valueOf(notiTime.toLocalDate());
                LocalTime time = notiTime.toLocalTime();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
                String ftime = time.format(formatter);
                
                String notiMsg = notification.getMessage();
                String transactionId;
                
                if(notification.getTransaction()==null){
                    transactionId = "-";
                }else{
                    transactionId = notification.getTransactionId();
                }

                Object[] row = {date, ftime, notiMsg, transactionId};
                model.addRow(row);
        }
        
        return model;
    }
    
    public static String generateTransactionId(String userId) {
        long timestamp = System.currentTimeMillis();
        return userId + "_" + timestamp; // Example: "user123_1617701234567"
    }

    public static String generateNotificationId(String userid) {
        long timestamp = System.currentTimeMillis();
        return "notif_" + userid + "_" + timestamp; // Example: "notif_user123_1617701234567"
    }
    
    public static String generateOrderId(String userId) {
        long timestamp = System.currentTimeMillis();
        return "order_" + userId + "_" + timestamp; // Example: "notif_user123_1617701234567"
    }
    
    public static String autoAssignDeliveryRunner(Order order) {
        FileManager fm = new FileManager();
        List<DeliveryRunner> availableRunners = fm.getDeliveryRunners();// Implement a method to read available delivery runners
        Optional<List<String>> rejectedRunners = Optional.ofNullable(order.getRejectedDeliveryRunners());

        // Use orElse() to return the value or an empty list if the value is null
        for (DeliveryRunner runner : availableRunners) {
            if (!rejectedRunners.orElse(Collections.emptyList()).contains(runner.getUserId())) {
                // Assign the order to the first available runner not in the rejected list
                order.setDeliveryRunnerId(runner.getUserId());
                String deliveryRunnerId = runner.getUserId();
                Notification runnerNotification = new Notification(
                        generateNotificationId(deliveryRunnerId),
                        deliveryRunnerId,
                        "A new order needs to be delivered: " + order.getOrderId(),
                        LocalDateTime.now(),
                        null
                );

                 fm.writeNotification(runnerNotification, true);
                return runner.getUserId();
                
            }
        }

        
        return null;
    }
    
}

   

