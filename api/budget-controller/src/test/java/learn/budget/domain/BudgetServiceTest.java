package learn.budget.domain;

import learn.budget.data.BudgetJdbcRepository;
import learn.budget.data.UserJdbcRepo;
import learn.budget.models.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
public class BudgetServiceTest {

    @Autowired
    BudgetService service;


    @MockBean
    BudgetJdbcRepository budgetRepo;
    @MockBean
    UserJdbcRepo userJdbcRepo;

    // TODO: 10/13/2022 Set when clauses for all these tests.
    @Test
    void shouldCreateValidBudget() {
        Budget budget = new Budget();
        List<MyRole> budgetRoles = new ArrayList<>();
        AppUser benny = new AppUser(1, "Benny Bootstrap", "bb@benny.gov",
                "DAFJKLHASKHFDI481248", false, budgetRoles);

        List<AppUser> roleUsers = new ArrayList<>();
        roleUsers.add(benny);
        budgetRoles.add(new MyRole(1, "Admin", roleUsers));


        List<AppUser> budgetUsers = new ArrayList<>();
        budgetUsers.add(benny);

        budget.setBudgetId(1);
        budget.setStartingBalance(BigDecimal.valueOf(2000));
        budget.setAppUsers(budgetUsers);
        List<Category> categories = new ArrayList<>();

        Category catOne = new Category(1, "Gym Membership", BigDecimal.valueOf(20), BigDecimal.valueOf(120),
                BigDecimal.valueOf(90), true, budget.getBudgetId());

        categories.add(catOne);

        Category catTwo = new Category(2, "Dog Food", BigDecimal.valueOf(5), BigDecimal.valueOf(40),
                BigDecimal.valueOf(20), false, budget.getBudgetId());

        categories.add(catTwo);

        budget.setCategories(categories);
        when(budgetRepo.createBudget(budget)).thenReturn(budget);
        Result<Budget> validation = service.createBudget(budget);
        assertTrue(validation.isSuccess());
        assertEquals(0, validation.getMessages().size());
    }
    @Test
    void shouldNotCreateBudgetWithNullBalance() {
        Budget budget = new Budget();
        List<MyRole> budgetRoles = new ArrayList<>();
        AppUser benny = new AppUser(1, "Benny Bootstrap", "bb@benny.gov",
                "DAFJKLHASKHFDI481248", false, budgetRoles);

        List<AppUser> roleUsers = new ArrayList<>();
        roleUsers.add(benny);
        budgetRoles.add(new MyRole(1, "Admin", roleUsers));


        List<AppUser> budgetUsers = new ArrayList<>();
        budgetUsers.add(benny);

        budget.setBudgetId(1);
        budget.setStartingBalance(null);
        budget.setAppUsers(budgetUsers);
        List<Category> categories = new ArrayList<>();

        Category catOne = new Category(1, "Gym Membership", BigDecimal.valueOf(20), BigDecimal.valueOf(120),
                BigDecimal.valueOf(90), true, budget.getBudgetId());

        categories.add(catOne);

        Category catTwo = new Category(2, "Dog Food", BigDecimal.valueOf(5), BigDecimal.valueOf(40),
                BigDecimal.valueOf(20), false, budget.getBudgetId());

        categories.add(catTwo);

        budget.setCategories(categories);
        when(budgetRepo.createBudget(budget)).thenReturn(budget);

        Result<Budget> validation = service.createBudget(budget);
        assertFalse(validation.isSuccess());
        assertEquals(1, validation.getMessages().size());
        assertEquals("Please enter a balance greater than zero.", validation.getMessages().get(0));
        // TODO: 10/13/2022 Make messages for this test and all other tests
    }
    @Test
    void shouldNotCreateBudgetWithNegativeBalance() {
        Budget budget = new Budget();
        List<MyRole> budgetRoles = new ArrayList<>();
        AppUser benny = new AppUser(1, "Benny Bootstrap", "bb@benny.gov",
                "DAFJKLHASKHFDI481248", false, budgetRoles);

        List<AppUser> roleUsers = new ArrayList<>();
        roleUsers.add(benny);
        budgetRoles.add(new MyRole(1, "Admin", roleUsers));


        List<AppUser> budgetUsers = new ArrayList<>();
        budgetUsers.add(benny);

        budget.setBudgetId(1);
        budget.setStartingBalance(BigDecimal.valueOf(-2000));
        budget.setAppUsers(budgetUsers);
        List<Category> categories = new ArrayList<>();

        Category catOne = new Category(1, "Gym Membership", BigDecimal.valueOf(20), BigDecimal.valueOf(120),
                BigDecimal.valueOf(90), true, budget.getBudgetId());

        categories.add(catOne);

        Category catTwo = new Category(2, "Dog Food", BigDecimal.valueOf(5), BigDecimal.valueOf(40),
                BigDecimal.valueOf(20), false, budget.getBudgetId());

        categories.add(catTwo);

        budget.setCategories(categories);
        when(budgetRepo.createBudget(budget)).thenReturn(budget);
        Result<Budget> validation = service.createBudget(budget);
        assertFalse(validation.isSuccess());
        assertEquals(1, validation.getMessages().size());
        assertEquals("Please enter a balance greater than zero.", validation.getMessages().get(0));
    }
    @Test
    void shouldNotCreateBudgetWithNullAppUsers() {
        Budget budget = new Budget();
        List<MyRole> budgetRoles = new ArrayList<>();
        AppUser benny = new AppUser(1, "Benny Bootstrap", "bb@benny.gov",
                "DAFJKLHASKHFDI481248", false, budgetRoles);

        List<AppUser> roleUsers = new ArrayList<>();
        roleUsers.add(benny);
        budgetRoles.add(new MyRole(1, "Admin", roleUsers));

        budget.setBudgetId(1);
        budget.setStartingBalance(BigDecimal.valueOf(2000));
        budget.setAppUsers(null);
        List<Category> categories = new ArrayList<>();

        Category catOne = new Category(1, "Gym Membership", BigDecimal.valueOf(20), BigDecimal.valueOf(120),
                BigDecimal.valueOf(90), true, budget.getBudgetId());

        categories.add(catOne);

        Category catTwo = new Category(2, "Dog Food", BigDecimal.valueOf(5), BigDecimal.valueOf(40),
                BigDecimal.valueOf(20), false, budget.getBudgetId());

        categories.add(catTwo);

        budget.setCategories(categories);
        when(budgetRepo.createBudget(budget)).thenReturn(budget);
        Result<Budget> validation = service.createBudget(budget);
        assertFalse(validation.isSuccess());
        assertEquals(1, validation.getMessages().size());
        assertEquals("There are no specified users for this budget.", validation.getMessages().get(0));
    }
    @Test
    void shouldNotCreateBudgetWithZeroAppUsers() {
        Budget budget = new Budget();
        List<MyRole> budgetRoles = new ArrayList<>();
        AppUser benny = new AppUser(1, "Benny Bootstrap", "bb@benny.gov",
                "DAFJKLHASKHFDI481248", false, budgetRoles);

        List<AppUser> roleUsers = new ArrayList<>();
        roleUsers.add(benny);
        budgetRoles.add(new MyRole(1, "Admin", roleUsers));


        List<AppUser> budgetUsers = new ArrayList<>(); // leaving this list empty

        budget.setBudgetId(1);
        budget.setStartingBalance(BigDecimal.valueOf(2000));
        budget.setAppUsers(budgetUsers);
        List<Category> categories = new ArrayList<>();

        Category catOne = new Category(1, "Gym Membership", BigDecimal.valueOf(20), BigDecimal.valueOf(120),
                BigDecimal.valueOf(90), true, budget.getBudgetId());

        categories.add(catOne);

        Category catTwo = new Category(2, "Dog Food", BigDecimal.valueOf(5), BigDecimal.valueOf(40),
                BigDecimal.valueOf(20), false, budget.getBudgetId());

        categories.add(catTwo);

        budget.setCategories(categories);
        when(budgetRepo.createBudget(budget)).thenReturn(budget);
        Result<Budget> validation = service.createBudget(budget);
        assertFalse(validation.isSuccess());
        assertEquals(1, validation.getMessages().size());
        assertEquals("There are no specified users for this budget.", validation.getMessages().get(0));
    }
    @Test
    void shouldNotCreateBudgetWithNullCategories() {
        Budget budget = new Budget();
        List<MyRole> budgetRoles = new ArrayList<>();
        AppUser benny = new AppUser(1, "Benny Bootstrap", "bb@benny.gov",
                "DAFJKLHASKHFDI481248", false, budgetRoles);

        List<AppUser> roleUsers = new ArrayList<>();
        roleUsers.add(benny);
        budgetRoles.add(new MyRole(1, "Admin", roleUsers));


        List<AppUser> budgetUsers = new ArrayList<>();
        budgetUsers.add(benny);

        budget.setBudgetId(1);
        budget.setStartingBalance(BigDecimal.valueOf(2000));
        budget.setAppUsers(budgetUsers);
        budget.setCategories(null);
        when(budgetRepo.createBudget(budget)).thenReturn(budget);
        Result<Budget> validation = service.createBudget(budget);
        assertFalse(validation.isSuccess());
        assertEquals(1, validation.getMessages().size());
        assertEquals("The list of categories is null!", validation.getMessages().get(0));
    }
    @Test
    void shouldCreateAValidBudgetWithAutomaticallyAddedSavingsCategoryOfSeventyFivePercent() {
        Budget budget = new Budget();
        List<MyRole> budgetRoles = new ArrayList<>();
        AppUser benny = new AppUser(1, "Benny Bootstrap", "bb@benny.gov",
                "DAFJKLHASKHFDI481248", false, budgetRoles);

        List<AppUser> roleUsers = new ArrayList<>();
        roleUsers.add(benny);
        budgetRoles.add(new MyRole(1, "Admin", roleUsers));


        List<AppUser> budgetUsers = new ArrayList<>();
        budgetUsers.add(benny);

        budget.setBudgetId(1);
        budget.setStartingBalance(BigDecimal.valueOf(2000));
        budget.setAppUsers(budgetUsers);
        List<Category> categories = new ArrayList<>();

        Category catOne = new Category(1, "Gym Membership", BigDecimal.valueOf(20), BigDecimal.valueOf(120),
                BigDecimal.valueOf(90), true, budget.getBudgetId());

        categories.add(catOne);

        Category catTwo = new Category(2, "Dog Food", BigDecimal.valueOf(5), BigDecimal.valueOf(40),
                BigDecimal.valueOf(20), false, budget.getBudgetId());

        categories.add(catTwo);

        budget.setCategories(categories);
        when(budgetRepo.createBudget(budget)).thenReturn(budget);
        Result<Budget> validation = service.createBudget(budget);
        assertTrue(validation.isSuccess());
        assertEquals(0, validation.getMessages().size());
        assertEquals(BigDecimal.valueOf(75), budget.getCategories().get(0).getCategoryPercent());
    }
    @Test
    void shouldCreateAValidBudgetWithAutomaticallyAddedSavingsCategoryOfZeroPercent() {
        Budget budget = new Budget();
        List<MyRole> budgetRoles = new ArrayList<>();
        AppUser benny = new AppUser(1, "Benny Bootstrap", "bb@benny.gov",
                "DAFJKLHASKHFDI481248", false, budgetRoles);

        List<AppUser> roleUsers = new ArrayList<>();
        roleUsers.add(benny);
        budgetRoles.add(new MyRole(1, "Admin", roleUsers));


        List<AppUser> budgetUsers = new ArrayList<>();
        budgetUsers.add(benny);

        budget.setBudgetId(1);
        budget.setStartingBalance(BigDecimal.valueOf(2000));
        budget.setAppUsers(budgetUsers);
        List<Category> categories = new ArrayList<>();

        Category catOne = new Category(1, "Gym Membership", BigDecimal.valueOf(20), BigDecimal.valueOf(120),
                BigDecimal.valueOf(90), true, budget.getBudgetId());

        categories.add(catOne);

        Category catTwo = new Category(2, "Dog Food", BigDecimal.valueOf(80), BigDecimal.valueOf(40),
                BigDecimal.valueOf(20), false, budget.getBudgetId());

        categories.add(catTwo);

        budget.setCategories(categories);
        when(budgetRepo.createBudget(budget)).thenReturn(budget);
        Result<Budget> validation = service.createBudget(budget);
        assertTrue(validation.isSuccess());
        assertEquals(0, validation.getMessages().size());
        assertEquals(BigDecimal.ZERO, budget.getCategories().get(0).getCategoryPercent());
    }
    @Test
    void shouldNotCreateBudgetIfAnyCategoryHasNullName() {
        Budget budget = new Budget();
        List<MyRole> budgetRoles = new ArrayList<>();
        AppUser benny = new AppUser(1, "Benny Bootstrap", "bb@benny.gov",
                "DAFJKLHASKHFDI481248", false, budgetRoles);

        List<AppUser> roleUsers = new ArrayList<>();
        roleUsers.add(benny);
        budgetRoles.add(new MyRole(1, "Admin", roleUsers));


        List<AppUser> budgetUsers = new ArrayList<>();
        budgetUsers.add(benny);

        budget.setBudgetId(1);
        budget.setStartingBalance(BigDecimal.valueOf(2000));
        budget.setAppUsers(budgetUsers);
        List<Category> categories = new ArrayList<>();

        Category catOne = new Category(1, null, BigDecimal.valueOf(20), BigDecimal.valueOf(120),
                BigDecimal.valueOf(90), true, budget.getBudgetId());

        categories.add(catOne);

        Category catTwo = new Category(2, "Dog Food", BigDecimal.valueOf(5), BigDecimal.valueOf(40),
                BigDecimal.valueOf(20), false, budget.getBudgetId());

        categories.add(catTwo);

        budget.setCategories(categories);
        when(budgetRepo.createBudget(budget)).thenReturn(budget);
        Result<Budget> validation = service.createBudget(budget);
        assertFalse(validation.isSuccess());
        assertEquals(1, validation.getMessages().size());
        assertEquals("Category name must be provided.", validation.getMessages().get(0));
    }
    @Test
    void shouldNotCreateBudgetIfAnyCategoryHasBlankName() {
        Budget budget = new Budget();
        List<MyRole> budgetRoles = new ArrayList<>();
        AppUser benny = new AppUser(1, "Benny Bootstrap", "bb@benny.gov",
                "DAFJKLHASKHFDI481248", false, budgetRoles);

        List<AppUser> roleUsers = new ArrayList<>();
        roleUsers.add(benny);
        budgetRoles.add(new MyRole(1, "Admin", roleUsers));


        List<AppUser> budgetUsers = new ArrayList<>();
        budgetUsers.add(benny);

        budget.setBudgetId(1);
        budget.setStartingBalance(BigDecimal.valueOf(2000));
        budget.setAppUsers(budgetUsers);
        List<Category> categories = new ArrayList<>();

        Category catOne = new Category(1, "Gym Membership", BigDecimal.valueOf(20), BigDecimal.valueOf(120),
                BigDecimal.valueOf(90), true, budget.getBudgetId());

        categories.add(catOne);

        Category catTwo = new Category(2, " ", BigDecimal.valueOf(5), BigDecimal.valueOf(40),
                BigDecimal.valueOf(20), false, budget.getBudgetId());

        categories.add(catTwo);

        budget.setCategories(categories);
        when(budgetRepo.createBudget(budget)).thenReturn(budget);
        Result<Budget> validation = service.createBudget(budget);
        assertFalse(validation.isSuccess());
        assertEquals(1, validation.getMessages().size());
        assertEquals("Category name must be provided.", validation.getMessages().get(0));
    }
    @Test
    void shouldNotCreateBudgetIfAnyCategoryHasNullPercentage() {
        Budget budget = new Budget();
        List<MyRole> budgetRoles = new ArrayList<>();
        AppUser benny = new AppUser(1, "Benny Bootstrap", "bb@benny.gov",
                "DAFJKLHASKHFDI481248", false, budgetRoles);

        List<AppUser> roleUsers = new ArrayList<>();
        roleUsers.add(benny);
        budgetRoles.add(new MyRole(1, "Admin", roleUsers));


        List<AppUser> budgetUsers = new ArrayList<>();
        budgetUsers.add(benny);

        budget.setBudgetId(1);
        budget.setStartingBalance(BigDecimal.valueOf(2000));
        budget.setAppUsers(budgetUsers);
        List<Category> categories = new ArrayList<>();

        Category catOne = new Category(1, "Gym Membership", null, BigDecimal.valueOf(120),
                BigDecimal.valueOf(90), true, budget.getBudgetId());

        categories.add(catOne);

        Category catTwo = new Category(2, "Dog Food", BigDecimal.valueOf(5), BigDecimal.valueOf(40),
                BigDecimal.valueOf(20), false, budget.getBudgetId());

        categories.add(catTwo);

        budget.setCategories(categories);
        when(budgetRepo.createBudget(budget)).thenReturn(budget);
        Result<Budget> validation = service.createBudget(budget);
        assertFalse(validation.isSuccess());
        assertEquals(1, validation.getMessages().size());
        assertEquals("Category percent must be provided.", validation.getMessages().get(0));
    }
    @Test
    void shouldNotCreateBudgetIfAnyCategoryHasNullHigherLimit() {
        Budget budget = new Budget();
        List<MyRole> budgetRoles = new ArrayList<>();
        AppUser benny = new AppUser(1, "Benny Bootstrap", "bb@benny.gov",
                "DAFJKLHASKHFDI481248", false, budgetRoles);

        List<AppUser> roleUsers = new ArrayList<>();
        roleUsers.add(benny);
        budgetRoles.add(new MyRole(1, "Admin", roleUsers));


        List<AppUser> budgetUsers = new ArrayList<>();
        budgetUsers.add(benny);

        budget.setBudgetId(1);
        budget.setStartingBalance(BigDecimal.valueOf(2000));
        budget.setAppUsers(budgetUsers);
        List<Category> categories = new ArrayList<>();

        Category catOne = new Category(1, "Gym Membership", BigDecimal.valueOf(20), null,
                BigDecimal.valueOf(90), true, budget.getBudgetId());

        categories.add(catOne);

        Category catTwo = new Category(2, "Dog Food", BigDecimal.valueOf(5), BigDecimal.valueOf(40),
                BigDecimal.valueOf(20), false, budget.getBudgetId());

        categories.add(catTwo);

        budget.setCategories(categories);
        when(budgetRepo.createBudget(budget)).thenReturn(budget);
        Result<Budget> validation = service.createBudget(budget);
        assertFalse(validation.isSuccess());
        assertEquals(1, validation.getMessages().size());
        assertEquals("Category higher limit must be provided.", validation.getMessages().get(0));
    }
    @Test
    void shouldNotCreateBudgetIfAnyCategoryHasNullLowerLimit() {
        Budget budget = new Budget();
        List<MyRole> budgetRoles = new ArrayList<>();
        AppUser benny = new AppUser(1, "Benny Bootstrap", "bb@benny.gov",
                "DAFJKLHASKHFDI481248", false, budgetRoles);

        List<AppUser> roleUsers = new ArrayList<>();
        roleUsers.add(benny);
        budgetRoles.add(new MyRole(1, "Admin", roleUsers));


        List<AppUser> budgetUsers = new ArrayList<>();
        budgetUsers.add(benny);

        budget.setBudgetId(1);
        budget.setStartingBalance(BigDecimal.valueOf(2000));
        budget.setAppUsers(budgetUsers);
        List<Category> categories = new ArrayList<>();

        Category catOne = new Category(1, "Gym Membership", BigDecimal.valueOf(20), BigDecimal.valueOf(120),
                BigDecimal.valueOf(90), true, budget.getBudgetId());

        categories.add(catOne);

        Category catTwo = new Category(2, "Dog Food", BigDecimal.valueOf(5), BigDecimal.valueOf(40),
                null, false, budget.getBudgetId());

        categories.add(catTwo);

        budget.setCategories(categories);
        when(budgetRepo.createBudget(budget)).thenReturn(budget);
        Result<Budget> validation = service.createBudget(budget);
        assertFalse(validation.isSuccess());
        assertEquals(1, validation.getMessages().size());
        assertEquals("Category lower limit must be provided.", validation.getMessages().get(0));
    }
    @Test
    void shouldNotCreateBudgetIfAnyCategoryHasNegativeHigherLimit() {
        Budget budget = new Budget();
        List<MyRole> budgetRoles = new ArrayList<>();
        AppUser benny = new AppUser(1, "Benny Bootstrap", "bb@benny.gov",
                "DAFJKLHASKHFDI481248", false, budgetRoles);

        List<AppUser> roleUsers = new ArrayList<>();
        roleUsers.add(benny);
        budgetRoles.add(new MyRole(1, "Admin", roleUsers));


        List<AppUser> budgetUsers = new ArrayList<>();
        budgetUsers.add(benny);

        budget.setBudgetId(1);
        budget.setStartingBalance(BigDecimal.valueOf(2000));
        budget.setAppUsers(budgetUsers);
        List<Category> categories = new ArrayList<>();

        Category catOne = new Category(1, "Gym Membership", BigDecimal.valueOf(20), BigDecimal.valueOf(120),
                BigDecimal.valueOf(90), true, budget.getBudgetId());

        categories.add(catOne);

        Category catTwo = new Category(2, "Dog Food", BigDecimal.valueOf(5), BigDecimal.valueOf(-40),
                BigDecimal.valueOf(20), false, budget.getBudgetId());

        categories.add(catTwo);

        budget.setCategories(categories);
        when(budgetRepo.createBudget(budget)).thenReturn(budget);
        Result<Budget> validation = service.createBudget(budget);
        assertFalse(validation.isSuccess());
        assertEquals(1, validation.getMessages().size());
        assertEquals("Category higher limit must be a positive number", validation.getMessages().get(0));
    }
    @Test
    void shouldNotCreateBudgetIfAnyCategoryHasNegativeLowerLimit() {
        Budget budget = new Budget();
        List<MyRole> budgetRoles = new ArrayList<>();
        AppUser benny = new AppUser(1, "Benny Bootstrap", "bb@benny.gov",
                "DAFJKLHASKHFDI481248", false, budgetRoles);

        List<AppUser> roleUsers = new ArrayList<>();
        roleUsers.add(benny);
        budgetRoles.add(new MyRole(1, "Admin", roleUsers));


        List<AppUser> budgetUsers = new ArrayList<>();
        budgetUsers.add(benny);

        budget.setBudgetId(1);
        budget.setStartingBalance(BigDecimal.valueOf(2000));
        budget.setAppUsers(budgetUsers);
        List<Category> categories = new ArrayList<>();

        Category catOne = new Category(1, "Gym Membership", BigDecimal.valueOf(20), BigDecimal.valueOf(120),
                BigDecimal.valueOf(-90), true, budget.getBudgetId());

        categories.add(catOne);

        Category catTwo = new Category(2, "Dog Food", BigDecimal.valueOf(5), BigDecimal.valueOf(40),
                BigDecimal.valueOf(20), false, budget.getBudgetId());

        categories.add(catTwo);

        budget.setCategories(categories);
        when(budgetRepo.createBudget(budget)).thenReturn(budget);
        Result<Budget> validation = service.createBudget(budget);
        assertFalse(validation.isSuccess());
        assertEquals(1, validation.getMessages().size());
        assertEquals("Category lower limit must be a positive number", validation.getMessages().get(0));
    }
    @Test
    void shouldNotCreateBudgetIfAnyCategoryHasNegativePercentage() {
        Budget budget = new Budget();
        List<MyRole> budgetRoles = new ArrayList<>();
        AppUser benny = new AppUser(1, "Benny Bootstrap", "bb@benny.gov",
                "DAFJKLHASKHFDI481248", false, budgetRoles);

        List<AppUser> roleUsers = new ArrayList<>();
        roleUsers.add(benny);
        budgetRoles.add(new MyRole(1, "Admin", roleUsers));


        List<AppUser> budgetUsers = new ArrayList<>();
        budgetUsers.add(benny);

        budget.setBudgetId(1);
        budget.setStartingBalance(BigDecimal.valueOf(2000));
        budget.setAppUsers(budgetUsers);
        List<Category> categories = new ArrayList<>();

        Category catOne = new Category(1, "Gym Membership", BigDecimal.valueOf(20), BigDecimal.valueOf(120),
                BigDecimal.valueOf(90), true, budget.getBudgetId());

        categories.add(catOne);

        Category catTwo = new Category(2, "Dog Food", BigDecimal.valueOf(-5), BigDecimal.valueOf(40),
                BigDecimal.valueOf(20), false, budget.getBudgetId());

        categories.add(catTwo);

        budget.setCategories(categories);
        when(budgetRepo.createBudget(budget)).thenReturn(budget);
        Result<Budget> validation = service.createBudget(budget);
        assertFalse(validation.isSuccess());
        assertEquals(1, validation.getMessages().size());
        assertEquals("Category percent must be a positive number.", validation.getMessages().get(0));
    }
    @Test
    void shouldNotCreateBudgetIfCategoryPercentagesAddUpPastOneHundred() {
        Budget budget = new Budget();
        List<MyRole> budgetRoles = new ArrayList<>();
        AppUser benny = new AppUser(1, "Benny Bootstrap", "bb@benny.gov",
                "DAFJKLHASKHFDI481248", false, budgetRoles);

        List<AppUser> roleUsers = new ArrayList<>();
        roleUsers.add(benny);
        budgetRoles.add(new MyRole(1, "Admin", roleUsers));


        List<AppUser> budgetUsers = new ArrayList<>();
        budgetUsers.add(benny);

        budget.setBudgetId(1);
        budget.setStartingBalance(BigDecimal.valueOf(2000));
        budget.setAppUsers(budgetUsers);
        List<Category> categories = new ArrayList<>();

        Category catOne = new Category(1, "Gym Membership", BigDecimal.valueOf(20), BigDecimal.valueOf(120),
                BigDecimal.valueOf(90), true, budget.getBudgetId());

        categories.add(catOne);

        Category catTwo = new Category(2, "Dog Food", BigDecimal.valueOf(81), BigDecimal.valueOf(40),
                BigDecimal.valueOf(20), false, budget.getBudgetId());

        categories.add(catTwo);

        budget.setCategories(categories);
        when(budgetRepo.createBudget(budget)).thenReturn(budget);
        Result<Budget> validation = service.createBudget(budget);
        assertFalse(validation.isSuccess());
        assertEquals(1, validation.getMessages().size());
        assertEquals("The categories must add up to be no greater than 100.", validation.getMessages().get(0));
    }
    @Test
    void shouldViewBudgetDetailsForRegisteredUserWithABudget() {
        UserBudget userBudget = new UserBudget(1, true, 1, 1);
        when(userJdbcRepo.getUserByUsername("Kendy")).thenReturn(new AppUser(1, "Kendy", "kendy@email",
                "sfksajfhio305483739", false, new ArrayList<>()));
        when(budgetRepo.getBridgeTableInfo(1)).thenReturn(userBudget);
        when(budgetRepo.findById(userBudget.getBudgetId())).thenReturn(new Budget());
        assertTrue(service.viewBudgetDetails("Kendy").isSuccess());
    }
    @Test
    void shouldNotViewBudgetDetailsForUnregisteredUser() {
        Result<Budget> validation = service.viewBudgetDetails("Kedny");
        assertFalse(validation.isSuccess());
        assertEquals(1, validation.getMessages().size());
        assertEquals("There was no user found in the database with this information.",
                validation.getMessages().get(0));
    }
    @Test
    void shouldNotViewBudgetDetailsForRegisteredUserWithNoBudget() {
        when(userJdbcRepo.getUserByUsername("Kendy")).thenReturn(new AppUser(1, "Kendy", "kendy@email",
                "sfksajfhio305483739", false, new ArrayList<>()));
        when(budgetRepo.getBridgeTableInfo(1)).thenReturn(null);
        assertNull(service.viewBudgetDetails("Kendy"));
    }
}
