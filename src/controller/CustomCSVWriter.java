package controller;

import model.Model;
import view.View;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.io.*;

public class CustomCSVWriter implements CustomWriter {

    @Override
    public void write(Model model, View view, File file) {
        try (Writer writer = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(file), "utf-8"))) {
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
        } catch (IOException ioe) {
            JOptionPane.showMessageDialog(view.getFrame(), "Exportation failed", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}