package learn.budget.data;

import learn.budget.models.AppUser;
import learn.budget.models.Transaction;
import org.springframework.jdbc.core.RowMapper;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class TransactionMapper implements RowMapper<Transaction> {
    @Override
    public Transaction mapRow(ResultSet rs, int rowNum) throws SQLException {
        Transaction transaction = new Transaction();

        transaction.setTransactionId(rs.getInt("transaction_id"));
        transaction.setTransactionName(rs.getString("transaction_name"));
        transaction.setTransactionAmount(rs.getBigDecimal("transaction_amount"));
        transaction.setTransacationDescription(rs.getString("transaction_description"));
//        transaction.setCategoryId(rs.getInt("category_id"));
        transaction.setAuto_Id(rs.getInt("auto_id"));

        // TODO: 10/17/2022 Make full Category object and add, also debug Transaction stuff
        // public Category(int categoryId, String categoryName, BigDecimal categoryPercent,
        // BigDecimal higherLimit, BigDecimal lowerLimit, boolean goal, int budgetId)
        int categoryId = rs.getInt("category_id");
        String categoryName = rs.getString("cat_name");
        BigDecimal categoryPercent = rs.getBigDecimal("cat_percent");
        BigDecimal higherLimit = rs.getBigDecimal("higher_limit");
        BigDecimal lowerLimit = rs.getBigDecimal("lower_limit");
        boolean goal = rs.getBoolean("goal");
        int budgetId = rs.getInt("budget_id");

        int userId = rs.getInt("user_id");
        String username = rs.getString("username");
        String email = rs.getString("email");
        String passhash = " ";
        boolean isDeleted = rs.getBoolean("isDeleted");

        // public AppUser(int userId, String username, String email, String passHash, boolean isDeleted,
        // List<MyRole> userRoles)
        AppUser user = new AppUser(userId, username, email, passhash, isDeleted, new ArrayList<>());

        transaction.setUser(user);

        return transaction;
    }
}
