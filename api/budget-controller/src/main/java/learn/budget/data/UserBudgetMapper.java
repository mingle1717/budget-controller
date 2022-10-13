package learn.budget.data;

import learn.budget.models.UserBudget;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserBudgetMapper implements RowMapper<UserBudget> {
    @Override
    public UserBudget mapRow(ResultSet rs, int rowNum) throws SQLException {
        UserBudget ub = new UserBudget();
        ub.setBudgetId(rs.getInt("budget_id"));
        ub.setUserId(rs.getInt("user_id"));
        ub.setUserBudgetId(rs.getInt("user_budget_id"));
        ub.setOwner(rs.getBoolean("isOwner"));

        return ub;
    }
}
