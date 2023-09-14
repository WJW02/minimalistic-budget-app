package controller;

import model.Model;

import javax.swing.table.DefaultTableModel;
import java.io.*;
import java.nio.charset.StandardCharsets;

/**
 * The class for writing .csv files from a {@link Model}.
 *
 * @author WJW02
 */
public class CustomCSVWriter implements CustomWriter {

    /**
     * Writes a .csv file from a {@link Model}.
     *
     * @param model the model from which it gets the data
     * @param file the file it writes on
     * @throws IOException if the file exists but is a directory rather than a regular file,
     * does not exist but cannot be created, or cannot be opened for any other reason
     */
    @Override
    public void write(Model model, File file) throws IOException {
        Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8));
        DefaultTableModel tableModel = model.getTableModel();
        int rowCount = tableModel.getRowCount();
        int columnCount = tableModel.getColumnCount();
        for (int i = 0; i < rowCount; ++i) {
            for (int j = 0; j < columnCount; ++j) {
                writer.write(tableModel.getValueAt(i, j).toString());
                if (j != columnCount - 1) {
                    writer.write(",");
                }
            }
            writer.write("\n");
        }
        writer.close();
    }
}