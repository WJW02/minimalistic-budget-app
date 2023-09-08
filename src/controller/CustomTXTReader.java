package controller;

import model.BudgetItem;
import model.Model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Vector;

public class CustomTXTReader implements CustomReader {
    @Override
    public boolean read(File file, Model model) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(file.getAbsolutePath()));
        // By reading lines it forces the data structure to be exact
        // That helps to identify not compatible data better
        String line;
        boolean success = true;
        Vector<BudgetItem> budgetItems = new Vector<>();
        while ((line = reader.readLine()) != null) {
            String[] components = line.split("\t");
            if (components.length != 3) {
                success = false;
                break;
            }
            // Not using already created methods because of different error handling and efficiency reasons (and laziness to refactor)
            LocalDate date;
            // Date format check
            try {
                date = LocalDate.parse(components[0]);
            } catch (DateTimeParseException dtpe) {
                success = false;
                break;
            }

            String description = components[1];

            BigDecimal amount;
            // Amount format check
            try {
                amount = new BigDecimal(components[2]);
            } catch (NumberFormatException nfe) {
                success = false;
                break;
            }
            budgetItems.add(new BudgetItem(date, description, amount));
        }
        if (success) {
            for (int i = 0; i < budgetItems.size(); ++i) {
                BudgetItem item = budgetItems.get(i);
                model.addRow(item);
            }
        }
        return success;
    }
}