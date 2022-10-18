package learn.budget.data;

import learn.budget.models.Category;
import org.springframework.transaction.annotation.Transactional;

public interface CategoryRepository {
    @Transactional
    Category findById(int categoryId);

    Category addCategory(Category category, int budgetId);

    boolean editCategory(Category category);
}
