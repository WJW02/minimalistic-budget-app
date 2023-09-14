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

/**
 * The class for reading .txt files for a {@link Model}.
 *
 * @author WJW02
 */
public class CustomTXTReader implements CustomReader {
    /**
     * Reads a .txt file and dumps its content in a {@link Model}.
     *
     * @param file the .txt file it reads from
     * @param model the model in which it dumps the content
     * @return <code>true</code> - if the dump was successful, that means that the file contains
     * compatible data for the Model<br>
     * <code>false</code> - otherwise
     * @throws IOException if the file exists but is a directory rather than a regular file,
     * does not exist but cannot be created, or cannot be opened for any other reason
     */
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
            // Dumps the content in the model
            model.getTableModel().setRowCount(0);
            for (int i = 0; i < budgetItems.size(); ++i) {
                BudgetItem item = budgetItems.get(i);
                model.addRow(item);
            }
        }
        return success;
    }
}