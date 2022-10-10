package learn.budget.data;

import learn.budget.models.MyRole;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MyRoleMapper implements RowMapper<MyRole> {
    @Override
    public MyRole mapRow(ResultSet rs, int rowNum) throws SQLException {
        MyRole toReturn = new MyRole();
        toReturn.setRoleId( rs.getInt("role_id") );
        toReturn.setRoleName( rs.getString("role_name"));

        return toReturn;
    }
}
