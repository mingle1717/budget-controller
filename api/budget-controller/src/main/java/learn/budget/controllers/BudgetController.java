package learn.budget.controllers;

import learn.budget.domain.BudgetService;
import learn.budget.domain.CategoryService;
import learn.budget.domain.Result;
import learn.budget.models.AppUser;
import learn.budget.models.Budget;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/budget")
@CrossOrigin
public class BudgetController {

    @Autowired
    BudgetService budgetService;

    @Autowired
    CategoryService categoryService;

    @PostMapping
    public ResponseEntity<Object> createBudget(@RequestBody Budget budget) {
        Result<Budget> budgetResult = budgetService.createBudget(budget);
        Result<Budget> budgetCategoryResult = categoryService.editBudgetCategories(budget);
        if (budgetResult.isSuccess() && budgetCategoryResult.isSuccess()) {
            return new ResponseEntity<>(budgetCategoryResult.getPayload(), HttpStatus.CREATED);
        }
        return ErrorResponse.build(budgetResult);
    }

    @GetMapping("/personal/{username}")
    public Object viewBudgetDetails(@PathVariable String username) {
        AppUser user = (AppUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Result<Budget> partiallyHydratedBudgetResult = budgetService.viewBudgetDetails(username);
        if (partiallyHydratedBudgetResult == null) {
            return ErrorResponse.build(partiallyHydratedBudgetResult);
        }

        if (partiallyHydratedBudgetResult.isSuccess()) {
            Result<Budget> budgetResultWithCategories = categoryService.getBudgetCategoryDetails(partiallyHydratedBudgetResult.getPayload());
            if (budgetResultWithCategories.isSuccess()) {
                return budgetResultWithCategories.getPayload();
            } else {
                return ErrorResponse.build(budgetResultWithCategories);
            }
        }
        return ErrorResponse.build(partiallyHydratedBudgetResult);
    }

    @PutMapping("/personal")
    public Object editBudget(@RequestBody Budget budget) {
        Result<Budget> budgetResult = budgetService.updateBalanceOrName(budget);

        if (budgetResult.isSuccess()) {

            Result<Budget> budgetResultWithCategories = categoryService.editBudgetCategories(budget);

            if (budgetResultWithCategories.isSuccess()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else {
                return ErrorResponse.build(budgetResultWithCategories);
            }

        } else {
            return ErrorResponse.build(budgetResult);
        }
    }
    @PostMapping("/addmember")
    public ResponseEntity<Object> addMemberToBudget(@RequestBody String username, @RequestBody int budgetId) {
        Result<Budget> budgetResultWithAddedMember = budgetService.addMemberToBudget(username, budgetId);
        if (budgetResultWithAddedMember.isSuccess()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return ErrorResponse.build(budgetResultWithAddedMember);
        }
    }
}
