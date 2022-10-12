package learn.budget.domain;

import learn.budget.models.Budget;
import learn.budget.models.Category;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class CategoryService {
    public Result<Budget> editBudgetCategories(Budget budget) {
        throw new UnsupportedOperationException();
    }

    public Result<Category> validateCategory(Category category) {
        Result<Category> result = new Result<>();

        if (category.getCategoryPercent() == null) {
            result.addMessage("Category percent must be provided.", ResultType.INVALID);
        }
        if (category.getCategoryName() == null || category.getCategoryName().isBlank()) {
            result.addMessage("Category name must be provided.", ResultType.INVALID);
        }
        if (category.getLowerLimit() == null) {
            result.addMessage("Category lower limit must be provided.", ResultType.INVALID);
        }
        if (category.getHigherLimit() == null) {
            result.addMessage("Category higher limit must be provided.", ResultType.INVALID);
        }
        if (category.getLowerLimit().compareTo(BigDecimal.ZERO) < 0) {
            result.addMessage("Category higher limit must be a positive number", ResultType.INVALID);
        }
        if (category.getLowerLimit().compareTo(BigDecimal.ZERO) < 0) {
            result.addMessage("Category lower limit must be a positive number", ResultType.INVALID);
        }
        result.setPayload(category);
        return result;
    }
}
