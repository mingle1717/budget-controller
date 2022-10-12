package learn.budget.security;

import learn.budget.App;
import learn.budget.data.UserJdbcRepo;
import learn.budget.domain.Result;
import learn.budget.domain.ResultType;
import learn.budget.models.AppUser;
import learn.budget.models.viewmodels.RegisterRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AppUserService {

    @Autowired
    UserJdbcRepo repo;

    public Result<AppUser> register(RegisterRequest request) {
        Result result = new Result();
        AppUser payload;
        String username = request.getUsername();
        String email = request.getEmail();
        String password = request.getPassword();
        // password validation here

        if (password == null || password.length() < 6 || password.length() > 12 || password.isBlank()) {
            result.addMessage("This password does not meet the length requirements" +
                    " (6-12 characters)", ResultType.INVALID);
        }

        if (username == null || username.isBlank()) {
            result.addMessage("Please enter a username.", ResultType.INVALID);
        }

        if (repo.getUserByUsername(username) != null) {
            result.addMessage("This username already exists. Please try a different username.",
                    ResultType.INVALID);
        }

        if (email == null || email.isBlank()) {
            result.addMessage("Please enter an email.", ResultType.INVALID);
        }

        if (repo.getUserByEmail(email) != null) {
            result.addMessage("This email is already in use. Please try a different email.",
                    ResultType.INVALID);
        }
        if (result.getMessages().size() > 0) {
            return result;
        } else  {
            payload = repo.register(request, request.getPasshash());
            if (payload != null) {
                result.setPayload(payload);
                return result;
            } else {
                // this should not be reached
                result.addMessage("Something went wrong in the database.", ResultType.INVALID);
                return result;
            }
        }
    }

}
