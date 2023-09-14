package controller;

import model.Model;

import java.io.File;
import java.io.IOException;

/**
 * The interface for reading files for {@link Model}s.
 * <p>
 * The method that classes that implements this interface must implement is {@link #read(File, Model)}.
 *
 * @author WJW02
 */
interface CustomReader {
    /**
     * Reads a file and dumps its content in a {@link Model}.
     *
     * @param file the file it reads from
     * @param model the model in which it dumps the content
     * @return <code>true</code> - if the dump was successful, that means that the file contains
     * compatible data for the Model<br>
     * <code>false</code> - otherwise
     * @throws IOException if the file exists but is a directory rather than a regular file,
     * does not exist but cannot be created, or cannot be opened for any other reason
     */
    boolean read(File file, Model model) throws IOException;
}