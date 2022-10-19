package learn.budget.domain;

import learn.budget.data.CategoryJdbcRepository;
import learn.budget.models.AppUser;
import learn.budget.models.Budget;
import learn.budget.models.Category;
import learn.budget.models.MyRole;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
public class CategoryServiceTest {

    @Autowired
    CategoryService service;

    @MockBean
    CategoryJdbcRepository repository;
    @Test
    void shouldReturnTrueForValidCategory() {
        Category category = new Category();
        category.setCategoryName("Gym Membership");
        category.setCategoryPercent(BigDecimal.valueOf(20));
        category.setGoal(false);
        category.setLowerLimit(BigDecimal.valueOf(100));
        category.setHigherLimit(BigDecimal.valueOf(150));
        category.setBudgetId(1);

        Result<Category> validation = service.validateCategory(category);
        assertTrue(validation.isSuccess());
        assertFalse(validation.getMessages().size() > 0);
    }
    @Test
    void shouldReturnFalseForCategoryWithNullName() {
        Category category = new Category();
        category.setCategoryName(null);
        category.setCategoryPercent(BigDecimal.valueOf(20));
        category.setGoal(false);
        category.setLowerLimit(BigDecimal.valueOf(100));
        category.setHigherLimit(BigDecimal.valueOf(150));
        category.setBudgetId(1);

        Result<Category> validation = service.validateCategory(category);
        assertFalse(validation.isSuccess());
        assertEquals(1, validation.getMessages().size());
        assertEquals("Category name must be provided.", validation.getMessages().get(0));
    }
    @Test
    void shouldReturnFalseForCategoryWithBlankName() {
        Category category = new Category();
        category.setCategoryName(" ");
        category.setCategoryPercent(BigDecimal.valueOf(20));
        category.setGoal(false);
        category.setLowerLimit(BigDecimal.valueOf(100));
        category.setHigherLimit(BigDecimal.valueOf(150));
        category.setBudgetId(1);

        Result<Category> validation = service.validateCategory(category);
        assertFalse(validation.isSuccess());
        assertEquals(1, validation.getMessages().size());
        assertEquals("Category name must be provided.", validation.getMessages().get(0));
    }
    @Test
    void shouldReturnFalseForCategoryWithNullPercentage() {
        Category category = new Category();
        category.setCategoryName("Gym Membership");
        category.setCategoryPercent(null);
        category.setGoal(false);
        category.setLowerLimit(BigDecimal.valueOf(100));
        category.setHigherLimit(BigDecimal.valueOf(150));
        category.setBudgetId(1);

        Result<Category> validation = service.validateCategory(category);
        assertFalse(validation.isSuccess());
        assertEquals(1, validation.getMessages().size());
        assertEquals("Category percent must be provided.", validation.getMessages().get(0));
    }
    @Test
    void shouldReturnFalseForCategoryWithNullHigherLimit() {
        Category category = new Category();
        category.setCategoryName("Gym Membership");
        category.setCategoryPercent(BigDecimal.valueOf(20));
        category.setGoal(false);
        category.setLowerLimit(BigDecimal.valueOf(100));
        category.setHigherLimit(null);
        category.setBudgetId(1);

        Result<Category> validation = service.validateCategory(category);
        assertFalse(validation.isSuccess());
        assertEquals(1, validation.getMessages().size());
        assertEquals("Category higher limit must be provided.", validation.getMessages().get(0));
    }


