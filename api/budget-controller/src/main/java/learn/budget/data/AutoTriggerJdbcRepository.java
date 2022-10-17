package learn.budget.data;

import learn.budget.models.AutoTrigger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class AutoTriggerJdbcRepository implements AutoTriggerRepository {

    private final JdbcTemplate jdbcTemplate;

    public AutoTriggerJdbcRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<AutoTrigger> findByUser(int userId){
        final String sql = "SELECT `auto_id`,\n" +
                "    `user_id`,\n" +
                "    `cron_schedule`,\n" +
                "    `payment_amount`,\n" +
                "    `end_date`,\n" +
                "    category_id`,\n" +
                "    `creation_date`,\n" +
                "    `last_execution_date`\n" +
                "FROM `autoTrigger` \n" +
                "WHERE user_id = ?";
        return  jdbcTemplate.query(sql, new AutoTriggerMapper(), userId);
    }

    @Override
    public List<AutoTrigger> findUnexpired() {
        final String sql = "SELECT `auto_id`,\n" +
                "    `user_id`,\n" +
                "    `cron_schedule`,\n" +
                "    `payment_amount`,\n" +
                "    `end_date`,\n" +
                "    `category_id`,\n" +
                "    `creation_date`,\n" +
                "    `last_execution_date`\n" +
                "FROM `autoTrigger` \n" +
                "WHERE end_date IS NULL OR end_date > now()";
        return  jdbcTemplate.query(sql, new AutoTriggerMapper());
    }

    @Override
    @Transactional
    public AutoTrigger findById(int autoId) {
        final String sql = "SELECT `auto_id`,\n" +
                "    `user_id`,\n" +
                "    `cron_schedule`,\n" +
                "    `payment_amount`,\n" +
                "    `end_date`,\n" +
                "    category_id`,\n" +
                "    `creation_date`,\n" +
                "    `last_execution_date`\n" +
                "FROM `autoTrigger` " +
                "where `auto_id` = ?'";

        return jdbcTemplate.query(sql, new AutoTriggerMapper(), autoId).stream()
                .findFirst().orElse(null);
    }

    @Override
    public AutoTrigger createTrigger(AutoTrigger autoTrigger){
        final String sql = "INSERT INTO `autoTrigger`\n" +
                "(`auto_id`,\n" +
                "`user_id`,\n" +
                "`cron_schedule`,\n" +
                "`payment_amount`,\n" +
                "`end_date`,\n" +
                "`category_id`,\n" +
                "`creation_date`,\n" +
                "`last_execution_date`)" +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?);";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        int rowsAffected = jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, autoTrigger.getAutoId());
            ps.setInt(2, autoTrigger.getUserId());
            ps.setString(3, autoTrigger.getCronSchedule());
            ps.setBigDecimal(4, autoTrigger.getPaymentAmount());
            ps.setDate(5, Date.valueOf(autoTrigger.getEndDate().toLocalDate()));
            ps.setInt(6, autoTrigger.getCategoryId());
            ps.setDate(7, Date.valueOf(autoTrigger.getCreationDate().toLocalDate()));
            ps.setDate(8, Date.valueOf(autoTrigger.getLastExecutionDate().toLocalDate()));

            return ps;
        }, keyHolder);

        if (rowsAffected <= 0) {
            return null;
        }

        autoTrigger.setAutoId(keyHolder.getKey().intValue());
        return autoTrigger;
    }

    @Override
    public AutoTrigger updateTrigger(AutoTrigger autoTrigger){
        final String sql = "UPDATE `calculator_test`.`autoTrigger`\n" +
                "SET\n" +
                "`cron_schedule` = ?,\n" +
                "`payment_amount` = ?,\n" +
                "`end_date` = ?,\n" +
                "`category_id` = ?,\n" +
                "`last_execution_date` = ?\n" +
                "WHERE `auto_id` = ?";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        int rowsAffected = jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, autoTrigger.getCronSchedule());
            ps.setBigDecimal(2, autoTrigger.getPaymentAmount());
            ps.setDate(3, Date.valueOf(autoTrigger.getEndDate().toLocalDate())); // THIS CAN BE NULL AND WILL EXPLODE AS IT IS
            ps.setInt(4, autoTrigger.getCategoryId());
            ps.setDate(5, Date.valueOf(autoTrigger.getLastExecutionDate().toLocalDate())); // THIS CAN BE NULL AND WILL EXPLODE AS IT IS
            ps.setInt(6, autoTrigger.getAutoId());

            return ps;
        }, keyHolder);

        if (rowsAffected <= 0) {
            return null;
        }

        return autoTrigger;
    }
}
