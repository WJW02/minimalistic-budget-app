package model;
import java.time.LocalDate;
import java.util.Date;
public class BudgetItem {
    private LocalDate date;
    private String description;
    private int amount;

    public BudgetItem(LocalDate date, String description, int amount) {
        this.date = date;
        this.description = description;
        this.amount = amount;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}