package controller;

import model.Model;

import java.io.*;

/**
 * The interface for writing character files from a {@link Model}.
 * <p>
 * The method that classes that implements this interface must implement is {@link #write(Model, File)}.
 *
 * @author WJW02
 */
interface CustomWriter {
    /**
     * Writes a character file from a {@link Model}.
     *
     * @param model the model from which it gets the data
     * @param file the file it writes on
     * @throws IOException
     */
    void write(Model model, File file) throws IOException;

}