    @Test
    void shouldReturnFalseForCategoryWithNullLowerLimit() {
        Category category = new Category();
        category.setCategoryName("Gym Membership");
        category.setCategoryPercent(BigDecimal.valueOf(20));
        category.setGoal(false);
        category.setLowerLimit(null);
        category.setHigherLimit(BigDecimal.valueOf(150));
        category.setBudgetId(1);

        Result<Category> validation = service.validateCategory(category);
        assertFalse(validation.isSuccess());
        assertEquals(1, validation.getMessages().size());
        assertEquals("Category lower limit must be provided.", validation.getMessages().get(0));
    }
    @Test
    void shouldReturnFalseForCategoryWithNegativeHigherLimit() {
        Category category = new Category();
        category.setCategoryName("Gym Membership");
        category.setCategoryPercent(BigDecimal.valueOf(20));
        category.setGoal(false);
        category.setLowerLimit(BigDecimal.valueOf(100));
        category.setHigherLimit(BigDecimal.valueOf(-150));
        category.setBudgetId(1);

        Result<Category> validation = service.validateCategory(category);
        assertFalse(validation.isSuccess());
        assertEquals(1, validation.getMessages().size());
        assertEquals("Category higher limit must be a positive number", validation.getMessages().get(0));
    }
    @Test
    void shouldReturnFalseForCategoryWithNegativeLowerLimit() {
        Category category = new Category();
        category.setCategoryName("Gym Membership");
        category.setCategoryPercent(BigDecimal.valueOf(20));
        category.setGoal(false);
        category.setLowerLimit(BigDecimal.valueOf(-100));
        category.setHigherLimit(BigDecimal.valueOf(150));
        category.setBudgetId(1);

        Result<Category> validation = service.validateCategory(category);
        assertFalse(validation.isSuccess());
        assertEquals(1, validation.getMessages().size());
        assertEquals("Category lower limit must be a positive number", validation.getMessages().get(0));
    }
    @Test
    void shouldReturnFalseForCategoryWithNegativePercentage() {
        Category category = new Category();
        category.setCategoryName("Gym Membership");
        category.setCategoryPercent(BigDecimal.valueOf(-20));
        category.setGoal(false);
        category.setLowerLimit(BigDecimal.valueOf(100));
        category.setHigherLimit(BigDecimal.valueOf(150));
        category.setBudgetId(1);

        Result<Category> validation = service.validateCategory(category);
        assertFalse(validation.isSuccess());
        assertEquals(1, validation.getMessages().size());
        assertEquals("Category percent must be a positive number.", validation.getMessages().get(0));
    }
    @Test
    void shouldEditValidSetOfCategories() {
        // Building a budget with all fields in order to input it in the editCategory method for the tests.
        Budget budget = new Budget();
        List<MyRole> budgetRoles = new ArrayList<>();
        List<AppUser> roleUsers = new ArrayList<>(); // stand in for role users to fill in field, but not tested here
        List<AppUser> budgetUsers = new ArrayList<>();
        budgetUsers.add(new AppUser(1, "Benny Bootstrap", "bb@benny.gov",
                "DAFJKLHASKHFDI481248", false, budgetRoles));
        budgetRoles.add(new MyRole(1, "Admin", roleUsers));

        budget.setBudgetId(1);
        budget.setBalance(BigDecimal.valueOf(2000));
        //budget.setAppUsers(budgetUsers);
        List<Category> categories = new ArrayList<>();

        Category catOne = new Category(1, "Gym Membership", BigDecimal.valueOf(20), BigDecimal.valueOf(120),
                BigDecimal.valueOf(90), true, budget.getBudgetId());

        categories.add(catOne);

        Category catTwo = new Category(2, "Dog Food", BigDecimal.valueOf(5), BigDecimal.valueOf(40),
                BigDecimal.valueOf(20), false, budget.getBudgetId());

        categories.add(catTwo);

        budget.setCategories(categories);

        // repo always should work, but these when clauses should not be reached if the result has messages
        when(repository.editCategory(catOne)).thenReturn(true);
        when(repository.editCategory(catTwo)).thenReturn(true);

        Result<Budget> validation = service.editBudgetCategories(budget);
        assertTrue(validation.isSuccess());
        assertEquals(0, validation.getMessages().size());
    }
    @Test
    void shouldNotEditCategoriesIfAnyCategoryHasNullName() {
        Budget budget = new Budget();
        List<MyRole> budgetRoles = new ArrayList<>();
        List<AppUser> roleUsers = new ArrayList<>();
        List<AppUser> budgetUsers = new ArrayList<>();
        budgetUsers.add(new AppUser(1, "Benny Bootstrap", "bb@benny.gov",
                "DAFJKLHASKHFDI481248", false, budgetRoles));
        budgetRoles.add(new MyRole(1, "Admin", roleUsers));

        budget.setBudgetId(1);
        budget.setBalance(BigDecimal.valueOf(2000));
        //budget.setAppUsers(budgetUsers);
        List<Category> categories = new ArrayList<>();

        Category catOne = new Category(1, null, BigDecimal.valueOf(20), BigDecimal.valueOf(120),
                BigDecimal.valueOf(90), true, budget.getBudgetId());

        categories.add(catOne);

        Category catTwo = new Category(2, "Dog Food", BigDecimal.valueOf(5), BigDecimal.valueOf(40),
                BigDecimal.valueOf(20), false, budget.getBudgetId());

        categories.add(catTwo);

        budget.setCategories(categories);

        when(repository.editCategory(catOne)).thenReturn(true);
        when(repository.editCategory(catTwo)).thenReturn(true);

        Result<Budget> validation = service.editBudgetCategories(budget);
        assertFalse(validation.isSuccess());
        assertEquals(1, validation.getMessages().size());
        assertEquals("Category name must be provided.", validation.getMessages().get(0));
    }
    @Test
    void shouldNotEditCategoriesIfAnyCategoryHasBlankName() {
        Budget budget = new Budget();
        List<MyRole> budgetRoles = new ArrayList<>();
        List<AppUser> roleUsers = new ArrayList<>();
        List<AppUser> budgetUsers = new ArrayList<>();
        budgetUsers.add(new AppUser(1, "Benny Bootstrap", "bb@benny.gov",
                "DAFJKLHASKHFDI481248", false, budgetRoles));
        budgetRoles.add(new MyRole(1, "Admin", roleUsers));

        budget.setBudgetId(1);
        budget.setBalance(BigDecimal.valueOf(2000));
        //budget.setAppUsers(budgetUsers);
        List<Category> categories = new ArrayList<>();

        Category catOne = new Category(1, " ", BigDecimal.valueOf(20), BigDecimal.valueOf(120),
                BigDecimal.valueOf(90), true, budget.getBudgetId());

        categories.add(catOne);

        Category catTwo = new Category(2, "Dog Food", BigDecimal.valueOf(5), BigDecimal.valueOf(40),
                BigDecimal.valueOf(20), false, budget.getBudgetId());

        categories.add(catTwo);

        budget.setCategories(categories);

        when(repository.editCategory(catOne)).thenReturn(true);
        when(repository.editCategory(catTwo)).thenReturn(true);

        Result<Budget> validation = service.editBudgetCategories(budget);
        assertFalse(validation.isSuccess());
        assertEquals(1, validation.getMessages().size());
        assertEquals("Category name must be provided.", validation.getMessages().get(0));
    }

