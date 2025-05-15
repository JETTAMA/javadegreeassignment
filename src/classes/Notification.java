package classes;

import java.time.LocalDateTime;

public class Notification {
    private String notificationId;
    private String userId; // The ID of the user who will receive the notification
    private String message; // The content of the notification
    private LocalDateTime dateTime; // When the notification was created/sent
    private Transaction transaction;

    // Constructor
    public Notification(){}
    
    public Notification(String notificationId, String userId, String message, LocalDateTime dateTime) {
        this.notificationId = notificationId;
        this.userId = userId;
        this.message = message;
        this.dateTime = dateTime;
    }
    public Notification(String notificationId, String userId, String message, LocalDateTime dateTime, Transaction transaction) {
        this.notificationId = notificationId;
        this.userId = userId;
        this.message = message;
        this.dateTime = dateTime;
        this.transaction = transaction;
    }

    // Getters and Setters
    public String getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(String notificationId) {
        this.notificationId = notificationId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public Transaction getTransaction() {
        return transaction;
    }
    public String getTransactionId() {
        return transaction.getTransactionId();
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }
}
