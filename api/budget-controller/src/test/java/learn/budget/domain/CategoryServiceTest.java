package learn.budget.domain;

import learn.budget.data.CategoryJdbcRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
public class CategoryServiceTest {

    @Autowired
    CategoryService service;

    @MockBean
    CategoryJdbcRepository repository;
    // need to make tests for validateCategory and editBudgetCategories
}
