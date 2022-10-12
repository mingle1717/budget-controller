package learn.budget.domain;

import learn.budget.data.BudgetJdbcRepository;
import learn.budget.data.CategoryJdbcRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
public class BudgetServiceTest {

    @Autowired
    BudgetService service;

    @MockBean
    CategoryJdbcRepository categoryRepo;

    @MockBean
    BudgetJdbcRepository budgetRepo;

    // need to write tests for createBudget
}
