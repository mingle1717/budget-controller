package learn.budget.data;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class BudgetJdbcRepositoryTest {

    @Autowired
    BudgetJdbcRepository repo;

    @Autowired
    KnownGoodState knownGoodState;

    @BeforeEach
    void setup(){
        knownGoodState.set();
    }

    // TODO: 10/13/2022 Write tests for getBridgeTableInfo, findById, and createBudget

}
