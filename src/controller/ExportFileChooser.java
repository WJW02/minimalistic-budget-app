package controller;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;

/**
 * A class providing a customized {@link JFileChooser} for export.
 * <p>
 * This JFileChooser provides 3 export options (csv, txt, ods), basic error handling through dialogs
 * and shows confirmation dialogs when trying to overwrite existing files.
 *
 * @author WJW02
 */
public class ExportFileChooser extends JFileChooser implements CustomFileChooser {
    /**
     * Initializes this JFileChooser with the 3 export options (csv, txt, ods).
     */
    public ExportFileChooser() {
        removeChoosableFileFilter(getFileFilter());     // Remove the default file filter
        addChoosableFileFilter(new FileNameExtensionFilter("CSV file", "csv"));
        addChoosableFileFilter(new FileNameExtensionFilter("TEXT file", "txt"));
        addChoosableFileFilter(new FileNameExtensionFilter("ODS file", "ods"));
    }

    /**
     * Called by the UI when the user hits the Approve button (labeled "Open" or "Save", by default).
     * This can also be called by the programmer.
     * This method causes an action event to fire with the command string equal to {@link #APPROVE_SELECTION}.
     * <p>
     * This provides basic error handling through dialogs
     * and shows confirmation dialogs when trying to overwrite existing files.
     */
    @Override
    public void approveSelection() {
        File file = getSelectedFileWithExtension();
        if (file == null) {
            JOptionPane.showMessageDialog(this, "This file type is not supported", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (file.exists() && getDialogType() == SAVE_DIALOG) {
            int result = JOptionPane.showConfirmDialog(this, "File already exists, do you want to overwrite it?", "Warning", JOptionPane.YES_NO_OPTION);
            if (result != JOptionPane.YES_OPTION) {
                return;
            }
        }
        super.approveSelection();
    }

    @Override
    public File getSelectedFileWithExtension() {
        File file = getSelectedFile();
        String fileDescription = getFileFilter().getDescription();
        switch (fileDescription) {
            case "TEXT file":
                if (!file.getName().endsWith(".txt")) {
                    file = new File(file + ".txt");
                }
                break;
            case "CSV file":
                if (!file.getName().endsWith(".csv")) {
                    file = new File(file + ".csv");
                }
                break;
            case "ODS file":
                if (!file.getName().endsWith(".ods")) {
                    file = new File(file + ".ods");
                }
                break;
            default:
                return null;
        }
        return file;
    }
}