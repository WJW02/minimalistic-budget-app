package model;

import javax.swing.table.DefaultTableModel;
import java.util.Vector;

public class Model {
    // Using DefaultTableModel over TableModel because it has built-in
    // support for dynamic data manipulation and basic sorting/filtering
    private DefaultTableModel tableModel;
    private String[] columnNames = {"Date", "Description", "Amount"};

    public Model() {
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                // Make all cells non-editable
                return false;
            }
        };
    }

    /*
    public Model(Vector<BudgetItem> budgetItems) {
        this();
        for (int i = 0; i < budgetItems.size(); ++i) {
            BudgetItem item = budgetItems.get(i);
            addRow(item);
        }
    }
    */

    public DefaultTableModel getTableModel() {
        return tableModel;
    }

    public void addRow(BudgetItem item) {
        tableModel.addRow(new Object[]{item.getDate(), item.getDescription(), item.getAmount()});
    }

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