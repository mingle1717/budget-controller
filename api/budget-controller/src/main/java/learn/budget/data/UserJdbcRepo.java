package learn.budget.data;

import learn.budget.models.AppUser;
import learn.budget.models.MyRole;
import learn.budget.models.viewmodels.RegisterRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class UserJdbcRepo implements UserRepo {

    @Autowired
    JdbcTemplate template;

    @Override
    public AppUser getUserByUsername(String username) {
        List<MyRole> userRoles = getRolesByUsername(username);

        return template.query(
                "select user_id, username, passhash, email, isDeleted from appUser where username = ?;",
                new AppUserMapper(userRoles),
                username).stream().findFirst().orElse(null);
    }

    public void setUserToAdmin(AppUser user) {
        template.update("update appUser set role_id = '2' where user_id = ?;", user.getUserId());
    }

    private List<MyRole> getRolesByUsername(String username) {
        String sql = "select r.role_id, r.role_name\n" +
                "                from myRole as r\n" +
                "                inner join appUser as u \n" +
                "                on u.role_id = r.role_id\n" +
                "                where u.username =?;";

        return template.query(sql, new MyRoleMapper(), username);
    }

    public AppUser getUserByEmail(String email) {

        List<MyRole> userRoles = getRolesByEmail(email);


        return template.query(
                "select user_id, username, passhash, email, isDeleted from appUser where email = ?;",
                new AppUserMapper(userRoles),
                email).stream().findFirst().orElse(null);
    }

    private List<MyRole> getRolesByEmail(String email) {
        String sql = "select r.role_id, r.role_name\n" +
                "                from myRole as r\n" +
                "                inner join appUser as u \n" +
                "                on u.role_id = r.role_id\n" +
                "                where u.email =?;";

        return template.query(sql, new MyRoleMapper(), email);
    }

    public AppUser register(RegisterRequest request, String passhash) {

        final String sql = "insert into appUser(username, passhash, email, role_id) values (?, ?, ?, ?);";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        int rowsAffected = template.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, request.getUsername());
            ps.setString(2, passhash);
            ps.setString(3, request.getEmail());
            ps.setString(4, "2"); // every registered user defaults as a member,
            // no registered user is without a role
            return ps;
        }, keyHolder);

        if (rowsAffected <= 0) {
            return null;
        }

        return new AppUser((keyHolder.getKey().intValue()), request.getUsername(), request.getEmail(),
                passhash, false, getRolesByUsername(request.getUsername()));
        // User ID: recieved from database
        // Username, Email, Passhash: taken from request since it should match database
        // isDeleted: false because database automatically sets to false
        // List<MyRole> userRoles: Using a different method to get roles
    }
}
