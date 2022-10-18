package learn.budget.models;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class AppUser implements UserDetails {

    private int userId;
    private String username;
    private String passHash;
    private String email;
    private boolean isDeleted;
    private List<MyRole> userRoles;

    public AppUser(int userId, String username, String email, String passHash, boolean isDeleted, List<MyRole> userRoles){
        this.userId = userId;
        this.username = username;
        this.passHash = passHash;
        this.userRoles = userRoles;
        this.email = email;
        this.isDeleted = isDeleted;
    }

    public AppUser(){}

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return userRoles.stream().map( r -> r.getAuthority()).collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return passHash;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return !isDeleted;
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