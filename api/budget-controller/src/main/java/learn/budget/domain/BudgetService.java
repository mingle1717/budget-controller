package learn.budget.domain;

import learn.budget.data.BudgetJdbcRepository;
import learn.budget.data.MyRoleJdbcRepository;
import learn.budget.data.UserJdbcRepo;
import learn.budget.models.*;
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


    public Result<Budget> createBudget(Budget budget, AppUser owner) {
        Result<Budget> result = new Result();
        if (budget.getBalance() == null || budget.getBalance().compareTo(BigDecimal.ZERO) <= 0) {
            result.addMessage("Please enter a balance greater than zero.", ResultType.INVALID);
        }
//        if (budget.getAppUsers() == null || budget.getAppUsers().size() <= 0) {
//            result.addMessage("There are no specified users for this budget.", ResultType.INVALID);
//        }
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
        // Creating the automatic savings category for each budget
        Category savings = new Category();
        savings.setHigherLimit(budget.getBalance());
        savings.setLowerLimit(BigDecimal.ZERO);
        savings.setCategoryName("Savings");

        // the next line checks if the total percentages are over 100 percent
        if (sum.compareTo(BigDecimal.valueOf(100)) > 0) {
            result.addMessage("The categories must add up to be no greater than 100.", ResultType.INVALID);
        }
        if (sum.compareTo(BigDecimal.valueOf(100)) < 0) {
            savings.setCategoryPercent(BigDecimal.valueOf(100).subtract(sum));
        }
        if (sum.compareTo(BigDecimal.valueOf(100)) == 0) {
            savings.setCategoryPercent(BigDecimal.ZERO);
        }

        if (result.getMessages().size() > 0) {
            return result;
        }

        //AppUser user = budget.getAppUsers().get(0);
        budget.setBudgetName(owner.getUsername() + "'s Budget");
        int userId = owner.getUserId();
        budget.setBudgetId(budgetRepository.createBudget(budget, userId).getBudgetId());

        savings.setBudgetId(budget.getBudgetId());
        // savings is now fully hydrated and can be set as first category
        categories.add(savings);
        categories.addAll(budget.getCategories());
        budget.setCategories(categories);
        for (Category c : budget.getCategories()) {
            c.setBudgetId(budget.getBudgetId());
            categoryService.repository.addCategory(c);
        }
        userJdbcRepo.setUserToAdmin(owner);

        result.setPayload(budget);

        return result;
    }

    public Result<Budget> viewBudgetDetails() {
        AppUser user = (AppUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        // validate that user_id is in the database before having the repo retrieve the existing budget
        Result<Budget> result = new Result<>();
        if (user != null) {
            UserBudget bridge = budgetRepository.getBridgeTableInfo(user.getUserId());
            if (bridge != null) {
                Budget budget = budgetRepository.findById(bridge.getBudgetId());
                // temporary fix, only owner is visible
//                List<AppUser> appUsers = new ArrayList<>();
//                appUsers.add(user);
//                budget.setAppUsers(appUsers);
                //
                    result.setPayload(budget);
                    return result;
            } else {
                result.addMessage("This user does not have a budget.", ResultType.INVALID); // there is a user but they don't have/own a budget
            }
        }
        result.addMessage("There was no user found in the database with this information.", ResultType.INVALID);
        return result; // Note: the above error should never appear.
    }

    public Result<Budget> updateBalanceOrName(Budget budget, AppUser user) {
        Result<Budget> result = new Result();
        int ownerId = budgetRepository.getBudgetOwnerId(budget.getBudgetId());
        if(ownerId != user.getUserId()){
            result.addMessage("Not an authorized user, budget can only be edited by the owner", ResultType.FORBIDDEN);
            return result;
        }
        if (budget.getBalance() == null || budget.getBalance().compareTo(BigDecimal.ZERO) <= 0) {
            result.addMessage("Please enter a balance greater than zero.", ResultType.INVALID);
        }
//        if (budget.getAppUsers() == null || budget.getAppUsers().size() <= 0) {
//            result.addMessage("There are no specified users for this budget.", ResultType.INVALID);
//        }
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

        // finding the savings category and adjusting its percentage
        Category savings = budget.getCategories().get(0);
        // the next line checks if the total percentages are over 100 percent and if the savings category can be changed
        if (sum.compareTo(BigDecimal.valueOf(100)) > 0) {
            BigDecimal toCompare = sum.subtract(savings.getCategoryPercent());
            if (toCompare.compareTo(BigDecimal.valueOf(100)) > 0) {
                result.addMessage("The categories must add up to be no greater than 100.", ResultType.INVALID);
            } else if (toCompare.compareTo(BigDecimal.valueOf(100)) < 0) {
                savings.setCategoryPercent(BigDecimal.valueOf(100).subtract(sum.subtract(savings.getCategoryPercent())));
                // here the app auto-sets savings
            } else if (toCompare.compareTo(BigDecimal.valueOf(100)) == 0) {
                savings.setCategoryPercent(BigDecimal.ZERO);
            }
        }
        if (result.getMessages().size() > 0) {
            return result;
        }
//        if (savings.getCategoryPercent().compareTo(BigDecimal.valueOf(-1)) > 0) {
//            categories.add(savings);
//        }
        categories.addAll(budget.getCategories());
        budget.setCategories(categories);
        if (!budgetRepository.update(budget)) {
            result.addMessage("Error: this budget was not found.", ResultType.NOT_FOUND);
            return result;
        }

        result.setPayload(budget);
        Result<Budget> resultWithCategoriesAdded = categoryService.editBudgetCategories(result.getPayload());
        return resultWithCategoriesAdded;
    }

    public Result<Budget> addMemberToBudget(String username, int budgetId) {
        Result<Budget> result = new Result<>();
        AppUser userToAddToBudget = userJdbcRepo.getUserByUsername(username);
        if (userToAddToBudget == null) {
            result.addMessage("This user does not exist in the database", ResultType.NOT_FOUND);
            return result;
        }
        UserBudget ownership = budgetRepository.getBridgeTableInfo(userToAddToBudget.getUserId());
        if(ownership != null) {

            if (ownership.getBudgetId() != 0 && ownership.getUserId() != 0) { // the user is associated with the budget already
                result.addMessage("This user is already associated with a budget.", ResultType.INVALID);
                return result;
            }
            if (ownership.isOwner()) {
                result.addMessage("This user already has a budget", ResultType.INVALID);
                return result;
            }
        }
        if (budgetRepository.addMemberToBridgeTableWithFalseIsOwnerField(userToAddToBudget, budgetId)) {
            return result;
        } else {
            result.addMessage("Something went wrong in the database", ResultType.INVALID);
            return result;
        }
    }
}
