package main;

import model.Model;
import view.View;
import controller.Controller;

/**
 * Main class
 *
 * @author WJW02
 */
public class Main {
    /**
     * Initializes the app's Model-View-Controller environment
     * and makes the view visible to the user.
     *
     * @param args The command line arguments
     */
    public static void main(String[] args) {
        Model model = new Model();
        View view = new View(model.getTableModel());
        Controller controller = new Controller(model, view);
        controller.displayView();
    }
}