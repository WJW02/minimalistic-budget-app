package controller;

import model.Model;

import java.io.*;

/**
 * The interface for writing files from a {@link Model}.
 * <p>
 * The method that classes that implements this interface must implement is {@link #write(Model, File)}.
 *
 * @author WJW02
 */
interface CustomWriter {
    /**
     * Writes a file from a {@link Model}.
     *
     * @param model the model from which it gets the data
     * @param file the file it writes on
     * @throws IOException if the file exists but is a directory rather than a regular file,
     * does not exist but cannot be created, or cannot be opened for any other reason
     */
    void write(Model model, File file) throws IOException;

}