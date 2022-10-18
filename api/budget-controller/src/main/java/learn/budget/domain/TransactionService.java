package learn.budget.domain;
import learn.budget.data.BudgetJdbcRepository;
import learn.budget.data.CategoryJdbcRepository;
import learn.budget.data.TransactionJdbcRepository;
import learn.budget.data.UserJdbcRepo;
import learn.budget.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


@Service
public class TransactionService {

    @Autowired
    UserJdbcRepo userJdbcRepo;
    @Autowired
    TransactionJdbcRepository repository;
    @Autowired
    CategoryJdbcRepository categoryJdbcRepo;

    // for calculating totals by category
    @Autowired
    BudgetJdbcRepository budgetJdbcRepo;


    public List<Transaction> viewAllTransactions(AppUser user) {
        return repository.findTransactionsByUser(user.getUserId());
    }

    public List<TransactionPieChartObject> viewTransactionTotalsByCategory(AppUser user) {
        List<Transaction> allTransactions = repository.findTransactionsByUser(user.getUserId());

        Budget budget = budgetJdbcRepo.findById(allTransactions.get(0).getCategory().getBudgetId());


        List<TransactionPieChartObject> toReturn = new ArrayList<>();
        for (Category c: budget.getCategories()) {
            TransactionPieChartObject pieChartObject = new TransactionPieChartObject();
            pieChartObject.setName(c.getCategoryName());
            BigDecimal sumForCategory = BigDecimal.ZERO;

            for (Transaction t : allTransactions) {
                if (t.getCategory().equals(c)) {
                    sumForCategory = sumForCategory.add(t.getTransactionAmount());
                }
            }

            pieChartObject.setValue(sumForCategory);
            toReturn.add(pieChartObject);
        }
        return toReturn;
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
        if(transaction.getCategory() == null || transaction.getCategory().getCategoryId() == 0 || transaction.getCategory().getCategoryName() == null) {
            result.addMessage("There is no category assigned to this transaction.", ResultType.INVALID);
        }
        result.setPayload(transaction);
        return result;
    }

    public Result<Transaction> addTransaction(Transaction transaction, AppUser user) {
        Result<Transaction> addedTransaction = new Result<>();
        transaction.setUser(user);
        transaction.setCategory(categoryJdbcRepo.getCategoryByCategoryId(transaction.getCategory().getCategoryId()));

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
