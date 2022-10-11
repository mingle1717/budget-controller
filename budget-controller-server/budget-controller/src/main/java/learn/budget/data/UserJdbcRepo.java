package learn.budget.data;

import learn.budget.models.AppUser;
import learn.budget.models.MyRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserJdbcRepo implements UserRepo {

    @Autowired
    JdbcTemplate template;

    @Override
    public AppUser getUserByUsername(String username) {
        List<MyRole> userRoles = getRolesByUsername(username);

        return template.query(
                "select user_id, username, passhash, email, isDeleted from appUser where username = ?",
                new AppUserMapper(userRoles),
                username).stream().findFirst().orElse(null);
    }

    private List<MyRole> getRolesByUsername(String username) {
        String sql = "select r.role_id, r.role_name\n" +
                "from myRole as r\n" +
                "inner join appUser as u\n" +
                "\ton u.user_id = r.user_id\n" +
                "where u.username = ?;";

        return template.query(sql, new MyRoleMapper(), username);
    }
}
