package learn.budget.models;

public class Category {

    private int categoryId;

    private String categoryName;

    private int categoryPercent;

    private int higherLimit;

    private int lowerLimit;

    private boolean goal;

    private int budgetId;

    public Category(int categoryId, String categoryName, int categoryPercent, int higherLimit, int lowerLimit, boolean goal, int budgetId) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.categoryPercent = categoryPercent;
        this.higherLimit = higherLimit;
        this.lowerLimit = lowerLimit;
        this.goal = goal;
        this.budgetId = budgetId;
    }

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

    public int getCategoryPercent() {
        return categoryPercent;
    }

    public void setCategoryPercent(int categoryPercent) {
        this.categoryPercent = categoryPercent;
    }

    public int getHigherLimit() {
        return higherLimit;
    }

    public void setHigherLimit(int higherLimit) {
        this.higherLimit = higherLimit;
    }

    public int getLowerLimit() {
        return lowerLimit;
    }

    public void setLowerLimit(int lowerLimit) {
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
