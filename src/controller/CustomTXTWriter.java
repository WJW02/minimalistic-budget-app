package controller;

import model.Model;
import view.View;

import javax.swing.table.DefaultTableModel;
import java.io.*;
import java.nio.charset.StandardCharsets;

public class CustomTXTWriter implements CustomWriter {
    @Override
    public void write(Model model, View view, File file) throws IOException {
        Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8));
        DefaultTableModel tableModel = model.getTableModel();
        int rowCount = tableModel.getRowCount();
        int columnCount = tableModel.getColumnCount();
        for (int i = 0; i < rowCount; ++i) {
            for (int j = 0; j < columnCount; ++j) {
                writer.write(tableModel.getValueAt(i, j).toString());
                if (j != columnCount - 1) {
                    writer.write("\t");
                }
            }
            writer.write("\n");
        }
        writer.close();
    }
}