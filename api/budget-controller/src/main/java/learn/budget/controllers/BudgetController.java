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
    public Object createBudget(@RequestBody Budget budget) {
        AppUser user = (AppUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Result<Budget> budgetResult = budgetService.createBudget(budget, user);
        Result<Budget> budgetCategoryResult = categoryService.editBudgetCategories(budget);
        if (budgetResult.isSuccess() && budgetCategoryResult.isSuccess()) {
            return new ResponseEntity<>(budgetCategoryResult.getPayload(), HttpStatus.CREATED);
        }
        return budgetResult.getMessages();
    }

    @GetMapping("/personal")
    public Object viewBudgetDetails() {
       // AppUser user = (AppUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Result<Budget> partiallyHydratedBudgetResult = budgetService.viewBudgetDetails();
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
        AppUser user = (AppUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Result<Budget> budgetResult = budgetService.updateBalanceOrName(budget, user);

        if (budgetResult.isSuccess()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return ErrorResponse.build(budgetResult);
        }
    }

    @PostMapping("/addmember")
    public Object addMemberToBudget(@RequestBody String [] userToAdd) {
        Result<Budget> budgetResultWithAddedMember = budgetService.addMemberToBudget(userToAdd[0], Integer.parseInt(userToAdd[1]));
        if (budgetResultWithAddedMember.isSuccess()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return budgetResultWithAddedMember.getMessages();
        }
    }

}
