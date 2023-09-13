package view;

import model.Model;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;

/**
 * The class providing the GUI of the application.
 * <p>
 * The {@link #gui} is made of three main panels:
 * <ul>
 *     <li>{@link #headerPanel}</li>
 *     <li>{@link #sidebarPanel}</li>
 *     <li>{@link #bodyPanel}</li>
 * </ul>
 *
 * @author WJW02
 */
public class View {
    /**
     * The frame of the application.
     */
    private JFrame frame;
    /**
     * The main panel of the application, containing {@link #headerPanel}, {@link #sidebarPanel} and {@link #bodyPanel}.
     */
    private JPanel gui;

    // header components
    /**
     * The panel containing the header components.
     *
     * @see #createHeader()
     */
    private JPanel headerPanel;
    /**
     * The button saving {@link model.Model#getTableModel()} in a file.
     */
    private CustomButton saveFileButton;
    /**
     * The button uploading a {@link model.Model#getTableModel()} from a file.
     */
    private CustomButton uploadFileButton;
    /**
     * The button exporting {@link model.Model#getTableModel()} in a file.
     */
    private CustomButton exportFileButton;
    /**
     * The button printing {@link model.Model#getTableModel()}.
     */
    private CustomButton printFileButton;

    // sidebar components
    /**
     * The panel containing the sidebar components.
     *
     * @see #createSidebar()
     */
    private JPanel sidebarPanel;
    // operational panel components
    /**
     * The panel containing the operational components (a section of {@link #sidebarPanel}).
     *
     * @see #createOperationalPanel()
     */
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
    /**
     * The button adding items to {@link model.Model#getTableModel()}.
     */
    private JButton addButton;
    /**
     * The button deleting items from {@link model.Model#getTableModel()}.
     */
    private JButton deleteButton;
    /**
     * The button updating items from {@link model.Model#getTableModel()}.
     */
    private JButton updateButton;
    // calendar panel components
    /**
     * The panel containing the calendar components (a section of {@link #sidebarPanel}).
     *
     * @see #createCalendarPanel()
     */
    private JPanel calendarPanel;
    private JPanel calendarPanel1;
    private JPanel calendarPanel2;
    private JPanel calendarPanel3;
    private JPanel calendarPanel4;
    private JLabel startDateLabel;
    private JLabel endDateLabel;
    private JTextField startDateTextField;
    private JTextField endDateTextField;
    /**
     * The button applying the date filter on {@link model.Model#getTableModel()}.
     */
    private JButton applyDateButton;
    /**
     * The button clearing the date filter on {@link model.Model#getTableModel()}.
     */
    private JButton clearDateButton;
    // search panel components
    /**
     * The panel containing the search components (a section of {@link #sidebarPanel}).
     *
     * @see #createSearchPanel()
     */
    private JPanel searchPanel;
    private JPanel searchPanel1;
    private JTextField searchBar;
    /**
     * The button searching items in {@link model.Model#getTableModel()}.
     */
    private JButton searchButton;
    /**
     * The button clearing the selected item from {@link model.Model#getTableModel()}.
     */
    private JButton clearSearchButton;

    // body components
    /**
     * The panel containing the body components.
     *
     * @see #createBody(DefaultTableModel)
     */
    private JPanel bodyPanel;
    /**
     * The panel containing the body-header components (a section of {@link #bodyPanel}).
     *
     * @see #createBodyHeaderPanel()
     */
    private JPanel bodyHeaderPanel;
    /**
     * The panel containing the body-footer components (a section of {@link #bodyPanel}).
     *
     * @see #createBodyFooterPanel()
     */
    private JPanel bodyFooterPanel;
    private JLabel timeFrameLabel;
    private TableRowSorter<DefaultTableModel> tableSorter;
    /**
     * The scrollPane containing the table (a section of {@link #bodyPanel}).
     *
     * @see #createBodyScrollPane(DefaultTableModel)
     */
    private JScrollPane bodyScrollPane;
    private JTable table;
    private JLabel textAmountLabel;
    private JLabel valueAmountLabel;

    /**
     * Creates the GUI of the application.
     *
     * @param tableModel the model of table which contains the data to show
     */
    public View(DefaultTableModel tableModel) {
        // Initializes the frame and the main panel
        frame = new JFrame("Budget app");
        gui = new JPanel(new BorderLayout());

        // Creates the main sections
        createHeader();
        createSidebar();
        createBody(tableModel);

        // Adds them to the main panel
        gui.add(headerPanel, BorderLayout.NORTH);
        gui.add(sidebarPanel, BorderLayout.WEST);
        gui.add(bodyPanel, BorderLayout.CENTER);

        // Sets frame dimensions and adds the main panel to the frame
        frame.setBounds(100, 100, 1024, 768);
        frame.setMinimumSize(new Dimension(1024, 768));
        frame.add(gui);
    }

