package learn.budget.data;

import learn.budget.models.AutoTrigger;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AutoTriggerMapper implements RowMapper<AutoTrigger> {


    @Override
    public AutoTrigger mapRow(ResultSet rs, int rowNum) throws SQLException{
        AutoTrigger autoTrigger = new AutoTrigger();

       autoTrigger.setAutoId(rs.getInt("auto_id"));
       autoTrigger.setUserId(rs.getInt("user_id"));
       autoTrigger.setCronSchedule(rs.getString("cron_schedule"));
       autoTrigger.setPaymentAmount(rs.getBigDecimal("payment_amount"));
       autoTrigger.setCategoryId(rs.getInt("category_id"));

       var endDate = rs.getDate("end_date");
       if (!rs.wasNull())
           autoTrigger.setEndDate(endDate.toLocalDate().atStartOfDay());

       var creationDate = rs.getDate("creation_date");
       if (!rs.wasNull())
           autoTrigger.setCreationDate(creationDate.toLocalDate().atStartOfDay());

        var lastExecutionDate = rs.getDate("last_execution_date");
        if (!rs.wasNull())
            autoTrigger.setLastExecutionDate(lastExecutionDate.toLocalDate().atStartOfDay());

        return autoTrigger;
    }
}
