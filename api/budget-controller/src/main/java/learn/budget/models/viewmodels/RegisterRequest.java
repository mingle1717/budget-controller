package learn.budget.models.viewmodels;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class RegisterRequest {
    String username;
    String password = "";
    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    String passhash = encoder.encode(password);

    String email;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasshash() {
        return passhash;
    }

    public void setPasshash(String passhash) {
        this.passhash = passhash;
    }
}
