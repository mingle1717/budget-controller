package learn.budget.controllers;
import learn.budget.domain.Result;
import learn.budget.domain.TransactionService;
import learn.budget.models.AppUser;
import learn.budget.models.Budget;
import org.springframework.beans.factory.annotation.Autowired;
import learn.budget.models.Transaction;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transaction")
@CrossOrigin
public class TransactionController {

    @Autowired
    TransactionService service;

    @GetMapping
    public List<Transaction> viewAllTransactions() {
        AppUser user = (AppUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<Transaction> allTransactions = service.viewAllTransactions(user);
        return allTransactions;
    }
    @GetMapping("/{transactionId}")
    public Object getTransactionById(@PathVariable int transactionId){
        AppUser user = (AppUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Transaction toReturn = service.getTransactionById(transactionId, user);
        if (toReturn != null) {
            return toReturn;
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @PostMapping
    public ResponseEntity<Object> addTransaction(@RequestBody Transaction transaction) {
        AppUser user = (AppUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Result<Transaction> result = service.addTransaction(transaction, user);
        if (result.isSuccess()) {
            return new ResponseEntity<>(result.getPayload(), HttpStatus.CREATED);
        }
        return ErrorResponse.build(result);

    }
    @PutMapping("/{transactionId}")
    public ResponseEntity<Object> editTransaction(@RequestBody Transaction transaction){
        AppUser user = (AppUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Result<Transaction> result = service.editTransaction(transaction, user);
        if(result.isSuccess()){
            return new ResponseEntity<>(result.getPayload(), HttpStatus.NO_CONTENT);
        }
        return ErrorResponse.build(result);
    }
    @DeleteMapping("/{transactionId}")
    public ResponseEntity<Object> deleteTransaction(@PathVariable int transactionId){
        if(service.deleteById(transactionId)){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
