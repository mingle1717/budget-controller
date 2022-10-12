package learn.budget.data;

import learn.budget.models.Category;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.sql.Statement;

@Repository
public class CategoryJdbcRepository implements CategoryRepository {

    private final JdbcTemplate jdbcTemplate;

    public CategoryJdbcRepository(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    @Transactional
    public Category findById(int categoryId){

        final String sql ="SELECT `category_id`, `cat_name`, `cat_percent`, `higher_limit`, `lower_limit`, `goal`, `budget_id`"
                + "from `calculator_test`.`Category`"
                + "where `category_id`= ?;";

        return jdbcTemplate.query(sql, new CategoryMapper(), categoryId).stream()
                .findFirst().orElse(null);
    }

    @Override
    public Category addCategory(Category category){

        final String sql = "INSERT INTO `calculator_test`.`Category` " +
                "(`category_id`, `cat_name`, `cat_percent`, `higher_limit`, `lower_limit`, `goal`, `budget_id`)"
                + "values (?, ?, ?, ?, ?, ?, ?);";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        int rowsAffected = jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, category.getCategoryId());
            ps.setString(2, category.getCategoryName());
            ps.setInt(3, category.getCategoryPercent());
            ps.setInt(4, category.getHigherLimit());
            ps.setInt(5, category.getLowerLimit());
            ps.setBoolean(6, category.isGoal());
            ps.setInt(7, category.getBudgetId());
            return ps;
        }, keyHolder);

        if (rowsAffected <= 0) {
            return null;
        }

        category.setCategoryId(keyHolder.getKey().intValue());
        return category;
    }

    @Override
    public boolean editCategory(Category category) {

        final String sql = " UPDATE `calculator_test`.`Category` SET"
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
}
