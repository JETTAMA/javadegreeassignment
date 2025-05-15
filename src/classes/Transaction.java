
package classes;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class Transaction {
    private String transactionId;
    private String userId;
    private String orderId; // Optional: Used for order-related transactions
    private double amount;
    private TransactionType type;
    private LocalDateTime dateTime;
    private TransactionStatus status;

    // Enum for Transaction Type
    public enum TransactionType {
        CREDIT, // For adding funds to wallet
        DEBIT   // For deducting funds from wallet or order payments
    }

    // Enum for Transaction Status
    public enum TransactionStatus {
        SUCCESS,
        FAILED
    }

    // Constructor
    public Transaction(String transactionId, String userId, String orderId, double amount, TransactionType type, LocalDateTime dateTime, TransactionStatus status) {
        this.transactionId = transactionId;
        this.userId = userId;
        this.orderId = orderId; // Can be null for non-order transactions
        this.amount = amount;
        this.type = type;
        this.dateTime = dateTime;
        this.status = status;
    }

    // Getters and Setters
    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public TransactionStatus getStatus() {
        return status;
    }

    public void setStatus(TransactionStatus status) {
        this.status = status;
    }

    @Override
    public String toString(){
        LocalDate date = dateTime.toLocalDate();
        LocalTime time = dateTime.toLocalTime();
        if (orderId ==null) {
        return "Transaction Receipt--" + 
           "Transaction ID: " + transactionId + "--" +
           "User ID: " + userId + "--" +
           "Amount: " + amount + "--" +  
           "Type: " + type + "--" +
           "Date: " + date + "--" +
           "Time: " + time + "--" +
           "Status: " + status;
        }else{
            return "Transaction Receipt--" + 
           "Transaction ID: " + transactionId + "--" +
           "User ID: " + userId + "--" +
           "Order ID: " + orderId + "--" +
           "Amount: " + amount + "--" +  
           "Type: " + type + "--" +
           "Date: " + date + "--" +
           "Time: " + time + "--" +
           "Status: " + status;
        }
    }

}

