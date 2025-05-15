
package classes;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author cocon
 */
public class User {
    private String userId;
    private String password;
    private String name;
    private String email;
    private Role role;
    private Wallet wallet;
    private List<Notification> notifications;
    
    public enum Role{
            Admin,
            Customer,
            Vendor,
            DeliveryRunner
    }
//    private String role;
    
    
    public User() {
  
    }
    public User(String userId, String password) {
        this.userId = userId;
        this.password = password;
    }

    public User(String userId, String password, String name, String email, Role role, Wallet wallet) {
        this.userId = userId;
        this.password = password;
        this.name = name;
        this.email = email;
        this.role = role;
        this.wallet = wallet;
        this.notifications = new ArrayList<>();
    }
    public User(String userId, String password, String name, String email, Role role) {
        this.userId = userId;
        this.password = password;
        this.name = name;
        this.email = email;
        this.role = role;
        this.wallet = new Wallet();
        this.notifications = new ArrayList<>();

    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
    
    public Wallet getWallet() {
        return wallet;
    }

    public void setWallet(Wallet wallet) {
        this.wallet = wallet;
    }

    public List<Notification> getNotifications() {
        return notifications;
    }

    public void setNotifications(List<Notification> notifications) {
        this.notifications = notifications;
    }
    
    public void addNotification(Notification notification){
        this.notifications.add(notification);
    }
    

}
