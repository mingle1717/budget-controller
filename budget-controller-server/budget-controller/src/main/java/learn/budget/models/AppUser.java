package learn.budget.models;
import org.springframework.security.core.userdetails.User;
import java.util.List;
import java.util.stream.Collectors;

public class AppUser extends User {

    private int userId;
    private String username;
    private String passHash;
    private String email;
    private boolean isDeleted;
    private List<MyRole> userRoles;
    private MyRole myRole;

    public AppUser(int userId, String username, String email, String passHash, boolean isDeleted, List<MyRole> roles){
        super(username, passHash, roles.stream().map( r -> r.getAuthority()).collect(Collectors.toList()));
        this.userId = userId;
        this.username = username;
        this.passHash = passHash;
        this.userRoles = userRoles;
        this.email = email;
        this.isDeleted = isDeleted;
        this.myRole = myRole;
    }


    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassHash() {
        return passHash;
    }

    public void setPassHash(String passHash) {
        this.passHash = passHash;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public List<MyRole> getUserRoles() {
        return userRoles;
    }

    public void setUserRoles(List<MyRole> userRoles) {
        this.userRoles = userRoles;
    }
}