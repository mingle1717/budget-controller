package learn.budget.domain;

import learn.budget.data.TransactionJdbcRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
public class TransactionServiceTest {

    @Autowired
    TransactionService service;

    @MockBean
    TransactionJdbcRepository repository;
    // TODO: 10/14/2022 Write tests for AddTransaction, validateTransaction and viewAllTransactions.
}
