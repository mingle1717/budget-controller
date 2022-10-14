package learn.budget.data;

import learn.budget.models.MyRole;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class MyRoleJdbcRepository implements MyRoleRepository{

    private final JdbcTemplate jdbcTemplate;

    public MyRoleJdbcRepository(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<MyRole> findAll(){
        final String sql =
                "SELECT `myRole`.`role_id`, `myRole`.`role_name`"
                + "from `calculator_test`.`myRole`";
        return jdbcTemplate.query(sql, new MyRoleMapper());
    }

    @Override
    @Transactional
    public MyRole findById(int roleID){
        final String sql =  "SELECT `myRole`.`role_id`, `myRole`.`role_name`"
                + "from `calculator_test`.`myRole`"
                + "where `role_id` = ?;";
        return jdbcTemplate.query(sql, new MyRoleMapper(), roleID).stream()
                .findFirst().orElse(null);
    }
}
