package learn.budget.domain;

import learn.budget.data.TransactionJdbcRepository;
import learn.budget.data.UserJdbcRepo;
import learn.budget.models.AppUser;
import learn.budget.models.Transaction;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.util.ArrayList;

import static org.mockito.Mockito.when;

@SpringBootTest
public class TransactionServiceTest {

    @Autowired
    TransactionService service;

    @MockBean
    TransactionJdbcRepository repository;
    @MockBean
    UserJdbcRepo userJdbcRepo;
    // TODO: 10/14/2022 Write tests for AddTransaction, validateTransaction, editTransaction and viewAllTransactions.

    @Test
    void shouldAddValidTransaction() {
    }

}
