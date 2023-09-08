package controller;

import model.Model;
import org.jopendocument.dom.spreadsheet.SpreadSheet;
import view.View;

import java.io.*;

public class CustomODSWriter implements CustomWriter {
    @Override
    public void write(Model model, View view, File file) throws IOException {
            SpreadSheet.createEmpty(model.getTableModel()).saveAs(file);
    }
}