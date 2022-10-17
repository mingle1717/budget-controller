package learn.budget.models;

import java.math.BigDecimal;

public class Transaction {

    private int transactionId;


    private String transactionName;

    private BigDecimal transactionAmount;

    private String transacationDescription;

    private Category category;

    private int auto_Id;

    private AppUser user;

    public Transaction(int transactionId, String transactionName, BigDecimal transactionAmount, String transacationDescription, Category category, int auto_Id, AppUser user) {
        this.transactionId = transactionId;
        this.transactionName = transactionName;
        this.transactionAmount = transactionAmount;
        this.transacationDescription = transacationDescription;
        this.auto_Id = auto_Id;
        this.user = user;
        this.category = category;
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

    public String getTransacationDescription() {
        return transacationDescription;
    }

    public void setTransacationDescription(String transacationDescription) {
        this.transacationDescription = transacationDescription;
    }


    public int getAuto_Id() {
        return auto_Id;
    }

    public void setAuto_Id(int auto_Id) {
        this.auto_Id = auto_Id;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public AppUser getUser() {
        return user;
    }

    public void setUser(AppUser user) {
        this.user = user;
    }
}
