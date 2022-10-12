package learn.budget.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

public class KnownGoodState {

    @Autowired
    JdbcTemplate jdbcTemplate;

    static boolean hasRun = false;

    void set() {
        jdbcTemplate.update("call set_known_good_state();");
    }
}
