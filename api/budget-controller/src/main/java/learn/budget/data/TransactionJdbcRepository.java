package learn.budget.data;

import learn.budget.models.Transaction;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class TransactionJdbcRepository implements TransactionRepository {

    private final JdbcTemplate jdbcTemplate;

    public TransactionJdbcRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Transaction> findAll() {
        final String sql =
                "SELECT `transaction_id`, `transaction_name`, `transaction_amount`, `transaction_description`, `category_id`, `auto_id`, `user_id` \n" +
                        "from myTransaction";
        return jdbcTemplate.query(sql, new TransactionMapper());
    }

    @Override
    @Transactional
    public Transaction findById(int transactionId){
        final String sql = "SELECT `myTransaction`.`transaction_id`,`myTransaction`.`transaction_name`` myTransaction`.`transaction_amount`,`myTransaction`.`transaction_description`,`myTransaction`.`category_id`,`myTransaction`.`auto_id`,`myTransaction`.`user_id`"
                + "from `calculator_test`.`myTransaction`"
                + " where `transaction_id` = ?;";

        return jdbcTemplate.query(sql, new TransactionMapper(), transactionId).stream()
                .findFirst().orElse(null);
    }

    @Override
    public Transaction addTransaction(Transaction transaction) {
        final String sql = " INSERT INTO `calculator_test`.`myTransaction`" +
                " (`transaction_id`,`transaction_name`,`transaction_amount`,`transaction_description`,`category_id`, +`auto_id`, +`user_id`)" +
                " VALUES (?, ?, ?, ?, ? ,?, ?);";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        int rowsAffected = jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, transaction.getTransactionId());
            ps.setString(2, transaction.getTransactionName());
            ps.setBigDecimal(3, transaction.getTransactionAmount());
            ps.setString(4, transaction.getTransactionDescription());
            ps.setInt(5, transaction.getCategoryId());
            ps.setInt(6, transaction.getAuto_Id());
            ps.setInt(7, transaction.getUserId());
            return ps;
        }, keyHolder);

        if (rowsAffected <= 0) {
            return null;
        }

        transaction.setTransactionId(keyHolder.getKey().intValue());
        return transaction;
    }

    @Override
    public boolean update(Transaction transactionUpdate) {
        final String sql = "UPDATE `calculator_test`.`myTransaction`"
                + "`transaction_name` = ?, "
                + "`transaction_amount` = ?,"
                + "`transaction_description = ?,"
                + "`category_id` = ?, "
                + "`auto_id` = ? "
                + "`user_id` = ? "
                + "where `transaction_id` = ?;";

        return jdbcTemplate.update(sql,
                transactionUpdate.getTransactionName(),
                transactionUpdate.getTransactionAmount(),
                transactionUpdate.getTransactionDescription(),
                transactionUpdate.getCategoryId(),
                transactionUpdate.getAuto_Id(),
                transactionUpdate.getUserId()) > 0;
    }

    @Override
    @Transactional
    public boolean deleteById(int transactionId) {
        return jdbcTemplate.update("delete from `calculator_test`.`myTransaction` where `transaction_id` = ?;", transactionId) > 0;
    }

}