    /**
     * Creates {@link #headerPanel} with all its components.
     * <p>
     * The components:
     * <ul>
     *     <li>{@link #saveFileButton}</li>
     *     <li>{@link #uploadFileButton}</li>
     *     <li>{@link #exportFileButton}</li>
     *     <li>{@link #printFileButton}</li>
     * </ul>
     */
    private void createHeader() {
        // Initializes headerPanel
        headerPanel = new JPanel();
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.X_AXIS));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Creates the custom buttons
        Color normalColor = new Color(23, 37, 42);
        Color hoverButtonColor = new Color(43,64,71);
        Color pressedButtonColor = new Color(83, 119, 132);
        saveFileButton = new CustomButton(normalColor, hoverButtonColor, pressedButtonColor);
        uploadFileButton = new CustomButton(normalColor, hoverButtonColor, pressedButtonColor);
        exportFileButton = new CustomButton(normalColor, hoverButtonColor, pressedButtonColor);
        printFileButton = new CustomButton(normalColor, hoverButtonColor, pressedButtonColor);

        // Adds icons to the custom buttons
        saveFileButton.setIcon(new ImageIcon(new ImageIcon("img/icon_save.png").getImage()));
        uploadFileButton.setIcon(new ImageIcon(new ImageIcon("img/icon_upload.png").getImage()));
        exportFileButton.setIcon(new ImageIcon(new ImageIcon("img/icon_export.png").getImage()));
        printFileButton.setIcon(new ImageIcon(new ImageIcon("img/icon_print.png").getImage()));

        // Deletes their border
        saveFileButton.setBorder(null);
        uploadFileButton.setBorder(null);
        exportFileButton.setBorder(null);
        printFileButton.setBorder(null);

        // Adds tool tips when hovering over the buttons
        saveFileButton.setToolTipText("Save");
        uploadFileButton.setToolTipText("Upload");
        exportFileButton.setToolTipText("Export");
        printFileButton.setToolTipText("Print");

        // Adds the components to headerPanel
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

    /**
     * Creates {@link #sidebarPanel} with all its components.
     * <p>
     * The components:
     * <ul>
     *     <li>{@link #operationalPanel}</li>
     *     <li>{@link #calendarPanel}</li>
     *     <li>{@link #searchPanel}</li>
     * </ul>
     *
     * @see #createOperationalPanel()
     * @see #createCalendarPanel()
     * @see #createSearchPanel()
     */
    private void createSidebar() {
        // Initializes sidebarPanel
        sidebarPanel = new JPanel();
        sidebarPanel.setLayout(new BoxLayout(sidebarPanel, BoxLayout.Y_AXIS));
        sidebarPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        sidebarPanel.setBackground(new Color(43, 122, 120));

        // Creates the different sections of sidebarPanel
        createOperationalPanel();
        createCalendarPanel();
        createSearchPanel();

        // Adds the different sections to sidebarPanel
        sidebarPanel.add(operationalPanel);
        sidebarPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        sidebarPanel.add(calendarPanel);
        sidebarPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        sidebarPanel.add(searchPanel);
    }

    /**
     * Creates {@link #operationalPanel} with all its components (a section of {@link #sidebarPanel}).
     * <p>
     * The components:
     * <ul>
     *     <li>{@link #operationalPanel1}</li>
     *     <li>{@link #operationalPanel2}</li>
     *     <li>{@link #operationalPanel3}</li>
     *     <li>{@link #operationalPanel4}</li>
     *     <li>{@link #dateLabel}</li>
     *     <li>{@link #descriptionLabel}</li>
     *     <li>{@link #amountLabel}</li>
     *     <li>{@link #dateTextField}</li>
     *     <li>{@link #descriptionTextField}</li>
     *     <li>{@link #amountTextField}</li>
     *     <li>{@link #addButton}</li>
     *     <li>{@link #deleteButton}</li>
     *     <li>{@link #updateButton}</li>
     * </ul>
     *
     * @see #createSidebar()
     */
    private void createOperationalPanel() {
        // Initializes the panels
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
        operationalPanel.setOpaque(false);
        operationalPanel1.setOpaque(false);
        operationalPanel2.setOpaque(false);
        operationalPanel3.setOpaque(false);
        operationalPanel4.setOpaque(false);
        operationalPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.WHITE),"Table operations",0,0, new Font("Arial", Font.BOLD, 14), Color.WHITE), BorderFactory.createEmptyBorder(10, 10, 10, 10)));

        // Initializes the labels
        dateLabel = new JLabel("Date");
        descriptionLabel = new JLabel("Description");
        amountLabel = new JLabel("Amount");
        dateLabel.setForeground(Color.WHITE);
        descriptionLabel.setForeground(Color.WHITE);
        amountLabel.setForeground(Color.WHITE);

        // Initializes the text fields
        dateTextField = new JTextField("", 16);
        descriptionTextField = new JTextField("", 16);
        amountTextField = new JTextField("", 16);
        dateTextField.setMaximumSize(new Dimension(Integer.MAX_VALUE, dateTextField.getPreferredSize().height));
        descriptionTextField.setMaximumSize(new Dimension(Integer.MAX_VALUE, descriptionTextField.getPreferredSize().height));
        amountTextField.setMaximumSize(new Dimension(Integer.MAX_VALUE, amountTextField.getPreferredSize().height));

        // Initializes the buttons
        addButton = new JButton("Add");
        deleteButton = new JButton("Delete");
        updateButton = new JButton("Update");

        // Adds the components to operationalPanel2
        operationalPanel2.add(dateLabel);
        operationalPanel2.add(Box.createRigidArea(new Dimension(0, 10)));
        operationalPanel2.add(descriptionLabel);
        operationalPanel2.add(Box.createRigidArea(new Dimension(0, 10)));
        operationalPanel2.add(amountLabel);

        // Adds the components to operationalPanel3
        operationalPanel3.add(dateTextField);
        operationalPanel3.add(Box.createRigidArea(new Dimension(0, 5)));
        operationalPanel3.add(descriptionTextField);
        operationalPanel3.add(Box.createRigidArea(new Dimension(0, 5)));
        operationalPanel3.add(amountTextField);

        // Adds the components to operationalPanel4
        operationalPanel4.add(addButton);
        operationalPanel4.add(Box.createRigidArea(new Dimension(5, 0)));
        operationalPanel4.add(deleteButton);
        operationalPanel4.add(Box.createRigidArea(new Dimension(5, 0)));
        operationalPanel4.add(updateButton);

        // Adds the components to operationalPanel1
        operationalPanel1.add(operationalPanel2);
        operationalPanel1.add(Box.createRigidArea(new Dimension(10, 0)));
        operationalPanel1.add(operationalPanel3);

        // Adds the components to operationalPanel
        operationalPanel.add(operationalPanel1);
        operationalPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        operationalPanel.add(operationalPanel4);
    }

    /**
     * Creates {@link #calendarPanel} with all its components (a section of {@link #sidebarPanel}).
     * <p>
     * The components:
     * <ul>
     *     <li>{@link #calendarPanel1}</li>
     *     <li>{@link #calendarPanel2}</li>
     *     <li>{@link #calendarPanel3}</li>
     *     <li>{@link #calendarPanel4}</li>
     *     <li>{@link #startDateLabel}</li>
     *     <li>{@link #endDateLabel}</li>
     *     <li>{@link #startDateTextField}</li>
     *     <li>{@link #endDateTextField}</li>
     *     <li>{@link #applyDateButton}</li>
     *     <li>{@link #clearDateButton}</li>
     * </ul>
     *
     * @see #createSidebar()
     */
    private void createCalendarPanel() {
        // Initializes the panels
        calendarPanel = new JPanel();
        calendarPanel1 = new JPanel();
        calendarPanel2 = new JPanel();
        calendarPanel3 = new JPanel();
        calendarPanel4 = new JPanel();
        calendarPanel.setLayout(new BoxLayout(calendarPanel, BoxLayout.Y_AXIS));
        calendarPanel1.setLayout(new BoxLayout(calendarPanel1, BoxLayout.X_AXIS));
        calendarPanel2.setLayout(new BoxLayout(calendarPanel2, BoxLayout.Y_AXIS));
        calendarPanel3.setLayout(new BoxLayout(calendarPanel3, BoxLayout.Y_AXIS));
        calendarPanel4.setLayout(new BoxLayout(calendarPanel4, BoxLayout.X_AXIS));
        calendarPanel.setOpaque(false);
        calendarPanel1.setOpaque(false);
        calendarPanel2.setOpaque(false);
        calendarPanel3.setOpaque(false);
        calendarPanel4.setOpaque(false);
        calendarPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.WHITE),"Calendar",0,0, new Font("Arial", Font.BOLD, 14), Color.WHITE), BorderFactory.createEmptyBorder(10, 10, 10, 10)));

        // Initializes the labels
        startDateLabel = new JLabel("Start date");
        endDateLabel = new JLabel("End date");
        startDateLabel.setForeground(Color.WHITE);
        endDateLabel.setForeground(Color.WHITE);

        // Initializes the text fields
        startDateTextField = new JTextField("", 16);
        startDateTextField.setMaximumSize(new Dimension(Integer.MAX_VALUE, startDateTextField.getPreferredSize().height));
        endDateTextField = new JTextField("", 16);
        endDateTextField.setMaximumSize(new Dimension(Integer.MAX_VALUE, endDateTextField.getPreferredSize().height));

        // Initializes the buttons
        applyDateButton = new JButton("Apply");
        clearDateButton = new JButton("Clear");

        // Adds the components to calendarPanel2
        calendarPanel2.add(startDateLabel);
        calendarPanel2.add(Box.createRigidArea(new Dimension(0, 10)));
        calendarPanel2.add(endDateLabel);

        // Adds the components to calendarPanel3
        calendarPanel3.add(startDateTextField);
        calendarPanel3.add(Box.createRigidArea(new Dimension(0, 5)));
        calendarPanel3.add(endDateTextField);

        // Adds the components to calendarPanel4
        calendarPanel4.add(applyDateButton);
        calendarPanel4.add(Box.createRigidArea(new Dimension(5, 0)));
        calendarPanel4.add(clearDateButton);

        // Adds the components to calendarPanel1
        calendarPanel1.add(calendarPanel2);
        calendarPanel1.add(Box.createRigidArea(new Dimension(10, 0)));
        calendarPanel1.add(calendarPanel3);

        // Adds the components to calendarPanel
        calendarPanel.add(calendarPanel1);
        calendarPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        calendarPanel.add(calendarPanel4);
    }

    /**
     * Creates {@link #searchPanel} with all its components (a section of {@link #sidebarPanel}).
     * <p>
     * The components:
     * <ul>
     *     <li>{@link #searchPanel1}</li>
     *     <li>{@link #searchBar}</li>
     *     <li>{@link #searchButton}</li>
     *     <li>{@link #clearSearchButton}</li>
     * </ul>
     *
     * @see #createSidebar()
     */
    private void createSearchPanel() {
        // Initializes the panels
        searchPanel = new JPanel();
        searchPanel1 = new JPanel();
        searchPanel.setLayout(new BoxLayout(searchPanel, BoxLayout.Y_AXIS));
        searchPanel1.setLayout(new BoxLayout(searchPanel1, BoxLayout.X_AXIS));
        searchPanel.setOpaque(false);
        searchPanel1.setOpaque(false);
        searchPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.WHITE),"Search",0,0, new Font("Arial", Font.BOLD, 14), Color.WHITE), BorderFactory.createEmptyBorder(10, 10, 10, 10)));

        // Initializes the searchBar
        searchBar = new JTextField("", 24);
        searchBar.setMaximumSize(new Dimension(Integer.MAX_VALUE, searchBar.getPreferredSize().height));

        // Initializes the buttons
        searchButton = new JButton("Search");
        clearSearchButton = new JButton("Clear");

        // Adds the components to searchPanel1
        searchPanel1.add(searchButton);
        searchPanel1.add(Box.createRigidArea(new Dimension(5, 0)));
        searchPanel1.add(clearSearchButton);

        // Adds the components to searchPanel
        searchPanel.add(searchBar);
        searchPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        searchPanel.add(searchPanel1);
    }

    /**
     * Creates {@link #bodyPanel} with all its components.
     * <p>
     * The components:
     * <ul>
     *     <li>{@link #bodyHeaderPanel}</li>
     *     <li>{@link #bodyScrollPane}</li>
     *     <li>{@link #bodyFooterPanel}</li>
     * </ul>
     *
     * @see #createBodyHeaderPanel()
     * @see #createBodyScrollPane(DefaultTableModel)
     * @see #createBodyFooterPanel()
     */
    private void createBody(DefaultTableModel tableModel) {
        // Initializes bodyPanel
        bodyPanel = new JPanel(new BorderLayout());

        // Creates the different sections
        createBodyHeaderPanel();
        createBodyScrollPane(tableModel);
        createBodyFooterPanel();

        // Adds the sections to bodyPanel
        bodyPanel.add(bodyHeaderPanel, BorderLayout.NORTH);
        bodyPanel.add(bodyScrollPane, BorderLayout.CENTER);
        bodyPanel.add(bodyFooterPanel, BorderLayout.SOUTH);
    }

    /**
     * Creates {@link #bodyHeaderPanel} with all its components (a section of {@link #bodyPanel}).
     * <p>
     * The components:
     * <ul>
     *     <li>{@link #timeFrameLabel}</li>
     * </ul>
     *
     * @see #createBody(DefaultTableModel)
     */
    private void createBodyHeaderPanel() {
        // Initializes bodyHeaderPanel
        bodyHeaderPanel = new JPanel();
        bodyHeaderPanel.setLayout(new BoxLayout(bodyHeaderPanel, BoxLayout.X_AXIS));
        bodyHeaderPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        bodyHeaderPanel.setBackground(new Color(58, 175, 169));

        // Initializes timeFrameLabel
        timeFrameLabel = new JLabel("TIME-FRAME");
        timeFrameLabel.setForeground(Color.WHITE);
        timeFrameLabel.setFont(new Font("Arial", Font.PLAIN, 32));

        // Adds timeFrameLabel to bodyHeaderPanel
        bodyHeaderPanel.add(timeFrameLabel);
    }

    /**
     * Creates {@link #bodyScrollPane} with all its components (a section of {@link #bodyPanel}).
     * <p>
     * The components:
     * <ul>
     *     <li>{@link #table}</li>
     *     <li>{@link #tableSorter}</li>
     * </ul>
     *
     * @see #createBody(DefaultTableModel)
     */
    private void createBodyScrollPane(DefaultTableModel tableModel) {
        // Initializes table
        table = new JTable(tableModel);
        tableSorter = new TableRowSorter<>(tableModel);
        table.setRowSorter(tableSorter);

        // Initializes bodyScrollPane
        bodyScrollPane = new JScrollPane(table);
        bodyScrollPane.getViewport().setBackground(new Color(222,242,241));
    }

    /**
     * Creates {@link #bodyFooterPanel} with all its components (a section of {@link #bodyPanel}).
     * <p>
     * The components:
     * <ul>
     *     <li>{@link #textAmountLabel}</li>
     *     <li>{@link #valueAmountLabel}</li>
     * </ul>
     *
     * @see #createBody(DefaultTableModel)
     */
    private void createBodyFooterPanel() {
        // Initializes bodyFooterPanel
        bodyFooterPanel = new JPanel();
        bodyFooterPanel.setLayout(new BoxLayout(bodyFooterPanel, BoxLayout.X_AXIS));
        bodyFooterPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        bodyFooterPanel.setBackground(new Color(222,242,241));

        // Initializes the labels
        textAmountLabel = new JLabel("TOTAL AMOUNT: ");
        valueAmountLabel = new JLabel();
        textAmountLabel.setFont(new Font("Arial", Font.PLAIN, 24));
        valueAmountLabel.setFont(new Font("Arial", Font.PLAIN, 24));

        // Adds the labels to bodyFooterPanel
        bodyFooterPanel.add(textAmountLabel);
        bodyFooterPanel.add(valueAmountLabel);
    }

    /**
     * Makes {@link #frame} visible.
     */
    public void display() {
        frame.setVisible(true);
    }

    public JFrame getFrame() {
        return frame;
    }

    public CustomButton getSaveFileButton() {
        return saveFileButton;
    }

    public CustomButton getUploadFileButton() {
        return uploadFileButton;
    }

    public CustomButton getExportFileButton() {
        return exportFileButton;
    }

    public CustomButton getPrintFileButton() {
        return printFileButton;
    }

    public JTextField getDateTextField() {
        return dateTextField;
    }

    public JTextField getDescriptionTextField() {
        return descriptionTextField;
    }

    public JTextField getAmountTextField() {
        return amountTextField;
    }

    public JButton getAddButton() {
        return addButton;
    }

    public JButton getDeleteButton() {
        return deleteButton;
    }

    public JButton getUpdateButton() {
        return updateButton;
    }

    public JTextField getStartDateTextField() {
        return startDateTextField;
    }

    public JTextField getEndDateTextField() {
        return endDateTextField;
    }

    public JButton getApplyDateButton() {
        return applyDateButton;
    }

    public JButton getClearDateButton() {
        return clearDateButton;
    }

    public JTextField getSearchBar() {
        return searchBar;
    }

    public JButton getSearchButton() {
        return searchButton;
    }

    public JButton getClearSearchButton() {
        return clearSearchButton;
    }

    public JLabel getTimeFrameLabel() {
        return timeFrameLabel;
    }

    public JTable getTable() {
        return table;
    }

    public TableRowSorter<DefaultTableModel> getTableSorter() {
        return tableSorter;
    }

    public JLabel getValueAmountLabel() {
        return valueAmountLabel;
    }
}