package learn.budget.controllers;

import learn.budget.domain.Result;
import learn.budget.domain.TransactionService;
import learn.budget.models.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
