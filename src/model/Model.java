package model;

import javax.swing.table.DefaultTableModel;
import java.util.Vector;

public class Model {
    // Using DefaultTableModel over TableModel because it has built-in
    // support for dynamic data manipulation and basic sorting/filtering
    DefaultTableModel tableModel;

    public Model() {
        tableModel = new DefaultTableModel();
    }

    public Model(Vector<BudgetItem> budgetItems) {
        for (int i = 0; i < budgetItems.size(); ++i) {
            tableModel.addRow(new BudgetItem[]{budgetItems.get(i)});
        }


    }
}