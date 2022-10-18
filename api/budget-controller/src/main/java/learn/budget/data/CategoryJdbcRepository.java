package learn.budget.data;

import learn.budget.models.Budget;
import learn.budget.models.Category;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class CategoryJdbcRepository implements CategoryRepository {

    private final JdbcTemplate jdbcTemplate;

    public CategoryJdbcRepository(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    @Transactional
    public Category findById(int categoryId){

        final String sql ="SELECT `category_id`, `cat_name`, `cat_percent`," +
                " `higher_limit`, `lower_limit`, `goal`, `budget_id`"
                + "from `Category`"
                + "where `category_id`= ?;";

        return jdbcTemplate.query(sql, new CategoryMapper(), categoryId).stream()
                .findFirst().orElse(null);
    }

    @Override
    @Transactional
    public Category addCategory(Category category, int budgetId){

        final String sql = "INSERT INTO `Category` " +
                "(`cat_name`, `cat_percent`, `higher_limit`, `lower_limit`, `goal`, `budget_id`)"
                + "values (?, ?, ?, ?, ?, ?);";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        int rowsAffected = jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, category.getCategoryName());
            ps.setBigDecimal(2, category.getCategoryPercent());
            ps.setBigDecimal(3, category.getHigherLimit());
            ps.setBigDecimal(4, category.getLowerLimit());
            ps.setBoolean(5, category.isGoal());
            ps.setInt(6, budgetId);
            return ps;
        }, keyHolder);

        if (rowsAffected <= 0) {
            return null;
        }

        category.setCategoryId(keyHolder.getKey().intValue());
        return category;
    }

    @Override
    @Transactional
    public boolean editCategory(Category category) {

        final String sql = " UPDATE `Category` SET"
                + "`cat_name` = ?, "
                + "`cat_percent` = ?, "
                + "`higher_limit` = ?, "
                + "`lower_limit` = ?, "
                + "`goal` = ?, "
                + "`budget_id` = ? "
                + "where `category_id` = ?;";

        return jdbcTemplate.update(sql,
                category.getCategoryName(),
                category.getCategoryPercent(),
                category.getHigherLimit(),
                category.getLowerLimit(),
                category.isGoal(),
                category.getBudgetId()) > 0;
    }

    @Transactional
    public Budget findAllCategoriesForABudget(Budget budget) {

        final String sql = "select `category_id`, `cat_name`, `cat_percent`, " +
                "`higher_limit`, `lower_limit`, `goal`, `budget_id` from `Category` where `budget_id`= ?;";

        List<Category> categories = jdbcTemplate.query(sql, new CategoryMapper(), budget.getBudgetId());
        budget.setCategories(categories);
        return budget;
    }

    @Transactional
    public Category getCategoryByCategoryId(int categoryId) {
        final String sql = "select `category_id`, `cat_name`, `cat_percent`, " +
                "`higher_limit`, `lower_limit`, `goal`, `budget_id` from `Category` where `category_id`= ?;";

        return jdbcTemplate.query(sql, new CategoryMapper(), categoryId).stream().findFirst().orElse(null);
    }
}
