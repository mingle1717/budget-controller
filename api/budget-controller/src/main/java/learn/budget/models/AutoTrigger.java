package learn.budget.models;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

public class AutoTrigger {

    private int autoId;

    private int userId;

    private String cronSchedule;

    private BigDecimal paymentAmount;

    private LocalDateTime endDate;

    private int categoryId;

    private LocalDateTime creationDate;

    private LocalDateTime lastExecutionDate;

    public AutoTrigger(int autoId, int userId, String cronSchedule, BigDecimal paymentAmount, LocalDateTime endDate, int categoryId, LocalDateTime creationDate, LocalDateTime lastExecutionDate) {
        this.autoId = autoId;
        this.userId = userId;
        this.cronSchedule = cronSchedule;
        this.paymentAmount = paymentAmount;
        this.endDate = endDate;
        this.categoryId = categoryId;
        this.creationDate = creationDate;
        this.lastExecutionDate = lastExecutionDate;
    }

    public int getAutoId() {
        return autoId;
    }

    public void setAutoId(int autoId) {
        this.autoId = autoId;
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

    public String getCronSchedule() {
        return cronSchedule;
    }

    public void setCronSchedule(String cronSchedule) {
        this.cronSchedule = cronSchedule;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public LocalDateTime getLastExecutionDate() {
        return lastExecutionDate;
    }

    public void setLastExecutionDate(LocalDateTime lastExecutionDate) {
        this.lastExecutionDate = lastExecutionDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }
}
