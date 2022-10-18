package learn.budget.domain;

import learn.budget.data.BudgetJdbcRepository;
import learn.budget.data.UserJdbcRepo;
import learn.budget.models.AppUser;
import learn.budget.models.Budget;
import learn.budget.models.Category;
import learn.budget.models.UserBudget;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class BudgetService {

    @Autowired
    BudgetJdbcRepository budgetRepository;
    @Autowired
    UserJdbcRepo userJdbcRepo;
    @Autowired
    CategoryService categoryService;

    public Result<Budget> createBudget(Budget budget) {
        Result<Budget> result = new Result();
        if (budget.getBalance() == null || budget.getBalance().compareTo(BigDecimal.ZERO) <= 0) {
            result.addMessage("Please enter a balance greater than zero.", ResultType.INVALID);
        }
        if (budget.getAppUsers() == null || budget.getAppUsers().size() <= 0) {
            result.addMessage("There are no specified users for this budget.", ResultType.INVALID);
        }
        if (budget.getCategories() == null) {
            result.addMessage("The list of categories is null!", ResultType.INVALID);
        }
        List<Category> categories = new ArrayList<>();

        if (budget.getCategories() != null) {
            for (Category c : budget.getCategories()) {
                Result<Category> categoryResult = categoryService.validateCategory(c);
                if (!(categoryResult.isSuccess())) {
                    // adds all the messages for failing categories. Accurate but potentially verbose if
                    //the user makes a lot of failing categories.
                    for (String m : categoryResult.getMessages())
                        result.addMessage(m, ResultType.INVALID);
                }
            }
        }

        BigDecimal sum = BigDecimal.ZERO;

        if (budget.getCategories() != null) {
            for (Category c : budget.getCategories()) {
                if (c.getCategoryPercent() != null) {
                    sum = sum.add(c.getCategoryPercent());
                }
            }
        }

        // the next line checks if the total percentages are over 100 percent
        if (sum.compareTo(BigDecimal.valueOf(100)) > 0) {
            result.addMessage("The categories must add up to be no greater than 100.", ResultType.INVALID);
        }
        if (result.getMessages().size() > 0) {
            return result;
        }
        categories.addAll(budget.getCategories());
        //budget.setCategories(categories);
        AppUser user = budget.getAppUsers().get(0);
        budget.setBudgetName(user.getUsername() + "'s Budget");
        int userId = userJdbcRepo.getUserByUsername(user.getUsername()).getUserId();
        budget.setBudgetId(budgetRepository.createBudget(budget, userId).getBudgetId());

        for (Category c : budget.getCategories()) {
            c.setBudgetId(budget.getBudgetId());
            categoryService.repository.addCategory(c);
        }
        userJdbcRepo.setUserToAdmin(user);
        result.setPayload(budget);

        return result;
    }

    public Result<Budget> viewBudgetDetails(String username) {
        AppUser user = (AppUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        // validate that user_id is in the database before having the repo retrieve the existing budget
        Result<Budget> result = new Result<>();
        if (user != null) {
            UserBudget bridge = budgetRepository.getBridgeTableInfo(user.getUserId());
            if (bridge != null) {
                Budget budget = budgetRepository.findById(bridge.getBudgetId());
                // temporary fix, only owner is visible
                List<AppUser> appUsers = new ArrayList<>();
                appUsers.add(user);
                budget.setAppUsers(appUsers);
                //
                    result.setPayload(budget);
                    return result;
            }
            return null; // there is a user but they don't have/own a budget
        }
        result.addMessage("There was no user found in the database with this information.", ResultType.INVALID);
        return result; // Note: the above error should never appear.
    }

    public Result<Budget> updateBalanceOrName(Budget budget) {
        Result<Budget> result = new Result();
        if (budget.getBalance() == null || budget.getBalance().compareTo(BigDecimal.ZERO) <= 0) {
            result.addMessage("Please enter a balance greater than zero.", ResultType.INVALID);
        }
        if (budget.getAppUsers() == null || budget.getAppUsers().size() <= 0) {
            result.addMessage("There are no specified users for this budget.", ResultType.INVALID);
        }
        if (budget.getCategories() == null) {
            result.addMessage("The list of categories is null!", ResultType.INVALID);
        }
        List<Category> categories = new ArrayList<>();

        if (budget.getCategories() != null) {
            for (Category c : budget.getCategories()) {
                Result<Category> categoryResult = categoryService.validateCategory(c);
                if (!(categoryResult.isSuccess())) {
                    // adds all the messages for failing categories. Accurate but potentially verbose if
                    //the user makes a lot of failing categories.
                    for (String m : categoryResult.getMessages())
                        result.addMessage(m, ResultType.INVALID);
                }
            }
        }

        BigDecimal sum = BigDecimal.ZERO;

        if (budget.getCategories() != null) {
            for (Category c : budget.getCategories()) {
                if (c.getCategoryPercent() != null) {
                    sum = sum.add(c.getCategoryPercent());
                }
            }
        }

        // the next line checks if the total percentages are over 100 percent
        if (sum.compareTo(BigDecimal.valueOf(100)) > 0) {
            result.addMessage("The categories must add up to be no greater than 100.", ResultType.INVALID);
        }
        if (result.getMessages().size() > 0) {
            return result;
        }
        categories.addAll(budget.getCategories());
        budget.setCategories(categories);
        if (budgetRepository.update(budget)) {
            result.addMessage("Error: this budget was not found.", ResultType.NOT_FOUND);
            return result;
        }
        for (Category c : budget.getCategories()) {
            c.setBudgetId(budget.getBudgetId());
            categoryService.repository.addCategory(c);
        }
        result.setPayload(budget);

        return result;
    }
}
