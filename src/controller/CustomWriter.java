package controller;

import model.Model;
import view.View;

import java.io.*;

interface CustomWriter {
    void write(Model model, View view, File file);

}