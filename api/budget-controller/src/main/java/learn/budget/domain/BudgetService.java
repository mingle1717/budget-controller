package learn.budget.domain;

import learn.budget.data.BudgetJdbcRepository;
import learn.budget.models.Budget;
import learn.budget.models.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class BudgetService {

    @Autowired
    BudgetJdbcRepository budgetRepository;
    @Autowired
    CategoryService categoryService;

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
        if (budget.getCategories() == null) { // also unlikely
            result.addMessage("The list of categories is null!", ResultType.INVALID);
        }
        List<Category> categories = new ArrayList<>();

        for (Category c: budget.getCategories()) {
            Result<Category> categoryResult = categoryService.validateCategory(c);
            if (!(categoryResult.isSuccess())) {
                // adds all the messages for failing categories. Accurate but potentially verbose if
                //the user makes a lot of failing categories.
                for (String m : categoryResult.getMessages())
                result.addMessage(m, ResultType.INVALID);
            }
        }


        Category savings = new Category();
        savings.setBudgetId(budget.getBudgetId());
        savings.setCategoryName("Savings");
        savings.setLowerLimit(BigDecimal.valueOf(0));
        savings.setHigherLimit(budget.getBalance());
        // set this later: savings.setCategoryPercent();
        BigDecimal sum = BigDecimal.ZERO;

        for (Category c: budget.getCategories()) {
            sum = sum.add(c.getCategoryPercent());
        }
        //checks that sum is equal to 100 percent
        if (sum.compareTo(BigDecimal.valueOf(100)) == 0) {
            // good to go! Set savings to 0 percent
            savings.setCategoryPercent(BigDecimal.ZERO);
        }
        // the next line checks if the sum of all percentages is less than 100 percent and auto sets savings
        if (sum.compareTo(BigDecimal.valueOf(100)) < 0) {
            savings.setCategoryPercent(BigDecimal.valueOf(100).subtract(sum));
        }
        // the next line checks if the total percentages are over 100 percent
        if (sum.compareTo(BigDecimal.valueOf(100)) > 0){
            result.addMessage("The categories must add up to be no greater than 100.", ResultType.INVALID);
        }
        if (result.getMessages().size() > 0) {
            return result;
        }
        categories.add(savings); // ensures that savings has category_id 1 always
        categories.addAll(budget.getCategories());
        budget.setCategories(categories);
        budget = budgetRepository.createBudget(budget);
        result.setPayload(budget);

        return result;
    }
}
