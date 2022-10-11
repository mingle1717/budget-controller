package learn.budget.models;

import java.math.BigDecimal;
import java.util.List;

public class Budget {

    private int budgetId;

    private AppUser appUser;

    private List<Category> categories;

    private BigDecimal balance;

    private String budgetName;

    public Budget(AppUser appUser, List<Category> categories, List<AutoTrigger> autoTriggers, List<Transaction> transactions, BigDecimal balance, String budgetName) {
        this.appUser = appUser;
        this.categories = categories;
        this.balance = balance;
        this.budgetName = budgetName;
    }
    public Budget(){};

    public AppUser getAppUser() {
        return appUser;
    }

    public void setAppUser(AppUser appUser) {
        this.appUser = appUser;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public String getBudgetName() {
        return budgetName;
    }

    public void setBudgetName(String budgetName) {
        this.budgetName = budgetName;
    }

    public int getBudgetId() {
        return budgetId;
    }

    public void setBudgetId(int budgetId) {
        this.budgetId = budgetId;
    }
}
