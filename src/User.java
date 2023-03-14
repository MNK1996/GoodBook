import java.util.HashMap;
import java.util.Map;

public class User {
    private String username;
    private String password;
    private boolean isLoggedIn;
    private Map<String, Integer> purchasedBooks;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.isLoggedIn = false;
        this.purchasedBooks=new HashMap<>();
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public boolean isLoggedIn() {
        return isLoggedIn;
    }

    public void setLoggedIn(boolean isLoggedIn) {
        this.isLoggedIn = isLoggedIn;
    }

    public Map<String, Integer> getPurchasedBooks() {
        return purchasedBooks;
    }

    public void setPurchasedBooks(Map<String, Integer> purchasedBooks) {
        this.purchasedBooks = purchasedBooks;
    }

    @Override
    public String toString() {
        return username;
    }


}