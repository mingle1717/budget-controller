package learn.budget.domain;
import learn.budget.data.TransactionJdbcRepository;
import learn.budget.data.UserJdbcRepo;
import learn.budget.models.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class TransactionService {

    @Autowired
    UserJdbcRepo userJdbcRepo;
    TransactionJdbcRepository repository;

    public List<Transaction> viewAllTransactions(int userId) {
        // TODO: 10/14/2022 validate that user is in database
        List<Transaction> allTransactions = repository.findAll();
        List<Transaction> transactionsForThisUser = new ArrayList<>();
        for (Transaction t : allTransactions) {
            if (t.getUserId() == userId) {
                transactionsForThisUser.add(t);
            }
        }
        return transactionsForThisUser;
    }
}
