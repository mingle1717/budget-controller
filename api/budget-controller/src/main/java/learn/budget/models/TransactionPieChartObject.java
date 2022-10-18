package learn.budget.models;

import java.math.BigDecimal;

public class TransactionPieChartObject {
    private BigDecimal value;
    private String name;

    public TransactionPieChartObject(BigDecimal value, String name) {
        this.value = value;
        this.name = name;
    }
    public TransactionPieChartObject(){}

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
