package learn.budget.models;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

public class Budget {

    private int budgetId;

    private List<AppUser> appUsers;

    private List<Category> categories;

    private BigDecimal balance;

    private HashMap<Category, BigDecimal> categoryBalance;

    private String budgetName;

    public Budget(List<AppUser> appUsers, List<Category> categories, List<AutoTrigger> autoTriggers, List<Transaction> transactions, BigDecimal startingBalance, String budgetName,  HashMap<Category, BigDecimal> categoryBalance) {
        this.appUsers = appUsers;
        this.categories = categories;
        this.balance = startingBalance;
        this.budgetName = budgetName;
        this.categoryBalance = categoryBalance;
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

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal startingBalance) {
        this.balance = startingBalance;
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

    public HashMap<Category, BigDecimal> getCategoryBalance() {
        return categoryBalance;
    }

    public void setCategoryBalance(HashMap<Category, BigDecimal> categoryBalance) {
        this.categoryBalance = categoryBalance;
    }
}
