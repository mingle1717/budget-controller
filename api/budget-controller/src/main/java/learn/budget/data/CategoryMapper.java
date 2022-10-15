package learn.budget.data;

import learn.budget.models.Category;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CategoryMapper implements RowMapper<Category> {
    @Override
    public Category mapRow(ResultSet rs, int rowNum) throws SQLException {
        Category category = new Category();

        category.setCategoryId(rs.getInt("category_id"));
        category.setCategoryName(rs.getString("cat_name"));
        category.setCategoryPercent(rs.getBigDecimal("cat_percent"));
        category.setHigherLimit(rs.getBigDecimal("higher_limit"));
        category.setLowerLimit(rs.getBigDecimal("lower_limit"));
        category.setGoal(rs.getBoolean("goal"));
        category.setBudgetId(rs.getInt("budget_id"));

        return category;
    }
}