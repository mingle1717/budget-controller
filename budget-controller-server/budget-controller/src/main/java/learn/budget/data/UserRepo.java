package learn.budget.data;

import learn.budget.models.AppUser;

public interface UserRepo {
    AppUser getUserByUsername(String username);
}