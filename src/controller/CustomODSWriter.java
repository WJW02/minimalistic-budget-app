package controller;

import model.Model;
import org.jopendocument.dom.spreadsheet.SpreadSheet;
import view.View;

import javax.swing.*;
import java.io.*;

public class CustomODSWriter implements CustomWriter {

    @Override
    public void write(Model model, View view, File file) {
        try {
            SpreadSheet.createEmpty(model.getTableModel()).saveAs(file);
        } catch (IOException ioe) {
            JOptionPane.showMessageDialog(view.getFrame(), "Exportation failed", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}