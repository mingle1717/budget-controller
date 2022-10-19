package learn.budget.data;


import learn.budget.domain.Result;

import learn.budget.models.AppUser;
import learn.budget.models.Budget;
import learn.budget.models.UserBudget;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
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
    public Budget createBudget(Budget toAdd, int userId) {
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
            ps.setInt(1, userId);
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
        final String sql = "update Budget set balance = ?,\n" +
                "budget_name = ?\n" +
                "where budget_id = ?;";
        return jdbcTemplate.update(sql, budget.getBalance(),
                budget.getBudgetName(), budget.getBudgetId()) > 0;
    }

    public int getBudgetOwnerId(int budgetId) {
        final String sql = "select a.user_id from appUser a inner join userBudget u on a.user_id = u.user_id where budget_id = ? and isOwner = 1 ;";
        return jdbcTemplate.query(sql, (rs, rowNum) -> rs.getInt("user_id"), budgetId).stream().findFirst().orElse(-1);

    }
        public boolean addMemberToBridgeTableWithFalseIsOwnerField (AppUser userToAddToBudget,int budgetId){
            final String sql = "insert into userBudget (isOwner, user_id, budget_id) values\n" +
                    "(False, ?, ?);";

            return jdbcTemplate.update(sql, userToAddToBudget.getUserId(), budgetId) > 0;


        }
    }


