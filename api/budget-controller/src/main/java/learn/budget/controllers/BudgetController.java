package learn.budget.controllers;

import learn.budget.domain.BudgetService;
import learn.budget.domain.CategoryService;
import learn.budget.domain.Result;
import learn.budget.models.Budget;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/budget")
@CrossOrigin
public class BudgetController {

    @Autowired
    BudgetService budgetService;
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

        Result<Budget> partiallyHydratedBudgetResult = budgetService.viewBudgetDetails(username);
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

}
