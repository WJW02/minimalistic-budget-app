import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

/**
 * Documentation
 */
public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Budget app");
        JPanel gui = new JPanel(new BorderLayout(5, 5));

        // header
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.X_AXIS));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        JButton saveFileButton = new JButton();
        saveFileButton.setBorder(BorderFactory.createEmptyBorder());
        try {
            Image img = new ImageIcon("img/save.png").getImage();
            saveFileButton.setIcon(new ImageIcon(img));
        } catch (Exception ex) {
            System.out.println(ex);
        }
        //saveFileButton.setContentAreaFilled(false);
        JButton loadFileButton = new JButton("Load");
        loadFileButton.setBorder(BorderFactory.createEmptyBorder());
        JButton exportFileButton = new JButton("Export");
        exportFileButton.setBorder(BorderFactory.createEmptyBorder());
        JButton printFileButton = new JButton("Print");
        printFileButton.setBorder(BorderFactory.createEmptyBorder());
        headerPanel.add(saveFileButton);
        headerPanel.add(Box.createRigidArea(new Dimension(10, 0)));
        headerPanel.add(loadFileButton);
        headerPanel.add(Box.createRigidArea(new Dimension(10, 0)));
        headerPanel.add(exportFileButton);
        headerPanel.add(Box.createRigidArea(new Dimension(10, 0)));
        headerPanel.add(printFileButton);
        headerPanel.add(Box.createRigidArea(new Dimension(10, 0)));
        headerPanel.setBackground(Color.red);

        // sidebar
        JPanel sidebarPanel = new JPanel();
        sidebarPanel.setLayout(new BoxLayout(sidebarPanel, BoxLayout.Y_AXIS));
        sidebarPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // sidebar operational panel
        JPanel operationalPanel = new JPanel();
        JPanel operationalPanel1 = new JPanel();
        JPanel operationalPanel2 = new JPanel();
        JPanel operationalPanel3 = new JPanel();
        JPanel operationalPanel4 = new JPanel();
        operationalPanel.setLayout(new BoxLayout(operationalPanel, BoxLayout.Y_AXIS));
        operationalPanel1.setLayout(new BoxLayout(operationalPanel1, BoxLayout.X_AXIS));
        operationalPanel2.setLayout(new BoxLayout(operationalPanel2, BoxLayout.Y_AXIS));
        operationalPanel3.setLayout(new BoxLayout(operationalPanel3, BoxLayout.Y_AXIS));
        operationalPanel4.setLayout(new BoxLayout(operationalPanel4, BoxLayout.X_AXIS));

        //test
        //operationalPanel3.add(Box.createHorizontalGlue());
        //operationalPanel3.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel dateLabel = new JLabel("Date");
        JLabel descriptionLabel = new JLabel("Description");
        JLabel amountLabel = new JLabel("Amount");
        JTextField dateTextField = new JTextField("", 16);
        JTextField descriptionTextField = new JTextField("", 16);
        JTextField amountTextField = new JTextField("", 16);
        dateTextField.setMaximumSize(new Dimension(Integer.MAX_VALUE, dateTextField.getPreferredSize().height));
        descriptionTextField.setMaximumSize(new Dimension(Integer.MAX_VALUE, descriptionTextField.getPreferredSize().height));
        amountTextField.setMaximumSize(new Dimension(Integer.MAX_VALUE, amountTextField.getPreferredSize().height));
        JButton addButton = new JButton("ADD");
        JButton deleteButton = new JButton("DELETE");
        JButton updateButton = new JButton("UPDATE");

        // test
        //JLabel testLabel = new JLabel("Vedimao proidofadisaofup");
        //operationalPanel3.add(testLabel);

        operationalPanel2.add(dateLabel);
        operationalPanel2.add(Box.createRigidArea(new Dimension(0, 10)));
        operationalPanel2.add(descriptionLabel);
        operationalPanel2.add(Box.createRigidArea(new Dimension(0, 10)));
        operationalPanel2.add(amountLabel);
        operationalPanel3.add(dateTextField);
        operationalPanel3.add(Box.createRigidArea(new Dimension(0, 5)));
        operationalPanel3.add(descriptionTextField);
        operationalPanel3.add(Box.createRigidArea(new Dimension(0, 5)));
        operationalPanel3.add(amountTextField);
        operationalPanel4.add(addButton);
        operationalPanel4.add(Box.createRigidArea(new Dimension(5, 0)));
        operationalPanel4.add(deleteButton);
        operationalPanel4.add(Box.createRigidArea(new Dimension(5, 0)));
        operationalPanel4.add(updateButton);

        // test
        operationalPanel.setBackground(Color.green);
        operationalPanel1.setBackground(Color.green);
        operationalPanel2.setBackground(Color.green);
        operationalPanel3.setBackground(Color.green);
        operationalPanel4.setBackground(Color.green);

        operationalPanel1.add(operationalPanel2);
        operationalPanel1.add(Box.createRigidArea(new Dimension(10, 0)));
        operationalPanel1.add(operationalPanel3);
        operationalPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black),"Table operations"), BorderFactory.createEmptyBorder(10, 10, 10, 10)));
        operationalPanel.add(operationalPanel1);
        operationalPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        operationalPanel.add(operationalPanel4);

        // sidebar calendar panel
        JPanel calendarPanel = new JPanel();
        calendarPanel.setLayout(new BoxLayout(calendarPanel, BoxLayout.Y_AXIS));

        JTextField calendar = new JTextField("", 24);
        calendar.setMaximumSize(new Dimension(Integer.MAX_VALUE, calendar.getPreferredSize().height));

        calendarPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black),"Calendar"), BorderFactory.createEmptyBorder(10, 10, 10, 10)));
        calendarPanel.add(calendar);

        // sidebar search panel
        JPanel searchPanel = new JPanel();
        JPanel searchPanel1 = new JPanel();
        searchPanel.setLayout(new BoxLayout(searchPanel, BoxLayout.Y_AXIS));
        searchPanel1.setLayout(new BoxLayout(searchPanel1, BoxLayout.X_AXIS));

        JTextField searchBar = new JTextField("", 24);
        searchBar.setMaximumSize(new Dimension(Integer.MAX_VALUE, searchBar.getPreferredSize().height));
        JButton searchButton = new JButton("SEARCH");
        JButton nextButton = new JButton("NEXT");

        searchPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black),"Search"), BorderFactory.createEmptyBorder(10, 10, 10, 10)));
        searchPanel.add(searchBar);
        searchPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        searchPanel1.add(searchButton);
        searchPanel1.add(Box.createRigidArea(new Dimension(5, 0)));
        searchPanel1.add(nextButton);
        searchPanel.add(searchPanel1);

        // sidebar adding elements
        sidebarPanel.add(operationalPanel);
        sidebarPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        sidebarPanel.add(calendarPanel);
        sidebarPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        sidebarPanel.add(searchPanel);
        sidebarPanel.setBackground(Color.yellow);

        // body
        JPanel mainPanel = new JPanel(new BorderLayout(5, 5));
        JPanel mainHeaderPanel = new JPanel();
        mainHeaderPanel.setLayout(new BoxLayout(mainHeaderPanel, BoxLayout.X_AXIS));
        mainHeaderPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        JPanel mainBodyPanel = new JPanel();
        JPanel mainFooterPanel = new JPanel();
        mainFooterPanel.setLayout(new BoxLayout(mainFooterPanel, BoxLayout.X_AXIS));
        mainFooterPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        JLabel timeFrameLabel = new JLabel("TIME-FRAME");
        timeFrameLabel.setFont(new Font("Arial", Font.PLAIN, 52));
        mainHeaderPanel.add(timeFrameLabel);
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.add(mainBodyPanel);
        JLabel textAmountLabel = new JLabel("TOTAL AMOUNT: ");
        textAmountLabel.setFont(new Font("Arial", Font.PLAIN, 36));
        JLabel valueAmountLabel = new JLabel();
        valueAmountLabel.setFont(new Font("Arial", Font.PLAIN, 36));
        mainFooterPanel.add(textAmountLabel);
        mainFooterPanel.add(valueAmountLabel);
        mainBodyPanel.setBackground(Color.green);
        mainPanel.add(mainHeaderPanel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(mainFooterPanel, BorderLayout.SOUTH);

        // add components to gui
        gui.add(headerPanel, BorderLayout.NORTH);
        gui.add(sidebarPanel, BorderLayout.WEST);
        gui.add(mainPanel, BorderLayout.CENTER);

        frame.setBounds(100, 100, 1024, 768);
        frame.add(gui);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

}