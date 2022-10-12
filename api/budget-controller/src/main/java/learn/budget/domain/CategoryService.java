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
        if (result.getMessages().size() > 0) {
            return result;
        }
        // good to go!
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
