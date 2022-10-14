package learn.budget.controllers;
import learn.budget.domain.Result;
import learn.budget.domain.TransactionService;
import learn.budget.models.Budget;
import org.springframework.beans.factory.annotation.Autowired;
import learn.budget.models.Transaction;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/transaction")
public class TransactionController {

    @Autowired
    TransactionService service;

    @GetMapping("/{userId}")
    public List<Transaction> viewAllTransactions(@PathVariable int userId) {
        return service.viewAllTransactions(userId);
    }
    @PostMapping
    public ResponseEntity<Object> addTransaction(@RequestBody Transaction transaction) {
        Result<Transaction> result = service.addTransaction(transaction);
        if (result.isSuccess()) {
            return new ResponseEntity<>(result.getPayload(), HttpStatus.CREATED);
        }
        return ErrorResponse.build(result);

    }
}
