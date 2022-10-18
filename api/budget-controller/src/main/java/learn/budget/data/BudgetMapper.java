package learn.budget.data;

import java.sql.ResultSet;
import java.sql.SQLException;

import learn.budget.models.Budget;
import org.springframework.jdbc.core.RowMapper;

public class BudgetMapper implements RowMapper<Budget> {
    @Override
    public Budget mapRow(ResultSet rs, int rowNum) throws SQLException {
        Budget budget = new Budget();

        budget.setBudgetName(rs.getString("budget_name"));
        budget.setBudgetId(rs.getInt("budget_id"));
        budget.setBalance(rs.getBigDecimal("balance"));

        return budget;
    }
}
