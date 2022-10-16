package learn.budget.schedule;

import com.cronutils.model.definition.CronDefinition;
import com.cronutils.model.definition.CronDefinitionBuilder;
import com.cronutils.model.time.ExecutionTime;
import com.cronutils.parser.CronParser;
import learn.budget.domain.AutoTriggerService;
import learn.budget.domain.TransactionService;
import learn.budget.models.AutoTrigger;
import learn.budget.models.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;


import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

import static com.cronutils.model.CronType.QUARTZ;


@Configuration
@EnableScheduling
public class TransactionSchedule {
    //Every AutoTransaction has a Category
    //Every Category has a budget, but the budget split categories
    //Every Category has a # associated with it
    @Autowired
    AutoTriggerService service;

    @Autowired
    TransactionService transactionService;

    // TODO: schedule ever minute? 5 minutes? 30? hour?
    @Scheduled(fixedRate = 1000)
    public void schedule() {
        List<AutoTrigger> triggers = service.viewAllAutoTriggers();
        CronDefinition cronDefinition = CronDefinitionBuilder.instanceDefinitionFor(QUARTZ);
        CronParser parser = new CronParser(cronDefinition);
        ZonedDateTime now = ZonedDateTime.now();

        for (AutoTrigger trigger : triggers) {
            ExecutionTime executionTime = ExecutionTime.forCron(parser.parse(trigger.getCronSchedule()));
            Optional<ZonedDateTime> lastCronExecution = executionTime.lastExecution(now);

            // invalid cron; ignore and continue
            if (lastCronExecution.isEmpty()) {
                // do not throw an exception otherwise all other transactions will fail
                System.out.println("Invalid CRON expression");
                continue;
            }

            // This shouldn't be necessary
            // Database query should not return transaction where end_date is in the past
            var isOver = trigger.getEndDate() != null && now.toLocalDateTime().compareTo(trigger.getEndDate()) > 0;
            if (isOver) {
                // TODO: better log message
                System.out.println("is over");
                continue;
            }

            // do not execute transaction if the lastCronExecution is before the creation date
            if (trigger.getCreationDate().compareTo(lastCronExecution.get().toLocalDateTime()) > 0) {
                // TODO: better log message
                System.out.println("past");
                continue;
            }

            // execute transaction when:
            //   we've never executed the transaction before
            //   OR the lastCronExecution is smaller our trigger last execution
            if (trigger.getLastExecutionDate() == null ||
                    trigger.getLastExecutionDate().compareTo(lastCronExecution.get().toLocalDateTime()) < 0) {

                var transaction = new Transaction();
                transaction.setTransactionAmount(trigger.getPaymentAmount());
                transaction.setUserId(trigger.getUserId());
                transaction.setAuto_Id(trigger.getAutoId());
                transaction.setCategoryId(trigger.getCategoryId());
                transaction.setTransactionName("Automatic Transaction");
                transactionService.addTransaction(transaction);

                // TODO: update last execution date for auto trigger in the database
                trigger.setLastExecutionDate(now.toLocalDateTime());

                // TODO: better log message? or remove log
                System.out.println("execute");
            }

        }

    }
}
