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

//    @Override
//    public List<Transaction> findAll() {
//        final String sql =
//                "SELECT `transaction_id`, `transaction_name`, `transaction_amount`, `transaction_description`, `category_id`, `auto_id`, `user_id` \n" +
//                        "from myTransaction";
//        return jdbcTemplate.query(sql, new TransactionMapper());
//    }

//    @Override
//    public Transaction findById(int transactionId){
//        final String sql = "SELECT `transaction_id`,`transaction_name`, `transaction_amount`, `transaction_description`, `category_id`, `auto_id`, `user_id`"
//                + "from `myTransaction`"
//                + " where `transaction_id` = ?;";
//
//        return jdbcTemplate.query(sql, new TransactionMapper(), transactionId).stream()
//                .findFirst().orElse(null);
//    }

    @Override
    @Transactional
    public Transaction addTransaction(Transaction transaction) {
        final String sql = "INSERT INTO `myTransaction` \n" +
                " (`transaction_name`,`transaction_amount`,`transaction_description`,`category_id`, `auto_id`, `user_id`)\n" +
                "VALUES (?, ?, ?, ?, ?, ?);";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        int rowsAffected = jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, transaction.getTransactionName());
            ps.setBigDecimal(2, transaction.getTransactionAmount());
            ps.setString(3, transaction.getTransacationDescription());
            ps.setInt(4, transaction.getCategory().getCategoryId());
            ps.setInt(5, transaction.getAuto_Id());
            ps.setInt(6, transaction.getUser().getUserId());
            return ps;
        }, keyHolder);

        if (rowsAffected <= 0) {
            return null;
        }

        transaction.setTransactionId(keyHolder.getKey().intValue());
        return transaction;
    }

    @Override
    @Transactional
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
                transactionUpdate.getTransacationDescription(),
                transactionUpdate.getCategory().getCategoryId(),
                transactionUpdate.getAuto_Id(),
                transactionUpdate.getUser().getUserId(),
                transactionUpdate.getTransactionId()) > 0;
    }

    @Override
    @Transactional
    public boolean deleteById(int transactionId) {
        return jdbcTemplate.update("delete from `calculator_test`.`myTransaction` where `transaction_id` = ?;", transactionId) > 0;
    }

    @Override
    public List<Transaction> findTransactionsByUser(int userId) {
        return jdbcTemplate.query("select transaction_id, transaction_name, transaction_amount, transaction_description, " +
                        "c.category_id, c.cat_name, c.cat_percent, c.higher_limit,\n" +
                        "c.lower_limit, c.goal, c.budget_id, auto_id, a.user_id, a.username, a.email, a.isDeleted, " +
                        "r.role_id, r.role_name  \n" +
                        "from myTransaction m inner join appUser a on m.user_id = a.user_id " +
                        "inner join Category c on m.category_id = c.category_id " +
                        "inner join myRole r on a.role_id = r.role_id where a.user_id = ?;", new TransactionMapper(),
                userId);
    }


}
