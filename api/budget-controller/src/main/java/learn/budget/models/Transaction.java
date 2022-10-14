package learn.budget.models;

import java.math.BigDecimal;

public class Transaction {

    private int transactionId;

    private String transactionName;

    private BigDecimal transactionAmount;

    private String transactionDescription;



    private String categoryName;

    private int categoryId;

    private int auto_Id;

    private String username;
    private int userId;

    public Transaction(String categoryName, String username, int transactionId, String transactionName, BigDecimal transactionAmount, String transactionDescription, int categoryId, int auto_Id,  int userId) {
        this.transactionId = transactionId;
        this.transactionName = transactionName;
        this.transactionAmount = transactionAmount;
        this.transactionDescription = transactionDescription;
        this.categoryId = categoryId;
        this.auto_Id = auto_Id;
        this.userId = userId;
        this.categoryName = categoryName;
    }

    public Transaction(){}

    public int getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

    public String getTransactionName() {
        return transactionName;
    }

    public void setTransactionName(String transactionName) {
        this.transactionName = transactionName;
    }

    public BigDecimal getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(BigDecimal transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    public String getTransactionDescription() {
        return transactionDescription;
    }

    public void setTransactionDescription(String transactionDescription) {
        this.transactionDescription = transactionDescription;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public int getAuto_Id() {
        return auto_Id;
    }

    public void setAuto_Id(int auto_Id) {
        this.auto_Id = auto_Id;
    }
    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
