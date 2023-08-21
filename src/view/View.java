package view;

import javax.swing.*;
import java.awt.*;

public class View {
    private JFrame frame;
    private JPanel gui;

    // header components
    private JPanel headerPanel;
    private JButton saveFileButton;
    private JButton loadFileButton;
    private JButton exportFileButton;
    private JButton printFileButton;

    // sidebar components
    private JPanel sidebarPanel;
    // operational panel components
    private JPanel operationalPanel;
    private JPanel operationalPanel1;
    private JPanel operationalPanel2;
    private JPanel operationalPanel3;
    private JPanel operationalPanel4;
    private JLabel dateLabel;
    private JLabel descriptionLabel;
    private JLabel amountLabel;
    private JTextField dateTextField;
    private JTextField descriptionTextField;
    private JTextField amountTextField;
    private JButton addButton;
    private JButton deleteButton;
    private JButton updateButton;
    // calendar panel components
    private JPanel calendarPanel;
    private JTextField calendar;
    // search panel components
    private JPanel searchPanel;
    private JPanel searchPanel1;
    private JTextField searchBar;
    private JButton searchButton;
    private JButton nextButton;

    // body components
    private JPanel mainPanel;
    private JPanel mainHeaderPanel;
    private JPanel mainBodyPanel;
    private JPanel mainFooterPanel;
    private JLabel timeFrameLabel;
    private JScrollPane scrollPane;
    private JLabel textAmountLabel;
    private JLabel valueAmountLabel;


    public View() {
        frame = new JFrame("Budget app");
        gui = new JPanel(new BorderLayout(5, 5));

        createHeader();
        createSidebar();
        createBody();

        gui.add(headerPanel, BorderLayout.NORTH);
        gui.add(sidebarPanel, BorderLayout.WEST);
        gui.add(mainPanel, BorderLayout.CENTER);

        frame.setBounds(100, 100, 1024, 768);
        frame.add(gui);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    private void createHeader() {
        headerPanel = new JPanel();
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.X_AXIS));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        saveFileButton = new JButton();
        saveFileButton.setBorder(BorderFactory.createEmptyBorder());
        try {
            Image img = new ImageIcon("img/save.png").getImage();
            saveFileButton.setIcon(new ImageIcon(img));
        } catch (Exception ex) {
            System.out.println(ex);
        }
        //saveFileButton.setContentAreaFilled(false);
        loadFileButton = new JButton("Load");
        loadFileButton.setBorder(BorderFactory.createEmptyBorder());
        exportFileButton = new JButton("Export");
        exportFileButton.setBorder(BorderFactory.createEmptyBorder());
        printFileButton = new JButton("Print");
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
    }
    private void createSidebar() {
        sidebarPanel = new JPanel();
        sidebarPanel.setLayout(new BoxLayout(sidebarPanel, BoxLayout.Y_AXIS));
        sidebarPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // sidebar operational panel
        operationalPanel = new JPanel();
        operationalPanel1 = new JPanel();
        operationalPanel2 = new JPanel();
        operationalPanel3 = new JPanel();
        operationalPanel4 = new JPanel();
        operationalPanel.setLayout(new BoxLayout(operationalPanel, BoxLayout.Y_AXIS));
        operationalPanel1.setLayout(new BoxLayout(operationalPanel1, BoxLayout.X_AXIS));
        operationalPanel2.setLayout(new BoxLayout(operationalPanel2, BoxLayout.Y_AXIS));
        operationalPanel3.setLayout(new BoxLayout(operationalPanel3, BoxLayout.Y_AXIS));
        operationalPanel4.setLayout(new BoxLayout(operationalPanel4, BoxLayout.X_AXIS));

        //test
        //operationalPanel3.add(Box.createHorizontalGlue());
        //operationalPanel3.setAlignmentX(Component.CENTER_ALIGNMENT);

        dateLabel = new JLabel("Date");
        descriptionLabel = new JLabel("Description");
        amountLabel = new JLabel("Amount");
        dateTextField = new JTextField("", 16);
        descriptionTextField = new JTextField("", 16);
        amountTextField = new JTextField("", 16);
        dateTextField.setMaximumSize(new Dimension(Integer.MAX_VALUE, dateTextField.getPreferredSize().height));
        descriptionTextField.setMaximumSize(new Dimension(Integer.MAX_VALUE, descriptionTextField.getPreferredSize().height));
        amountTextField.setMaximumSize(new Dimension(Integer.MAX_VALUE, amountTextField.getPreferredSize().height));
        addButton = new JButton("ADD");
        deleteButton = new JButton("DELETE");
        updateButton = new JButton("UPDATE");

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
        calendarPanel = new JPanel();
        calendarPanel.setLayout(new BoxLayout(calendarPanel, BoxLayout.Y_AXIS));

        calendar = new JTextField("", 24);
        calendar.setMaximumSize(new Dimension(Integer.MAX_VALUE, calendar.getPreferredSize().height));

        calendarPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black),"Calendar"), BorderFactory.createEmptyBorder(10, 10, 10, 10)));
        calendarPanel.add(calendar);

        // sidebar search panel
        searchPanel = new JPanel();
        searchPanel1 = new JPanel();
        searchPanel.setLayout(new BoxLayout(searchPanel, BoxLayout.Y_AXIS));
        searchPanel1.setLayout(new BoxLayout(searchPanel1, BoxLayout.X_AXIS));

        searchBar = new JTextField("", 24);
        searchBar.setMaximumSize(new Dimension(Integer.MAX_VALUE, searchBar.getPreferredSize().height));
        searchButton = new JButton("SEARCH");
        nextButton = new JButton("NEXT");

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
    }
    private void createBody() {
        mainPanel = new JPanel(new BorderLayout(5, 5));
        mainHeaderPanel = new JPanel();
        mainHeaderPanel.setLayout(new BoxLayout(mainHeaderPanel, BoxLayout.X_AXIS));
        mainHeaderPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        mainBodyPanel = new JPanel();
        mainFooterPanel = new JPanel();
        mainFooterPanel.setLayout(new BoxLayout(mainFooterPanel, BoxLayout.X_AXIS));
        mainFooterPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        timeFrameLabel = new JLabel("TIME-FRAME");
        timeFrameLabel.setFont(new Font("Arial", Font.PLAIN, 52));
        mainHeaderPanel.add(timeFrameLabel);
        scrollPane = new JScrollPane();
        scrollPane.add(mainBodyPanel);
        textAmountLabel = new JLabel("TOTAL AMOUNT: ");
        textAmountLabel.setFont(new Font("Arial", Font.PLAIN, 36));
        valueAmountLabel = new JLabel();
        valueAmountLabel.setFont(new Font("Arial", Font.PLAIN, 36));
        mainFooterPanel.add(textAmountLabel);
        mainFooterPanel.add(valueAmountLabel);
        mainBodyPanel.setBackground(Color.green);
        mainPanel.add(mainHeaderPanel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(mainFooterPanel, BorderLayout.SOUTH);
    }
}