package model;

import javax.swing.table.DefaultTableModel;
import java.util.Vector;

public class Model {
    // Using DefaultTableModel over TableModel because it has built-in
    // support for dynamic data manipulation and basic sorting/filtering
    private DefaultTableModel tableModel;
    private String[] columnNames = {"Date", "Description", "Amount"};

    public Model() {
        tableModel = new DefaultTableModel(columnNames, 0);
    }

    public Model(Vector<BudgetItem> budgetItems) {
        this();
        for (int i = 0; i < budgetItems.size(); ++i) {
            tableModel.addRow(new BudgetItem[]{budgetItems.get(i)});
        }
    }

    public DefaultTableModel getTableModel() {
        return tableModel;
    }

}