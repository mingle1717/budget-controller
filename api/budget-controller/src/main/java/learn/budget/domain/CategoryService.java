package learn.budget.domain;

import learn.budget.data.CategoryJdbcRepository;
import learn.budget.models.Budget;
import learn.budget.models.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class CategoryService {
    @Autowired
    CategoryJdbcRepository repository;
    public Result<Budget> editBudgetCategories(Budget budget) {
        List<Category> categories = budget.getCategories();
        Result<Budget> result = new Result<>();
        for (Category c: categories) {
            if (!(validateCategory(c).isSuccess())) {
                for (String m : validateCategory(c).getMessages()) {
                    result.addMessage(m, ResultType.INVALID);
                }
            }
        }
        BigDecimal sum = BigDecimal.ZERO;

        for (Category c: budget.getCategories()) {
            if (c.getCategoryPercent() != null) { // avoids null pointer exception
                sum = sum.add(c.getCategoryPercent());
            }
        }
        // Still need to validate that percentages aren't changed to add up over 100%
        //checks that sum is equal to 100 percent

       // if (sum.compareTo(BigDecimal.valueOf(100)) == 0) {
            // good to go! Set savings to 0 percent
        //    budget.getCategories().get(1).setCategoryPercent(BigDecimal.ZERO);
       // }
        // the next line checks if the sum of all percentages is less than 100 percent and auto sets savings
       // if (sum.compareTo(BigDecimal.valueOf(100)) < 0) {
       //     budget.getCategories().get(1).setCategoryPercent(BigDecimal.valueOf(100).subtract(sum));
       // }

        // the next line checks if the total percentages are over 100 percent
        if (sum.compareTo(BigDecimal.valueOf(100)) > 0){
            result.addMessage("The categories must add up to be no greater than 100.", ResultType.INVALID);
        }
        if (result.getMessages().size() > 0) {
            return result;
        }
        List<Category> updatedCategories = new ArrayList<>();
        for (Category c : categories) {
            if (repository.editCategory(c)) {
                c = repository.findById(c.getCategoryId());
                updatedCategories.add(c);
            }
        }
        budget.setCategories(updatedCategories);
        result.setPayload(budget);
        return result;
    }

    public Result<Category> validateCategory(Category category) {
        Result<Category> result = new Result<>();

        if (category.getCategoryPercent() == null) {
            result.addMessage("Category percent must be provided.", ResultType.INVALID);
        } else if (category.getCategoryPercent().compareTo(BigDecimal.ZERO) < 0) {
            result.addMessage("Category percent must be a positive number.", ResultType.INVALID);
        }
        if (category.getCategoryName() == null || category.getCategoryName().isBlank()) {
            result.addMessage("Category name must be provided.", ResultType.INVALID);
        }
        if (category.getLowerLimit() == null) {
            result.addMessage("Category lower limit must be provided.", ResultType.INVALID);
        } else if (category.getLowerLimit().compareTo(BigDecimal.ZERO) < 0) {
            result.addMessage("Category lower limit must be a positive number", ResultType.INVALID);
        }
        if (category.getHigherLimit() == null) {
            result.addMessage("Category higher limit must be provided.", ResultType.INVALID);
        } else if (category.getHigherLimit().compareTo(BigDecimal.ZERO) < 0) {
            result.addMessage("Category higher limit must be a positive number", ResultType.INVALID);
        }
        result.setPayload(category);
        return result;
    }

    public Result<Budget> getBudgetCategoryDetails(Budget budget) {
        // It is nearly impossible for the budget not to pass these validations, but you can never be too secure.
        Result<Budget> result = new Result<>();
        if (budget.getBalance() == null || budget.getBalance().equals(BigDecimal.ZERO)) {
            result.addMessage("The budget requested has no balance on record.", ResultType.INVALID);
        }
        if (budget.getBudgetName() == null || budget.getBudgetName().isBlank()) {
            result.addMessage("The budget requested has no budget name. This is the developer's fault.", ResultType.INVALID);
        }
        if (result.getMessages().size() > 0) {
            return result;
        }
        Budget fullyHydratedBudget = repository.findAllCategoriesForABudget(budget);
        if (fullyHydratedBudget.getCategories() == null) {
            result.addMessage("There are no categories associated with this budget", ResultType.INVALID);
            //shouldn't reach this since the savings category is on every created budget.
        }

        // TODO: 10/18/2022 When getting a budget, the percentage of the balance for each category
        //  should be calculated and returned.

        //HashMap<Category, BigDecimal> balanceForEachCategory = new HashMap<>();

//        for (Category c : budget.getCategories()) {
//            BigDecimal balance = (budget.getBalance().multiply(c.getCategoryPercent()));
//            balanceForEachCategory.put(c, balance);
//        }
        //budget.setCategoryBalance(balanceForEachCategory);

        result.setPayload(fullyHydratedBudget);
        return result;
    }
}
