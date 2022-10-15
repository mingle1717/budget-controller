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
@RequestMapping("/api/transaction")
@CrossOrigin
public class TransactionController {

    @Autowired
    TransactionService service;

    @GetMapping("/{username}")
    public List<Transaction> viewAllTransactions(@PathVariable String username) {
        List<Transaction> allTransactions = service.viewAllTransactions(username);
        return allTransactions;
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
