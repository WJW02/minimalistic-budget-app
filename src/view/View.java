package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class View {
    private JFrame frame;
    private JPanel gui;

    // header components
    private JPanel headerPanel;
    private CustomButton saveFileButton;
    private CustomButton uploadFileButton;
    private CustomButton exportFileButton;
    private CustomButton printFileButton;

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
    private JPanel calendarPanel1;
    private JPanel calendarPanel2;
    private JPanel calendarPanel3;
    private JLabel startDateLabel;
    private JLabel endDateLabel;
    private JTextField startDateTextField;
    private JTextField endDateTextField;
    private JButton applyDateButton;
    // search panel components
    private JPanel searchPanel;
    private JPanel searchPanel1;
    private JTextField searchBar;
    private JButton searchButton;
    private JButton nextButton;

    // body components
    private JPanel mainPanel;
    private JPanel mainHeaderPanel;
    private JPanel mainFooterPanel;
    private JLabel timeFrameLabel;
    private TableRowSorter<DefaultTableModel> sorter;
    private JScrollPane scrollPane;
    private JTable table;
    private JLabel textAmountLabel;
    private JLabel valueAmountLabel;


    public View(DefaultTableModel tableModel) {
        frame = new JFrame("Budget app");
        gui = new JPanel(new BorderLayout());

        createHeader();
        createSidebar();
        createBody(tableModel);

        gui.add(headerPanel, BorderLayout.NORTH);
        gui.add(sidebarPanel, BorderLayout.WEST);
        gui.add(mainPanel, BorderLayout.CENTER);

        frame.setBounds(100, 100, 1024, 768);
        frame.add(gui);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    private void createHeader() {
        headerPanel = new JPanel();
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.X_AXIS));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        Color normalColor = new Color(23, 37, 42);
        Color hoverButtonColor = new Color(43,64,71);
        Color pressedButtonColor = new Color(83, 119, 132);
        saveFileButton = new CustomButton(normalColor, hoverButtonColor, pressedButtonColor);
        uploadFileButton = new CustomButton(normalColor, hoverButtonColor, pressedButtonColor);
        exportFileButton = new CustomButton(normalColor, hoverButtonColor, pressedButtonColor);
        printFileButton = new CustomButton(normalColor, hoverButtonColor, pressedButtonColor);

        saveFileButton.setIcon(new ImageIcon(new ImageIcon("img/icon_save.png").getImage()));
        uploadFileButton.setIcon(new ImageIcon(new ImageIcon("img/icon_upload.png").getImage()));
        exportFileButton.setIcon(new ImageIcon(new ImageIcon("img/icon_export.png").getImage()));
        printFileButton.setIcon(new ImageIcon(new ImageIcon("img/icon_print.png").getImage()));

        saveFileButton.setBorder(null);
        uploadFileButton.setBorder(null);
        exportFileButton.setBorder(null);
        printFileButton.setBorder(null);


        /*
        Dimension buttonSize = new Dimension(79, 64);
        //saveFileButton.setPreferredSize(buttonSize);    // Allocates the space
        //saveFileButton.setMaximumSize(buttonSize);      // Uses the space
        uploadFileButton.setPreferredSize(buttonSize);
        uploadFileButton.setMaximumSize(buttonSize);
        exportFileButton.setPreferredSize(buttonSize);
        exportFileButton.setMaximumSize(buttonSize);
        printFileButton.setPreferredSize(buttonSize);
        printFileButton.setMaximumSize(buttonSize);
        */

        headerPanel.add(saveFileButton);
        headerPanel.add(Box.createRigidArea(new Dimension(16, 0)));
        headerPanel.add(uploadFileButton);
        headerPanel.add(Box.createRigidArea(new Dimension(16, 0)));
        headerPanel.add(exportFileButton);
        headerPanel.add(Box.createRigidArea(new Dimension(16, 0)));
        headerPanel.add(printFileButton);
        headerPanel.add(Box.createRigidArea(new Dimension(16, 0)));
        headerPanel.setBackground(normalColor);
    }
    private void createSidebar() {
        sidebarPanel = new JPanel();
        sidebarPanel.setLayout(new BoxLayout(sidebarPanel, BoxLayout.Y_AXIS));
        sidebarPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        createOperationalPanel();
        createCalendarPanel();
        createSearchPanel();

        sidebarPanel.add(operationalPanel);
        sidebarPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        sidebarPanel.add(calendarPanel);
        sidebarPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        sidebarPanel.add(searchPanel);
        sidebarPanel.setBackground(new Color(43, 122, 120));
    }

    private void createOperationalPanel() {
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

        dateLabel.setForeground(Color.WHITE);
        descriptionLabel.setForeground(Color.WHITE);
        amountLabel.setForeground(Color.WHITE);

        dateTextField = new JTextField("", 16);
        descriptionTextField = new JTextField("", 16);
        amountTextField = new JTextField("", 16);
        dateTextField.setMaximumSize(new Dimension(Integer.MAX_VALUE, dateTextField.getPreferredSize().height));
        descriptionTextField.setMaximumSize(new Dimension(Integer.MAX_VALUE, descriptionTextField.getPreferredSize().height));
        amountTextField.setMaximumSize(new Dimension(Integer.MAX_VALUE, amountTextField.getPreferredSize().height));
        addButton = new JButton("Add");
        deleteButton = new JButton("Delete");
        updateButton = new JButton("Update");

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
        operationalPanel.setOpaque(false);
        operationalPanel1.setOpaque(false);
        operationalPanel2.setOpaque(false);
        operationalPanel3.setOpaque(false);
        operationalPanel4.setOpaque(false);

        operationalPanel1.add(operationalPanel2);
        operationalPanel1.add(Box.createRigidArea(new Dimension(10, 0)));
        operationalPanel1.add(operationalPanel3);

        operationalPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.WHITE),"Table operations",0,0, new Font("Arial", Font.BOLD, 14), Color.WHITE), BorderFactory.createEmptyBorder(10, 10, 10, 10)));
        operationalPanel.add(operationalPanel1);
        operationalPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        operationalPanel.add(operationalPanel4);
    }

    private void createCalendarPanel() {
        calendarPanel = new JPanel();
        calendarPanel1 = new JPanel();
        calendarPanel2 = new JPanel();
        calendarPanel3 = new JPanel();
        calendarPanel.setLayout(new BoxLayout(calendarPanel, BoxLayout.Y_AXIS));
        calendarPanel1.setLayout(new BoxLayout(calendarPanel1, BoxLayout.X_AXIS));
        calendarPanel2.setLayout(new BoxLayout(calendarPanel2, BoxLayout.Y_AXIS));
        calendarPanel3.setLayout(new BoxLayout(calendarPanel3, BoxLayout.Y_AXIS));

        startDateLabel = new JLabel("Start date");
        endDateLabel = new JLabel("End date");

        startDateLabel.setForeground(Color.WHITE);
        endDateLabel.setForeground(Color.WHITE);

        // Current date
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();

        startDateTextField = new JTextField(formatter.format(date), 16);
        startDateTextField.setMaximumSize(new Dimension(Integer.MAX_VALUE, startDateTextField.getPreferredSize().height));
        endDateTextField = new JTextField(formatter.format(date), 16);
        endDateTextField.setMaximumSize(new Dimension(Integer.MAX_VALUE, endDateTextField.getPreferredSize().height));

        calendarPanel.setOpaque(false);
        calendarPanel1.setOpaque(false);
        calendarPanel2.setOpaque(false);
        calendarPanel3.setOpaque(false);

        calendarPanel2.add(startDateLabel);
        calendarPanel2.add(Box.createRigidArea(new Dimension(0, 10)));
        calendarPanel2.add(endDateLabel);
        calendarPanel3.add(startDateTextField);
        calendarPanel3.add(Box.createRigidArea(new Dimension(0, 5)));
        calendarPanel3.add(endDateTextField);

        applyDateButton = new JButton("Apply");

        calendarPanel1.add(calendarPanel2);
        calendarPanel1.add(Box.createRigidArea(new Dimension(10, 0)));
        calendarPanel1.add(calendarPanel3);
        calendarPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.WHITE),"Calendar",0,0, new Font("Arial", Font.BOLD, 14), Color.WHITE), BorderFactory.createEmptyBorder(10, 10, 10, 10)));
        calendarPanel.add(calendarPanel1);
        calendarPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        calendarPanel.add(applyDateButton);
    }

    private void createSearchPanel() {
        searchPanel = new JPanel();
        searchPanel1 = new JPanel();
        searchPanel.setLayout(new BoxLayout(searchPanel, BoxLayout.Y_AXIS));
        searchPanel1.setLayout(new BoxLayout(searchPanel1, BoxLayout.X_AXIS));

        searchBar = new JTextField("", 24);
        searchBar.setMaximumSize(new Dimension(Integer.MAX_VALUE, searchBar.getPreferredSize().height));
        searchButton = new JButton("Search");
        nextButton = new JButton("Next");

        searchPanel.setOpaque(false);
        searchPanel1.setOpaque(false);

        searchPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.WHITE),"Search",0,0, new Font("Arial", Font.BOLD, 14), Color.WHITE), BorderFactory.createEmptyBorder(10, 10, 10, 10)));
        searchPanel.add(searchBar);
        searchPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        searchPanel1.add(searchButton);
        searchPanel1.add(Box.createRigidArea(new Dimension(5, 0)));
        searchPanel1.add(nextButton);
        searchPanel.add(searchPanel1);
    }

    private void createBody(DefaultTableModel tableModel) {
        mainPanel = new JPanel(new BorderLayout());
        mainHeaderPanel = new JPanel();
        //mainBodyPanel = new JPanel();
        mainFooterPanel = new JPanel();

        mainHeaderPanel.setLayout(new BoxLayout(mainHeaderPanel, BoxLayout.X_AXIS));
        mainFooterPanel.setLayout(new BoxLayout(mainFooterPanel, BoxLayout.X_AXIS));

        mainHeaderPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        mainFooterPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Header
        timeFrameLabel = new JLabel("TIME-FRAME");
        timeFrameLabel.setForeground(Color.WHITE);
        timeFrameLabel.setFont(new Font("Arial", Font.PLAIN, 32));
        mainHeaderPanel.add(timeFrameLabel);

        // Body
        table = new JTable(tableModel);
        sorter = new TableRowSorter<>(tableModel);
        table.setRowSorter(sorter);
        scrollPane = new JScrollPane(table);
        //mainBodyPanel.add(scrollPane);
        //mainBodyPanel.setBackground(new Color(222,242,241));

        // Footer
        textAmountLabel = new JLabel("TOTAL AMOUNT: ");
        valueAmountLabel = new JLabel();

        textAmountLabel.setFont(new Font("Arial", Font.PLAIN, 24));
        valueAmountLabel.setFont(new Font("Arial", Font.PLAIN, 24));        //mainBodyPanel = new JPanel();

        mainFooterPanel.add(textAmountLabel);
        mainFooterPanel.add(valueAmountLabel);

        mainHeaderPanel.setBackground(new Color(58, 175, 169));
        scrollPane.getViewport().setBackground(new Color(222,242,241));
        mainFooterPanel.setBackground(new Color(222,242,241));

        mainPanel.add(mainHeaderPanel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(mainFooterPanel, BorderLayout.SOUTH);
    }

    public void display() {
        frame.setVisible(true);
    }

    public JFrame getFrame() {
        return frame;
    }

    public void setFrame(JFrame frame) {
        this.frame = frame;
    }

    public CustomButton getSaveFileButton() {
        return saveFileButton;
    }

    public void setSaveFileButton(CustomButton saveFileButton) {
        this.saveFileButton = saveFileButton;
    }

    public CustomButton getUploadFileButton() {
        return uploadFileButton;
    }

    public void setUploadFileButton(CustomButton uploadFileButton) {
        this.uploadFileButton = uploadFileButton;
    }

    public CustomButton getExportFileButton() {
        return exportFileButton;
    }

    public void setExportFileButton(CustomButton exportFileButton) {
        this.exportFileButton = exportFileButton;
    }

    public CustomButton getPrintFileButton() {
        return printFileButton;
    }

    public void setPrintFileButton(CustomButton printFileButton) {
        this.printFileButton = printFileButton;
    }

    public JTextField getDateTextField() {
        return dateTextField;
    }

    public void setDateTextField(JTextField dateTextField) {
        this.dateTextField = dateTextField;
    }

    public JTextField getDescriptionTextField() {
        return descriptionTextField;
    }

    public void setDescriptionTextField(JTextField descriptionTextField) {
        this.descriptionTextField = descriptionTextField;
    }

    public JTextField getAmountTextField() {
        return amountTextField;
    }

    public void setAmountTextField(JTextField amountTextField) {
        this.amountTextField = amountTextField;
    }

    public JButton getAddButton() {
        return addButton;
    }

    public void setAddButton(JButton addButton) {
        this.addButton = addButton;
    }

    public JButton getDeleteButton() {
        return deleteButton;
    }

    public void setDeleteButton(JButton deleteButton) {
        this.deleteButton = deleteButton;
    }

    public JButton getUpdateButton() {
        return updateButton;
    }

    public void setUpdateButton(JButton updateButton) {
        this.updateButton = updateButton;
    }

    public JTextField getStartDateTextField() {
        return startDateTextField;
    }

    public void setStartDateTextField(JTextField startDateTextField) {
        this.startDateTextField = startDateTextField;
    }

    public JTextField getEndDateTextField() {
        return endDateTextField;
    }

    public void setEndDateTextField(JTextField endDateTextField) {
        this.endDateTextField = endDateTextField;
    }

    public JButton getApplyDateButton() {
        return applyDateButton;
    }

    public void setApplyDateButton(JButton applyDateButton) {
        this.applyDateButton = applyDateButton;
    }

    public JTextField getSearchBar() {
        return searchBar;
    }

    public void setSearchBar(JTextField searchBar) {
        this.searchBar = searchBar;
    }

    public JButton getSearchButton() {
        return searchButton;
    }

    public void setSearchButton(JButton searchButton) {
        this.searchButton = searchButton;
    }

    public JButton getNextButton() {
        return nextButton;
    }

    public void setNextButton(JButton nextButton) {
        this.nextButton = nextButton;
    }

    public JLabel getTimeFrameLabel() {
        return timeFrameLabel;
    }

    public void setTimeFrameLabel(JLabel timeFrameLabel) {
        this.timeFrameLabel = timeFrameLabel;
    }

    public TableRowSorter<DefaultTableModel> getSorter() {
        return sorter;
    }

    public void setSorter(TableRowSorter<DefaultTableModel> sorter) {
        this.sorter = sorter;
    }

    public JLabel getValueAmountLabel() {
        return valueAmountLabel;
    }

    public void setValueAmountLabel(JLabel valueAmountLabel) {
        this.valueAmountLabel = valueAmountLabel;
    }
}