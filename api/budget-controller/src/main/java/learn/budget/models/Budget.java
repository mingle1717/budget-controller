package learn.budget.models;

import java.math.BigDecimal;
import java.util.List;

public class Budget {

    private int budgetId;

    private List<AppUser> appUsers;

    private List<Category> categories;

    private BigDecimal startingBalance;

    private String budgetName;

    public Budget(List<AppUser> appUsers, List<Category> categories, List<AutoTrigger> autoTriggers, List<Transaction> transactions, BigDecimal startingBalance, String budgetName) {
        this.appUsers = appUsers;
        this.categories = categories;
        this.startingBalance = startingBalance;
        this.budgetName = budgetName;
    }
    public Budget(){};

    public List<AppUser> getAppUsers() {
        return appUsers;
    }

    public void setAppUsers(List<AppUser> appUsers) {
        this.appUsers = appUsers;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public BigDecimal getStartingBalance() {
        return startingBalance;
    }

    public void setStartingBalance(BigDecimal startingBalance) {
        this.startingBalance = startingBalance;
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
