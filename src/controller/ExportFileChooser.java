package controller;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;

public class ExportFileChooser extends JFileChooser implements CustomFileChooser {
    public ExportFileChooser() {
        removeChoosableFileFilter(getFileFilter());  //remove the default file filter
        addChoosableFileFilter(new FileNameExtensionFilter("CSV file", "csv"));
        addChoosableFileFilter(new FileNameExtensionFilter("TEXT file", "txt"));
        addChoosableFileFilter(new FileNameExtensionFilter("ODS file", "ods"));
    }

    @Override
    public void approveSelection() {
        File file = getSelectedFileWithExtension();
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