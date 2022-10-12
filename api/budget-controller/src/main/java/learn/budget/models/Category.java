package learn.budget.models;

import java.math.BigDecimal;

public class Category {

    private int categoryId;

    private String categoryName;

    private BigDecimal categoryPercent;

    private BigDecimal higherLimit;

    private BigDecimal lowerLimit;

    private boolean goal;

    private int budgetId;

    public Category(int categoryId, String categoryName, BigDecimal categoryPercent, BigDecimal higherLimit, BigDecimal lowerLimit, boolean goal, int budgetId) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.categoryPercent = categoryPercent;
        this.higherLimit = higherLimit;
        this.lowerLimit = lowerLimit;
        this.goal = goal;
        this.budgetId = budgetId;
    }

    public Category(){}

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public BigDecimal getCategoryPercent() {
        return categoryPercent;
    }

    public void setCategoryPercent(BigDecimal categoryPercent) {
        this.categoryPercent = categoryPercent;
    }

    public BigDecimal getHigherLimit() {
        return higherLimit;
    }

    public void setHigherLimit(BigDecimal higherLimit) {
        this.higherLimit = higherLimit;
    }

    public BigDecimal getLowerLimit() {
        return lowerLimit;
    }

    public void setLowerLimit(BigDecimal lowerLimit) {
        this.lowerLimit = lowerLimit;
    }

    public boolean isGoal() {
        return goal;
    }

    public void setGoal(boolean goal) {
        this.goal = goal;
    }

    public int getBudgetId() {
        return budgetId;
    }

    public void setBudgetId(int budgetId) {
        this.budgetId = budgetId;
    }
}
