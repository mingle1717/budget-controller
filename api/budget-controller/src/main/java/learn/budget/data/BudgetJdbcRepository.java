package learn.budget.data;

import learn.budget.models.Budget;
import learn.budget.models.UserBudget;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.sql.PreparedStatement;
import java.sql.Statement;

@Repository
public class BudgetJdbcRepository implements BudgetRepository {

    private final JdbcTemplate jdbcTemplate;

    public BudgetJdbcRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    @Transactional
    public Budget findById(int budgetId) {

        final String sql = "select `budget_id`, `budget_name`, `balance` "
                + "from `Budget`"
                + "where budget_id = ?;";

        return jdbcTemplate.query(sql, new BudgetMapper(), budgetId).stream()
                .findFirst().orElse(null);
    }

    @Override
    public Budget createBudget(Budget toAdd) {
        final String sql = "INSERT INTO `Budget`" +
                "(`budget_id`, `budget_name`, `balance`)" +
                " VALUES (?, ?, ?);";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        int rowsAffected = jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, toAdd.getBudgetId());
            ps.setString(2, toAdd.getBudgetName());
            ps.setBigDecimal(3, toAdd.getBalance());
            return ps;
        }, keyHolder);

        if (rowsAffected <= 0) {
            return null;
        }

        toAdd.setBudgetId(keyHolder.getKey().intValue());

        final String moreSql = "insert into userBudget (isOwner, user_id, budget_id) values (true, ?, ?);";
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(moreSql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, toAdd.getAppUsers().get(0).getUserId());
            ps.setInt(2, toAdd.getBudgetId());
            return ps;
        }, keyHolder);

        return toAdd;
    }

    public UserBudget getBridgeTableInfo(int userId) {
        final String sql = "select userBudget_id, isOwner, user_id, budget_id from userBudget where user_id = ?;";
        return jdbcTemplate.query(
                sql,
                new UserBudgetMapper(),
                userId).stream().findFirst().orElse(null);
    }

    public boolean update(Budget budget) {
        final String sql = "update budget set balance = ?,\n" +
                "budget_name = ?\n" +
                "where budget_id = ?;";
        return jdbcTemplate.update(sql, budget.getBalance(),
                budget.getBudgetName(), budget.getBudgetId()) > 0;
    }
}

