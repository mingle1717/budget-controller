package learn.budget.schedule;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.annotation.Schedules;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Logger;



@Configuration
@EnableAsync // Can use @Asynch to define methods
@EnableScheduling
public class TransactionSchedule {
    //Every AutoTransaction has a Category
    //Every Category has a budget, but the budget split categories
    //Every Category has a # associated with it
    @Schedules({
            @Scheduled(fixedRate = 10000), //Time Frame
            @Scheduled(cron = "0 * * * * MON-FRI") // * is any time
    })
    public void schedule() throws InterruptedException {
        LocalDateTime current = LocalDateTime.now();
        DateTimeFormatter format = DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm:ss");
        String formatDateTime = current.format(format);
        Logger logger = Logger.getLogger("com.api.jar");
        logger.info("Schedule Time" + formatDateTime);
        Thread.sleep(1000);

    }
}
