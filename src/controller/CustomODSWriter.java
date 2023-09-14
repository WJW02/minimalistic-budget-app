package controller;

import model.Model;
import org.jopendocument.dom.spreadsheet.SpreadSheet;

import java.io.*;

public class CustomODSWriter implements CustomWriter {
    @Override
    public void write(Model model, File file) throws IOException {
            SpreadSheet.createEmpty(model.getTableModel()).saveAs(file);
    }
}