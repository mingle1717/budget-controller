package learn.budget.data;

import learn.budget.models.AutoTrigger;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface AutoTriggerRepository {
    List<AutoTrigger> findByUser(int userId);

    List<AutoTrigger> findUnexpired();

    @Transactional
    AutoTrigger findById(int autoId);

    AutoTrigger createTrigger(AutoTrigger autoTrigger);

    AutoTrigger updateTrigger(AutoTrigger autoTrigger);
}
