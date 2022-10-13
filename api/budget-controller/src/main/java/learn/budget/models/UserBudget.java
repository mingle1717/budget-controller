package learn.budget.models;

import org.springframework.security.core.userdetails.User;

public class UserBudget {
    private int userBudgetId;
    private boolean isOwner;
    private int userId;
    private int budgetId;

    public UserBudget(int userBudgetId, boolean isOwner, int userId, int budgetId) {
        this.userBudgetId = userBudgetId;
        this.isOwner = isOwner;
        this.userId = userId;
        this.budgetId = budgetId;
    }
    public UserBudget(){}

    public int getUserBudgetId() {
        return userBudgetId;
    }

    public void setUserBudgetId(int userBudgetId) {
        this.userBudgetId = userBudgetId;
    }

    public boolean isOwner() {
        return isOwner;
    }

    public void setOwner(boolean owner) {
        isOwner = owner;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getBudgetId() {
        return budgetId;
    }

    public void setBudgetId(int budgetId) {
        this.budgetId = budgetId;
    }
}
