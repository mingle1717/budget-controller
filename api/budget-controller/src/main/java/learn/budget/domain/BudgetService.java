package learn.budget.domain;

import learn.budget.models.Budget;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@Service
public class BudgetService {
    public Result<Budget> createBudget(Budget budget) {
        //this is to maybe create the budget name and validate the balance.
        /*
        private int budgetId;

    private List<AppUser> appUsers;

    private List<Category> categories;

    private BigDecimal balance;

    private String budgetName;
         */
        Result<Budget> result = new Result();
        if (budget.getBalance().compareTo(BigDecimal.ZERO) <= 0) {
            result.addMessage("Please enter a balance greater than zero.", ResultType.INVALID);
        }
        if (budget.getAppUsers() == null || budget.getAppUsers().size() <= 0) { // unlikely to reach this validation
            result.addMessage("There are no specified users for this budget.", ResultType.INVALID);
        }
        if (budget.getCategories() == null) {
            result.addMessage("The categories specified are not valid.");
        }
    }
}
