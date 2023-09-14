package model;

import javax.swing.table.DefaultTableModel;

/**
 * The class providing the model of the application.
 * <p>
 * The model uses a DefaultTableModel over a TableModel because it has built-in
 * support for dynamic data manipulation and basic sorting/filtering.
 *
 * @author WJW02
 */
public class Model {
    /**
     * The actual underlying model used to keep the data.
     */
    private DefaultTableModel tableModel;
    /**
     * The list of names of the columns of {@link #tableModel}.
     */
    private String[] columnNames = {"Date", "Description", "Amount"};

    /**
     * Initializes {@link #tableModel} with no rows and not editable cells.
     */
    public Model() {
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                // Make all cells non-editable
                return false;
            }
        };
    }

    public DefaultTableModel getTableModel() {
        return tableModel;
    }

    /**
     * Adds a new row to {@link #tableModel}.
     *
     * @param item the item added to tableModel
     */
    public void addRow(BudgetItem item) {
        tableModel.addRow(new Object[]{item.getDate(), item.getDescription(), item.getAmount()});
    }

    /**
     * Compares this model with another model.
     *
     * @param model the model it's compared to
     * @return <code>true</code> - if their {@link #tableModel} contain the same values in the same order<br>
     * <code>false</code> - otherwise
     */
    public boolean equals(Model model) {
        DefaultTableModel tableModel1 = this.getTableModel();
        DefaultTableModel tableModel2 = model.getTableModel();
        if (tableModel1.getRowCount() != tableModel2.getRowCount()) {
            return false;
        }
        for (int row = 0; row < tableModel1.getRowCount(); ++row) {
            for (int col = 0; col < tableModel1.getColumnCount(); ++col) {
                String value1 = tableModel1.getValueAt(row, col).toString();
                String value2 = tableModel2.getValueAt(row, col).toString();
                if (!value1.equals(value2)) {
                    return false;
                }
            }
        }
        return true;
    }
}