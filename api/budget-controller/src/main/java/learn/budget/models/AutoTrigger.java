package learn.budget.models;

import java.math.BigDecimal;
import java.time.LocalDate;

public class AutoTrigger {

    private int autoId;

    private LocalDate triggerDate;

    private BigDecimal paymentAmount;

    private int categoryId;

    public AutoTrigger(int autoId, LocalDate triggerDate, BigDecimal paymentAmount, int categoryId) {
        this.autoId = autoId;
        this.triggerDate = triggerDate;
        this.paymentAmount = paymentAmount;
        this.categoryId = categoryId;
    }

    public int getAutoId() {
        return autoId;
    }

    public void setAutoId(int autoId) {
        this.autoId = autoId;
    }

    public LocalDate getTriggerDate() {
        return triggerDate;
    }

    public void setTriggerDate(LocalDate triggerDate) {
        this.triggerDate = triggerDate;
    }

    public BigDecimal getPaymentAmount() {
        return paymentAmount;
    }

    public void setPaymentAmount(BigDecimal paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }
}
