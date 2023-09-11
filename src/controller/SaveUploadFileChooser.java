package controller;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;

public class SaveUploadFileChooser extends JFileChooser implements CustomFileChooser {
    private boolean isSaveUpToDate;
    public SaveUploadFileChooser(boolean isSaveUpToDate) {
        removeChoosableFileFilter(getFileFilter());  //remove the default file filter
        addChoosableFileFilter(new FileNameExtensionFilter("TEXT file", "txt"));
        this.isSaveUpToDate = isSaveUpToDate;
    }

    @Override
    public void approveSelection() {
        File file = getSelectedFileWithExtension();
        if (file == null) {
            JOptionPane.showMessageDialog(this, "This file type is not supported", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (getDialogType() == SAVE_DIALOG && file.exists()) {
            int result = JOptionPane.showConfirmDialog(this, "File already exists, do you want to overwrite it?", "Warning", JOptionPane.YES_NO_OPTION);
            if (result != JOptionPane.YES_OPTION) {
                return;
            }
        } else if (getDialogType() == OPEN_DIALOG && !file.exists()) {
            JOptionPane.showMessageDialog(this, "File doesn't exist or can't be opened", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        } else if (getDialogType() == OPEN_DIALOG && !isSaveUpToDate()) {
            int result = JOptionPane.showConfirmDialog(this, "You haven't saved your changes. Are you sure you want to upload another save?", "Warning", JOptionPane.YES_NO_OPTION);
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

    public boolean isSaveUpToDate() {
        return isSaveUpToDate;
    }

    public void setSaveUpToDate(boolean saveUpToDate) {
        isSaveUpToDate = saveUpToDate;
    }
}