package controller;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;

/**
 * A class providing a customized {@link JFileChooser} for save and upload.
 * <p>
 * This JFileChooser provides basic error handling through dialogs, shows confirmation dialogs when
 * trying to overwrite existing files and when trying to close without saving.
 *
 * @author WJW02
 */
public class SaveUploadFileChooser extends JFileChooser implements CustomFileChooser {
    /**
     * Checks whether file is up-to-date.
     */
    private boolean isSaveUpToDate;

    /**
     * Initializes this JFileChooser with txt as the only allowed extension.
     *
     * @param isSaveUpToDate checks whether file is up-to-date
     */
    public SaveUploadFileChooser(boolean isSaveUpToDate) {
        removeChoosableFileFilter(getFileFilter());     // Remove the default file filter
        addChoosableFileFilter(new FileNameExtensionFilter("TEXT file", "txt"));
        this.isSaveUpToDate = isSaveUpToDate;
    }

    /**
     * Called by the UI when the user hits the Approve button (labeled "Open" or "Save", by default).
     * This can also be called by the programmer.
     * This method causes an action event to fire with the command string equal to {@link #APPROVE_SELECTION}.
     * <p>
     * This provides basic error handling through dialogs, shows confirmation dialogs when
     * trying to overwrite existing files and when trying to close without saving.
     */
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
        if (fileDescription.equals("TEXT file")) {
            if (!file.getName().endsWith(".txt")) {
                file = new File(file + ".txt");
            }
        } else {
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