import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Vector;

import model.BudgetItem;
import model.Model;
import view.View;
import controller.Controller;
/**
 * Documentation
 */
public class Main {
    public static void main(String[] args) {
        Vector<BudgetItem> budgetItems = new Vector<>();
        budgetItems.add(new BudgetItem(LocalDate.now(), "Ciaone", new BigDecimal(123)));
        budgetItems.add(new BudgetItem(LocalDate.now(), "Arriv", new BigDecimal(456)));

        Model model = new Model(budgetItems);
        View view = new View(model.getTableModel());
        Controller controller = new Controller(model, view);
        controller.displayView();
    }

}