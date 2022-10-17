package learn.budget.domain;

import com.cronutils.model.Cron;
import com.cronutils.model.SingleCron;
import com.cronutils.model.definition.CronDefinition;
import com.cronutils.model.definition.CronDefinitionBuilder;
import com.cronutils.parser.CronParser;
import learn.budget.data.AutoTriggerRepository;
import learn.budget.data.UserJdbcRepo;
import learn.budget.models.AppUser;
import learn.budget.models.AutoTrigger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

import static com.cronutils.model.CronType.QUARTZ;



public class AutoTriggerService {

    @Autowired
    AutoTriggerRepository autoTriggerRepository;

    @Autowired
    UserJdbcRepo userJdbcRepo;

    public List<AutoTrigger> viewAllAutoTriggers() {

        return autoTriggerRepository.findUnexpired();
    }

    // TODO: call this service in the page that allows user to edit their autoTrigger
    public List<AutoTrigger> viewTriggersByUser(String username) {
        AppUser user = userJdbcRepo.getUserByUsername(username);

        return autoTriggerRepository.findByUser(user.getUserId());
    }

    public Result<AutoTrigger> addAutoTrigger(AutoTrigger trigger) {
        Result<AutoTrigger> addedAutoTrigger = new Result<>();
        // if validate == true; insert into db
        if (validate(trigger).isSuccess()) {
            AutoTrigger triggerFromRepo = autoTriggerRepository.createTrigger(trigger);
            addedAutoTrigger.setPayload(triggerFromRepo);
            return addedAutoTrigger;
        } else {
            for (String message : validate(trigger).getMessages()) {
                addedAutoTrigger.addMessage(message, ResultType.INVALID);
            }
        }
        return addedAutoTrigger;
    }

    // TODO:
    public Result<AutoTrigger> update(AutoTrigger trigger) {
        Result<AutoTrigger> result = validate(trigger);
        if (!result.isSuccess()) {
            return result;
        }

        if (trigger.getAutoId() <= 0) {
            result.addMessage("AutoID must be set for update", ResultType.INVALID);
            return result;
        }

        if (trigger.getCronSchedule() == null)
            result.addMessage("Cron can't be null for update", ResultType.INVALID);
        {
            if (trigger.getCreationDate() == null)
                result.addMessage("Creation date can't be null for update", ResultType.INVALID);
        }
        if (trigger.getPaymentAmount().compareTo(BigDecimal.ZERO) <= 0)
            result.addMessage("Payment amount for update can't be 0", ResultType.INVALID);
        {
            if (trigger.getLastExecutionDate() == null)
                result.addMessage("Last execution date can't be null for update", ResultType.INVALID);
        }
        if (trigger.getEndDate() == null)
            result.addMessage("End date can't be null for update", ResultType.INVALID);
        {
            if (trigger.getCategoryId() <= 0)
                result.addMessage("CategoryID must be set for update", ResultType.INVALID);
        }
        if (trigger.getUserId() <= 0)
            result.addMessage("UserId must be set for Update", ResultType.INVALID);

        // validate output if null error
        var updated = autoTriggerRepository.updateTrigger(trigger);
        return result;
    }


    private Result<AutoTrigger> validate(AutoTrigger trigger) {
        Result<AutoTrigger> result = new Result<>();
        // validate trigger not null
        if (trigger == null) {
            result.addMessage("Trigger shouldn't be null", ResultType.INVALID);
            return result;
        }
        // validate cron
        if (trigger.getCronSchedule() == null || trigger.getCronSchedule().isEmpty())
            result.addMessage("Trigger Cron Schedule shouldn't be null or empty", ResultType.INVALID);
        else if (!tryParseCron(trigger.getCronSchedule()))
            result.addMessage("Invalid cron expression", ResultType.INVALID);
        {
            // validate payment amount
            if (trigger.getPaymentAmount() == null) {
                result.addMessage("Trigger amount can't be null.", ResultType.INVALID);
            }
            // validate end_date (null or in the future)
            if (trigger.getEndDate() == null) {
                result.addMessage("Trigger end date can't be null", ResultType.INVALID);
            }
            // validate last_execution_date should be null
            if (trigger.getLastExecutionDate() == null) {
                result.addMessage("Trigger last execution date must be provided", ResultType.INVALID);
            }
        }
        return result;
    }

    private boolean tryParseCron(String cronExpression) {
        boolean result;

        try {
            CronDefinition cronDefinition = CronDefinitionBuilder.instanceDefinitionFor(QUARTZ);
            CronParser parser = new CronParser(cronDefinition);

            Cron parsed = parser.parse(cronExpression);

            // Ensure that our cron expression includes only one cron schedule and not multiple
            result = (parsed instanceof SingleCron);
        } catch (NullPointerException | IllegalArgumentException ex) {
            result = false;
        }

        return result;
    }
}