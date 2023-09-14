package controller;

import java.io.File;

/**
 * The interface for {@link javax.swing.JFileChooser}s providing a method forcing the chosen extension on the selected file from a JFileChooser.
 * <p>
 * The method that classes that implements this interface must implement is {@link #getSelectedFileWithExtension()}.
 *
 * @author WJW02
 */
interface CustomFileChooser {
    /**
     * Forces the chosen extension on the selected file from the JFileChooser.
     *
     * @return the file selected from the JFileChooser with the chosen extension
     */
    File getSelectedFileWithExtension();
}