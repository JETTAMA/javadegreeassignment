
package classes;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Wallet {
    private double balance;
    private List<Transaction> transactions;

    // Constructor
    public Wallet() {
        this.transactions = new ArrayList<>();
    }

    // Getters and Setters
    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    // Method to add balance
    public void addBalance(double amount, String userId) { // Added userId parameter
        if (amount > 0) {
            this.balance += amount;
            FileManager fm = new FileManager();
            Controller controller = new Controller();
            String transactionId = controller.generateTransactionId(userId);
            LocalDateTime dateTime = LocalDateTime.now();

//            transactions.add(new Transaction(transactionId, userId, null, amount, Transaction.TransactionType.CREDIT, dateTime, Transaction.TransactionStatus.SUCCESS));
            Transaction transaction = new Transaction(transactionId, userId, null, amount, Transaction.TransactionType.CREDIT, dateTime, Transaction.TransactionStatus.SUCCESS);
            transactions.add(transaction);
            fm.writeTransaction(transaction, true);
            
//            new notification
            String notificationid = controller.generateNotificationId(userId);
            String notificationMsg = "Wallet balance added: " + amount;
            Notification balanceChgNotification = new Notification(notificationid, userId, notificationMsg, transaction.getDateTime(), transaction);
            fm.writeNotification(balanceChgNotification, true);

//            update wallets
            Map<String, Double> walletBalances = fm.readWalletBalances();
            walletBalances.put(userId, getBalance());
            fm.writeWalletBalances(walletBalances, false);

        }
    }
    
    // Method to deduct balance
    public boolean deductBalance(Order order, String userId) { // Added userId parameter
        double orderTotal = order.getTotalAmount();
        if (orderTotal > 0 && this.balance >= orderTotal) {
            this.balance -= orderTotal;
            FileManager fm = new FileManager();
            String transactionId = Controller.generateTransactionId(userId);
            LocalDateTime dateTime = LocalDateTime.now();
            
            Transaction transaction = new Transaction(transactionId, userId, order.getOrderId(), orderTotal, 
                    Transaction.TransactionType.DEBIT, dateTime, Transaction.TransactionStatus.SUCCESS);
            transactions.add(transaction);
            fm.writeTransaction(transaction, true);
            
//            write notification of wallet balanced chged
            String notificationid = Controller.generateNotificationId(userId);
            String notificationMsg = "Wallet Balance deducted: " + orderTotal;
            Notification balanceChgNotification = 
                    new Notification(notificationid, userId, notificationMsg, transaction.getDateTime(), transaction);
            fm.writeNotification(balanceChgNotification, true);
            
            Map<String, Double> walletBalances = fm.readWalletBalances();
            walletBalances.put(userId, getBalance());
            fm.writeWalletBalances(walletBalances, false);
            
            return true;
        }
        return false;
    }
    
    public boolean deductBalance(double cash, String userId) { // Added userId parameter
        if (cash > 0 && this.balance >= cash) {
            this.balance -= cash;
            FileManager fm = new FileManager();
            String transactionId = Controller.generateTransactionId(userId);
            LocalDateTime dateTime = LocalDateTime.now();
            
            Transaction transaction = 
                    new Transaction(transactionId, userId, null, cash, 
                        Transaction.TransactionType.DEBIT, dateTime, Transaction.TransactionStatus.SUCCESS);
            transactions.add(transaction);
            fm.writeTransaction(transaction, true);
            
//            write notification of wallet balanced chged
            String notificationid = Controller.generateNotificationId(userId);
            String notificationMsg = "Wallet Balance deducted: " + cash;
            Notification balanceChgNotification = 
                    new Notification(notificationid, userId, notificationMsg, transaction.getDateTime(), transaction);
            fm.writeNotification(balanceChgNotification, true);
            
            Map<String, Double> walletBalances = fm.readWalletBalances();
            walletBalances.put(userId, getBalance());
            fm.writeWalletBalances(walletBalances, false);
            
            return true;
        }
        return false;
    }

    // Method to add a transaction
    public void addTransaction(Transaction transaction) {
        transactions.add(transaction);
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }
    
    public Transaction getLastTransaction(){
        Transaction transaction = transactions.get (transactions.size () - 1);
        return transaction;
    }

    
}

