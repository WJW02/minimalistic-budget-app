package model;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
public class BudgetItem {
    private Date date;
    private String description;
    private BigDecimal amount;

    public BudgetItem(Date date, String description, BigDecimal amount) {
        this.date = date;
        this.description = description;
        this.amount = amount;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}