import model.Model;
import view.View;
import controller.Controller;
/**
 * Documentation
 */
public class Main {
    public static void main(String[] args) {
        Model model = new Model();
        View view = new View(model.getTableModel());
        Controller controller = new Controller(model, view);
        controller.displayView();
    }

}