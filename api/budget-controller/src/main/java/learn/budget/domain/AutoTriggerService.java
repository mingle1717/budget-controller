package learn.budget.domain;

import learn.budget.models.AutoTrigger;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Service
public class AutoTriggerService {

    // TODO: Import repo (and create them lol)

    private static final LocalDateTime temp = LocalDateTime.now().plusMinutes(1);

    private  static final List<AutoTrigger> triggers = new ArrayList<>(
            List.of(new AutoTrigger(
                    1,
                    1,
                    // " 0/5 * * 1/1 * ?", // every 5 seconds
                    "0 0 12 15 1/1 ? *", // every 15th of the month
                    BigDecimal.ONE,
                    temp,
                    1,
                    LocalDateTime.parse("2021-10-01T00:00:00"),
                    LocalDateTime.parse("2022-09-15T12:00:00")))
    );

    public List<AutoTrigger> viewAllAutoTriggers() {
        // TODO: query database, ignore autoTrigger where autoTrigger.end_date is in the past.

        return triggers;
    }

    // TODO: if schedule is set to day, it shouldn't be possible to create autoTrigger that trigger more than once a day
    // TODO: validate cron expression with https://github.com/jmrozanec/cron-utils validate functions
    public Result<AutoTrigger> addAutoTrigger() {
        // TODO
        return null;
    }

    // TODO: update should allow update of lastExecutionDate
}
