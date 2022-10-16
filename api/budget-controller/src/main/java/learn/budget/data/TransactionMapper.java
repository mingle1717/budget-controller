package learn.budget.data;

import learn.budget.models.Transaction;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TransactionMapper implements RowMapper<Transaction> {
    @Override
    public Transaction mapRow(ResultSet rs, int rowNum) throws SQLException {
        Transaction transaction = new Transaction();

        transaction.setTransactionId(rs.getInt("transaction_id"));
        transaction.setTransactionName(rs.getString("transaction_name"));
        transaction.setTransactionAmount(rs.getBigDecimal("transaction_amount"));
        transaction.setTransacationDescription(rs.getString("transaction_description"));
        transaction.setCategoryId(rs.getInt("category_id"));
        transaction.setAuto_Id(rs.getInt("auto_id"));
        transaction.setUserId(rs.getInt("user_id"));

        return transaction;
    }
}
