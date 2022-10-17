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
   // @GetMapping("/{transactionId}")
   // public Transaction getTransactionById(@PathVariable int transactionId){
        //Transaction toReturn = service.getTransactionById(transactionId);
        //return toReturn;
  //  }
    @PostMapping
    public ResponseEntity<Object> addTransaction(@RequestBody Transaction transaction) {
        Result<Transaction> result = service.addTransaction(transaction);
        if (result.isSuccess()) {
            return new ResponseEntity<>(result.getPayload(), HttpStatus.CREATED);
        }
        return ErrorResponse.build(result);

    }
    @PutMapping("/{transactionId}")
    public ResponseEntity<Object> editTransaction(@RequestBody Transaction transaction){
        Result<Transaction> result = service.editTransaction(transaction);
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
