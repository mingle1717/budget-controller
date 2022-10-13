package learn.budget.domain;

import learn.budget.data.CategoryJdbcRepository;
import learn.budget.models.Budget;
import learn.budget.models.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
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
        if (sum.compareTo(BigDecimal.valueOf(100)) == 0) {
            // good to go! Set savings to 0 percent
            budget.getCategories().get(1).setCategoryPercent(BigDecimal.ZERO);
        }
        // the next line checks if the sum of all percentages is less than 100 percent and auto sets savings
        if (sum.compareTo(BigDecimal.valueOf(100)) < 0) {
            budget.getCategories().get(1).setCategoryPercent(BigDecimal.valueOf(100).subtract(sum));
        }
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
}
