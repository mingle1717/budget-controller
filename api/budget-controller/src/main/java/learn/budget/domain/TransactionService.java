package learn.budget.domain;
import learn.budget.data.CategoryJdbcRepository;
import learn.budget.data.TransactionJdbcRepository;
import learn.budget.data.UserJdbcRepo;
import learn.budget.models.AppUser;
import learn.budget.models.Category;
import learn.budget.models.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


@Service
public class TransactionService {

    @Autowired
    UserJdbcRepo userJdbcRepo;
    @Autowired
    TransactionJdbcRepository repository;
    @Autowired
    CategoryJdbcRepository categoryJdbcRepo;


    public List<Transaction> viewAllTransactions(AppUser user) {
        return repository.findTransactionsByUser(user.getUserId());
    }
    public Result<Transaction> validateTransaction(Transaction transaction){
        Result<Transaction> result = new Result<>();

        if(transaction.getTransactionAmount() == null) {
            result.addMessage("Transaction amount must be added.", ResultType.INVALID);
        } else if (transaction.getTransactionAmount().compareTo(BigDecimal.ZERO) < 0) {
            result.addMessage("Transaction amount must be positive number", ResultType.INVALID);
        }
        if(transaction.getTransactionName() == null || transaction.getTransactionName().isBlank()) {
            result.addMessage("Transaction name must be provided.", ResultType.INVALID);
        }
        if(transaction.getTransacationDescription() == null || transaction.getTransactionName().isBlank()) {
            result.addMessage("Transaction description must be provided.", ResultType.INVALID);
        }
        if(transaction.getCategory().getCategoryId() == 0 || transaction.getCategory().getCategoryName() == null) {
            result.addMessage("There is no category assigned to this transaction.", ResultType.INVALID);
        }
        result.setPayload(transaction);
        return result;
    }

    public Result<Transaction> addTransaction(Transaction transaction) {
        Result<Transaction> addedTransaction = new Result<>();
        AppUser user = userJdbcRepo.getUserByUsername(transaction.getUser().getUsername());
        transaction.setUser(user);

        if (validateTransaction(transaction).isSuccess()) {
            Transaction resultingTransactionFromRepo = repository.addTransaction(transaction);
            addedTransaction.setPayload(resultingTransactionFromRepo);
            return addedTransaction;

        } else {
            for (String message : validateTransaction(transaction).getMessages()) {
                addedTransaction.addMessage(message, ResultType.INVALID);
            }
            return addedTransaction;
        }

    }
    public Result<Transaction> editTransaction(Transaction transaction){
        Result<Transaction> editedTransaction = new Result<>();
        if(validateTransaction(transaction).isSuccess()){
            boolean result = repository.update(transaction);
            if(result) {
                editedTransaction.setPayload(transaction);
            }
            else{
                editedTransaction.addMessage("Update not successful", ResultType.INVALID);
            }
            return editedTransaction;
        }
        else{
            for (String message : validateTransaction(transaction).getMessages()) {
                editedTransaction.addMessage(message, ResultType.INVALID);
            }
            return editedTransaction;
        }
    }
    public boolean deleteById(int transactionId){ return repository.deleteById(transactionId);}

    public Transaction getTransactionById(int transactionId, AppUser user) {
        List<Transaction> toFilter = repository.findTransactionsByUser(user.getUserId());
        for (Transaction t : toFilter) {
            if (t.getTransactionId() == transactionId) {
                return t;
            }
        }
        return null;
    }
}
