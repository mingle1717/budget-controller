package learn.budget.data;

import learn.budget.models.AppUser;
import learn.budget.models.MyRole;
import org.springframework.jdbc.core.RowMapper;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class AppUserMapper implements RowMapper<AppUser> {

    List<MyRole> roles;

    public AppUserMapper(List<MyRole> roles){
        this.roles = roles;
    }

    @Override
    public AppUser mapRow(ResultSet rs, int rowNum) throws SQLException {
        int userId = rs.getInt( "user_id");
        String username = rs.getString("username");
        String email = rs.getString( "email");
        String passHash = rs.getString("passhash");
        boolean isDeleted = rs.getBoolean("isDeleted");
        int roleId = rs.getInt("role_id");

        return new AppUser(userId, username, email, passHash, isDeleted, roles);
    }
}