    @Test
    void shouldNotEditCategoriesIfAnyCategoryHasNullPercent() {
        Budget budget = new Budget();
        List<MyRole> budgetRoles = new ArrayList<>();
        List<AppUser> roleUsers = new ArrayList<>();
        List<AppUser> budgetUsers = new ArrayList<>();
        budgetUsers.add(new AppUser(1, "Benny Bootstrap", "bb@benny.gov",
                "DAFJKLHASKHFDI481248", false, budgetRoles));
        budgetRoles.add(new MyRole(1, "Admin", roleUsers));

        budget.setBudgetId(1);
        budget.setBalance(BigDecimal.valueOf(2000));
        //budget.setAppUsers(budgetUsers);
        List<Category> categories = new ArrayList<>();

        Category catOne = new Category(1, "Gym Membership", null, BigDecimal.valueOf(120),
                BigDecimal.valueOf(90), true, budget.getBudgetId());

        categories.add(catOne);

        Category catTwo = new Category(2, "Dog Food", BigDecimal.valueOf(5), BigDecimal.valueOf(40),
                BigDecimal.valueOf(20), false, budget.getBudgetId());

        categories.add(catTwo);

        budget.setCategories(categories);

        when(repository.editCategory(catOne)).thenReturn(true);
        when(repository.editCategory(catTwo)).thenReturn(true);

        Result<Budget> validation = service.editBudgetCategories(budget);
        assertFalse(validation.isSuccess());
        assertEquals(1, validation.getMessages().size());
        assertEquals("Category percent must be provided.", validation.getMessages().get(0));
    }
    @Test
    void shouldNotEditCategoriesIfAnyCategoryHasNullLowerLimit() {
        Budget budget = new Budget();
        List<MyRole> budgetRoles = new ArrayList<>();
        List<AppUser> roleUsers = new ArrayList<>();
        List<AppUser> budgetUsers = new ArrayList<>();
        budgetUsers.add(new AppUser(1, "Benny Bootstrap", "bb@benny.gov",
                "DAFJKLHASKHFDI481248", false, budgetRoles));
        budgetRoles.add(new MyRole(1, "Admin", roleUsers));

        budget.setBudgetId(1);
        budget.setBalance(BigDecimal.valueOf(2000));
        //budget.setAppUsers(budgetUsers);
        List<Category> categories = new ArrayList<>();

        Category catOne = new Category(1, "Gym Membership", BigDecimal.valueOf(20), BigDecimal.valueOf(120),
                null, true, budget.getBudgetId());

        categories.add(catOne);

        Category catTwo = new Category(2, "Dog Food", BigDecimal.valueOf(5), BigDecimal.valueOf(40),
                BigDecimal.valueOf(20), false, budget.getBudgetId());

        categories.add(catTwo);

        budget.setCategories(categories);

        when(repository.editCategory(catOne)).thenReturn(true);
        when(repository.editCategory(catTwo)).thenReturn(true);

        Result<Budget> validation = service.editBudgetCategories(budget);
        assertFalse(validation.isSuccess());
        assertEquals(1, validation.getMessages().size());
        assertEquals("Category lower limit must be provided.", validation.getMessages().get(0));
    }
    @Test
    void shouldNotEditCategoriesIfAnyCategoryHasNullHigherLimit() {
        Budget budget = new Budget();
        List<MyRole> budgetRoles = new ArrayList<>();
        List<AppUser> roleUsers = new ArrayList<>();
        List<AppUser> budgetUsers = new ArrayList<>();
        budgetUsers.add(new AppUser(1, "Benny Bootstrap", "bb@benny.gov",
                "DAFJKLHASKHFDI481248", false, budgetRoles));
        budgetRoles.add(new MyRole(1, "Admin", roleUsers));

        budget.setBudgetId(1);
        budget.setBalance(BigDecimal.valueOf(2000));
        //budget.setAppUsers(budgetUsers);
        List<Category> categories = new ArrayList<>();

        Category catOne = new Category(1, "Gym Membership", BigDecimal.valueOf(20), null,
                BigDecimal.valueOf(90), true, budget.getBudgetId());

        categories.add(catOne);

        Category catTwo = new Category(2, "Dog Food", BigDecimal.valueOf(5), BigDecimal.valueOf(40),
                BigDecimal.valueOf(20), false, budget.getBudgetId());

        categories.add(catTwo);

        budget.setCategories(categories);

        when(repository.editCategory(catOne)).thenReturn(true);
        when(repository.editCategory(catTwo)).thenReturn(true);

        Result<Budget> validation = service.editBudgetCategories(budget);
        assertFalse(validation.isSuccess());
        assertEquals(1, validation.getMessages().size());
        assertEquals("Category higher limit must be provided.", validation.getMessages().get(0));
    }
    @Test
    void shouldNotEditCategoriesIfAnyCategoryHasNegativeLowerLimit() {
        Budget budget = new Budget();
        List<MyRole> budgetRoles = new ArrayList<>();
        List<AppUser> roleUsers = new ArrayList<>();
        List<AppUser> budgetUsers = new ArrayList<>();
        budgetUsers.add(new AppUser(1, "Benny Bootstrap", "bb@benny.gov",
                "DAFJKLHASKHFDI481248", false, budgetRoles));
        budgetRoles.add(new MyRole(1, "Admin", roleUsers));

        budget.setBudgetId(1);
        budget.setBalance(BigDecimal.valueOf(2000));
        //budget.setAppUsers(budgetUsers);
        List<Category> categories = new ArrayList<>();

        Category catOne = new Category(1, "Gym Membership", BigDecimal.valueOf(20), BigDecimal.valueOf(120),
                BigDecimal.valueOf(-90), true, budget.getBudgetId());

        categories.add(catOne);

        Category catTwo = new Category(2, "Dog Food", BigDecimal.valueOf(5), BigDecimal.valueOf(40),
                BigDecimal.valueOf(20), false, budget.getBudgetId());

        categories.add(catTwo);

        budget.setCategories(categories);

        when(repository.editCategory(catOne)).thenReturn(true);
        when(repository.editCategory(catTwo)).thenReturn(true);

        Result<Budget> validation = service.editBudgetCategories(budget);
        assertFalse(validation.isSuccess());
        assertEquals(1, validation.getMessages().size());
        assertEquals("Category lower limit must be a positive number", validation.getMessages().get(0));
    }
    @Test
    void shouldNotEditCategoriesIfAnyCategoryHasNegativeHigherLimit() {
        Budget budget = new Budget();
        List<MyRole> budgetRoles = new ArrayList<>();
        List<AppUser> roleUsers = new ArrayList<>();
        List<AppUser> budgetUsers = new ArrayList<>();
        budgetUsers.add(new AppUser(1, "Benny Bootstrap", "bb@benny.gov",
                "DAFJKLHASKHFDI481248", false, budgetRoles));
        budgetRoles.add(new MyRole(1, "Admin", roleUsers));

        budget.setBudgetId(1);
        budget.setBalance(BigDecimal.valueOf(2000));
        //budget.setAppUsers(budgetUsers);
        List<Category> categories = new ArrayList<>();

        Category catOne = new Category(1, "Gym Membership", BigDecimal.valueOf(20), BigDecimal.valueOf(-120),
                BigDecimal.valueOf(90), true, budget.getBudgetId());

        categories.add(catOne);

        Category catTwo = new Category(2, "Dog Food", BigDecimal.valueOf(5), BigDecimal.valueOf(40),
                BigDecimal.valueOf(20), false, budget.getBudgetId());

        categories.add(catTwo);

        budget.setCategories(categories);

        when(repository.editCategory(catOne)).thenReturn(true);
        when(repository.editCategory(catTwo)).thenReturn(true);

        Result<Budget> validation = service.editBudgetCategories(budget);
        assertFalse(validation.isSuccess());
        assertEquals(1, validation.getMessages().size());
        assertEquals("Category higher limit must be a positive number", validation.getMessages().get(0));
    }
    @Test
    void shouldNotEditCategoriesIfAnyCategoryHasNegativePercentage() {
        Budget budget = new Budget();
        List<MyRole> budgetRoles = new ArrayList<>();
        List<AppUser> roleUsers = new ArrayList<>();
        List<AppUser> budgetUsers = new ArrayList<>();
        budgetUsers.add(new AppUser(1, "Benny Bootstrap", "bb@benny.gov",
                "DAFJKLHASKHFDI481248", false, budgetRoles));
        budgetRoles.add(new MyRole(1, "Admin", roleUsers));

        budget.setBudgetId(1);
        budget.setBalance(BigDecimal.valueOf(2000));
       // budget.setAppUsers(budgetUsers);
        List<Category> categories = new ArrayList<>();

        Category catOne = new Category(1, "Gym Membership", BigDecimal.valueOf(-20), BigDecimal.valueOf(120),
                BigDecimal.valueOf(90), true, budget.getBudgetId());

        categories.add(catOne);

        Category catTwo = new Category(2, "Dog Food", BigDecimal.valueOf(5), BigDecimal.valueOf(40),
                BigDecimal.valueOf(20), false, budget.getBudgetId());

        categories.add(catTwo);

        budget.setCategories(categories);

        when(repository.editCategory(catOne)).thenReturn(true);
        when(repository.editCategory(catTwo)).thenReturn(true);

        Result<Budget> validation = service.editBudgetCategories(budget);
        assertFalse(validation.isSuccess());
        assertEquals(1, validation.getMessages().size());
        assertEquals("Category percent must be a positive number.", validation.getMessages().get(0));
    }
    @Test
    void shouldNotEditCategoriesIfPercentagesAddUpPastOneHundredPercent() {
        Budget budget = new Budget();
        List<MyRole> budgetRoles = new ArrayList<>();
        List<AppUser> roleUsers = new ArrayList<>();
        List<AppUser> budgetUsers = new ArrayList<>();
        budgetUsers.add(new AppUser(1, "Benny Bootstrap", "bb@benny.gov",
                "DAFJKLHASKHFDI481248", false, budgetRoles));
        budgetRoles.add(new MyRole(1, "Admin", roleUsers));

        budget.setBudgetId(1);
        budget.setBalance(BigDecimal.valueOf(2000));
        //budget.setAppUsers(budgetUsers);
        List<Category> categories = new ArrayList<>();

        Category catOne = new Category(1, "Gym Membership", BigDecimal.valueOf(20), BigDecimal.valueOf(120),
                BigDecimal.valueOf(90), true, budget.getBudgetId());

        categories.add(catOne);

        // setting catTwo percentage so that it adds the total percentage up to 101.
        Category catTwo = new Category(2, "Dog Food", BigDecimal.valueOf(81), BigDecimal.valueOf(40),
                BigDecimal.valueOf(20), false, budget.getBudgetId());

        categories.add(catTwo);

        budget.setCategories(categories);

        when(repository.editCategory(catOne)).thenReturn(true);
        when(repository.editCategory(catTwo)).thenReturn(true);

        Result<Budget> validation = service.editBudgetCategories(budget);
        assertFalse(validation.isSuccess());
        assertEquals(1, validation.getMessages().size());
        assertEquals("The categories must add up to be no greater than 100.", validation.getMessages().get(0));
    }
    @Test
    void shouldHaveCorrectNumberOfResultErrorMessagesForMultipleFailingCategoriesWhenEditingCategories() {
        Budget budget = new Budget();
        List<MyRole> budgetRoles = new ArrayList<>();
        List<AppUser> roleUsers = new ArrayList<>();
        List<AppUser> budgetUsers = new ArrayList<>();
        budgetUsers.add(new AppUser(1, "Benny Bootstrap", "bb@benny.gov",
                "DAFJKLHASKHFDI481248", false, budgetRoles));
        budgetRoles.add(new MyRole(1, "Admin", roleUsers));

        budget.setBudgetId(1);
        budget.setBalance(BigDecimal.valueOf(2000));
        //budget.setAppUsers(budgetUsers);
        List<Category> categories = new ArrayList<>();

        Category catOne = new Category(1, null, BigDecimal.valueOf(20), BigDecimal.valueOf(120),
                BigDecimal.valueOf(90), true, budget.getBudgetId());

        categories.add(catOne);

        Category catTwo = new Category(2, "Dog Food", BigDecimal.valueOf(85), BigDecimal.valueOf(40),
                BigDecimal.valueOf(20), false, budget.getBudgetId());

        categories.add(catTwo);

        budget.setCategories(categories);

        when(repository.editCategory(catOne)).thenReturn(true);
        when(repository.editCategory(catTwo)).thenReturn(true);

        Result<Budget> validation = service.editBudgetCategories(budget);
        assertFalse(validation.isSuccess());
        assertEquals(2, validation.getMessages().size());
        assertEquals("Category name must be provided.", validation.getMessages().get(0));
        assertEquals("The categories must add up to be no greater than 100.", validation.getMessages().get(1));
    }
    // TODO: 10/13/2022 Write tests for getBudgetCategoryDetails
    @Test
    void shouldGetBudgetCategoryDetailsForValidPartiallyHydratedBudget() {
        Budget budget = new Budget();
        Budget mockBudget = new Budget();
        mockBudget.setCategories(new ArrayList<>());
        when(repository.findAllCategoriesForABudget(budget)).thenReturn(mockBudget);
        budget.setBudgetName("Benny Bootstrap's Budget");
        budget.setBalance(BigDecimal.TEN);
        Result<Budget> validation = service.getBudgetCategoryDetails(budget);
        assertTrue(validation.isSuccess());
        assertEquals(0, validation.getMessages().size());
    }
    @Test
    void shouldNotGetBudgetCategoryDetailsForBudgetWithNullBalance() {
        Budget budget = new Budget();
        when(repository.findAllCategoriesForABudget(budget)).thenReturn(new Budget());
        budget.setBudgetName("Benny Bootstrap's Budget");
        budget.setBalance(null);
        Result<Budget> validation = service.getBudgetCategoryDetails(budget);
        assertFalse(validation.isSuccess());
        assertEquals(1, validation.getMessages().size());
    }
    @Test
    void shouldNotGetBudgetCategoryDetailsForBudgetWithBalanceOfZero() {
        Budget budget = new Budget();
        when(repository.findAllCategoriesForABudget(budget)).thenReturn(new Budget());
        budget.setBudgetName("Benny Bootstrap's Budget");
        budget.setBalance(BigDecimal.ZERO);
        Result<Budget> validation = service.getBudgetCategoryDetails(budget);
        assertFalse(validation.isSuccess());
        assertEquals(1, validation.getMessages().size());
    }
    @Test
    void shouldNotGetBudgetCategoryDetailsForBudgetWithNullBudgetName() {
        Budget budget = new Budget();
        when(repository.findAllCategoriesForABudget(budget)).thenReturn(new Budget());
        budget.setBudgetName(null);
        budget.setBalance(BigDecimal.TEN);
        Result<Budget> validation = service.getBudgetCategoryDetails(budget);
        assertFalse(validation.isSuccess());
        assertEquals(1, validation.getMessages().size());
    }
    @Test
    void shouldNotGetBudgetCategoryDetailsForBudgetWithBlankBudgetName() {
        Budget budget = new Budget();
        when(repository.findAllCategoriesForABudget(budget)).thenReturn(new Budget());
        budget.setBudgetName(" ");
        budget.setBalance(BigDecimal.TEN);
        Result<Budget> validation = service.getBudgetCategoryDetails(budget);
        assertFalse(validation.isSuccess());
        assertEquals(1, validation.getMessages().size());
    }
    @Test
    void shouldNotGetBudgetCategoryDetailsForBudgetWithNullCategoryList() {
        Budget budget = new Budget();
        Budget mockBudget = new Budget();
        mockBudget.setCategories(null);
        when(repository.findAllCategoriesForABudget(budget)).thenReturn(mockBudget);
        budget.setBudgetName("Benny Bootstrap's Budget");
        budget.setBalance(BigDecimal.TEN);
        Result<Budget> validation = service.getBudgetCategoryDetails(budget);
        assertFalse(validation.isSuccess());
        assertEquals(1, validation.getMessages().size());
    }
}