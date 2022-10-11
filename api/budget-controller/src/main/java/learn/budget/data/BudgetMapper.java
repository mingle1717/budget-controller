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
    // Note: This returns a partially hydrated budget since it only maps from the budget table.
    // It still needs to set the userId and the categories.


    /*
    AgentMapper for reference.

    public class AgentMapper implements RowMapper<Agent> {

    @Override
    public Agent mapRow(ResultSet resultSet, int i) throws SQLException {
        Agent agent = new Agent();
        agent.setAgentId(resultSet.getInt("agent_id"));
        agent.setFirstName(resultSet.getString("first_name"));
        agent.setMiddleName(resultSet.getString("middle_name"));
        agent.setLastName(resultSet.getString("last_name"));
        if (resultSet.getDate("dob") != null) {
            agent.setDob(resultSet.getDate("dob").toLocalDate());
        }
        agent.setHeightInInches(resultSet.getInt("height_in_inches"));
        return agent;
    }
}

     */
}
