package learn.budget.data;

import learn.budget.models.Transaction;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface TransactionRepository {
    List<Transaction> findAll();

    Transaction addTransaction(Transaction transaction);

    boolean update(Transaction transactionUpdate);

    @Transactional
    boolean deleteById(int transactionId);
}
