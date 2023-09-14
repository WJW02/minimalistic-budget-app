package controller;

import model.Model;
import org.jopendocument.dom.spreadsheet.SpreadSheet;

import java.io.*;

/**
 * The class for writing .ods files from {@link Model}s.
 *
 * @author WJW02
 */
public class CustomODSWriter implements CustomWriter {

    /**
     * Writes a .ods file from a {@link Model}.
     *
     * @param model the model from which it gets the data
     * @param file the file it writes on
     * @throws IOException if the file exists but is a directory rather than a regular file,
     * does not exist but cannot be created, or cannot be opened for any other reason
     */
    @Override
    public void write(Model model, File file) throws IOException {
            SpreadSheet.createEmpty(model.getTableModel()).saveAs(file);
    }
}