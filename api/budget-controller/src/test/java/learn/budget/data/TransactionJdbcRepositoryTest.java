package learn.budget.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TransactionJdbcRepositoryTest {

    @Autowired
    TransactionJdbcRepository repo;

    // TODO: 10/13/2022 Write tests for findAll, addTransaction, update, and deleteById.
}
