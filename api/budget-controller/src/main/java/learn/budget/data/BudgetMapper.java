package learn.budget.data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import learn.budget.models.Budget;
import learn.budget.models.Category;
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
