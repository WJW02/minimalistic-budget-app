package controller;

import model.Model;

import java.io.File;
import java.io.IOException;

interface CustomReader {
    boolean read(File file, Model model) throws IOException;
}