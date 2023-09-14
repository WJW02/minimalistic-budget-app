package model;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * The basic unit of a {@link Model}.
 * <p>
 * It represents a row in a {@link Model#getTableModel()}.
 *
 * @author WJW02
 */
public class BudgetItem {
    /**
     * The date of the item.
     */
    private LocalDate date;
    /**
     * The description of the item.
     */
    private String description;
    /**
     * The amount of the item.
     */
    private BigDecimal amount;

    /**
     * Creates a BudgetItem.
     *
     * @param date the date of the item
     * @param description the description of the item
     * @param amount the amount of the item
     */
    public BudgetItem(LocalDate date, String description, BigDecimal amount) {
        this.date = date;
        this.description = description;
        this.amount = amount;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) { this.date = date; }

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