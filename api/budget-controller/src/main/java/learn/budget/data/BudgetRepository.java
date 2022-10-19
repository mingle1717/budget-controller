package learn.budget.data;

import learn.budget.models.Budget;
import org.springframework.transaction.annotation.Transactional;

public interface BudgetRepository {

    @Transactional
    Budget findById(int budgetId);

    Budget createBudget(Budget toAdd, int userId);
    int getBudgetOwnerId(int budgetId);
}
