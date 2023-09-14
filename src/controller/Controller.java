package controller;

import model.BudgetItem;
import model.Model;
import view.View;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.Timer;
import java.awt.print.*;

import static java.time.temporal.TemporalAdjusters.*;

/**
 * The class providing the controller of the application.
 * <p>
 * The controller connects the {@link View} with the {@link Model}.
 * <p>
 * saveUploadFileChooser is separated from exportFileChooser because:
 * <ul>
 *     <li>They need different file filters options</li>
 *     <li>This way the default file save name will be equal to the uploaded file name
 *     when the dialog shows up</li>
 * </ul>
 *
 * @author WJW02
 */
public class Controller {
    /**
     * The model of the application.
     */
    private Model model;
    /**
     * The view of the application.
     */
    private View view;
    /**
     * The starting search index for {@link View#getTable()}.
     */
    int searchRowIndex;
    /**
     * The last searched string in {@link View#getTable()}.
     */
    String previousSearch;
    /**
     * The file chooser used for save and upload.
     */
    SaveUploadFileChooser saveUploadFileChooser;
    /**
     * The file chooser used for export.
     */
    ExportFileChooser exportFileChooser;
    /**
     * The timer executing {@link #automaticSaveTimerTask}.
     */
    Timer automaticSaveTimer;
    /**
     * The task executed by {@link #automaticSaveTimer}.
     */
    TimerTask automaticSaveTimerTask;
    /**
     * Checks if save is up-to-date.
     */
    boolean isSaveUpToDate;

    /**
     * Creates a controller and connects it to the model and the view.
     *
     * @param model the model of the application
     * @param view  the view of the application
     * @see #init()
     */
    public Controller(Model model, View view) {
        this.model = model;
        this.view = view;
        init();
    }

    /**
     * Makes the view visible.
     */
    public void displayView() {
        view.display();
    }

    /**
     * Initializes the underlying mechanisms.
     *
     * @see #initFrame()
     * @see #initAutomaticSaveTimer()
     * @see #initTable()
     * @see #addActionListeners()
     * @see #applyOther()
     */
    private void init() {
        initFrame();
        initAutomaticSaveTimer();
        initTable();
        addActionListeners();
        selectDateFilter();
        previousSearch = "";
        isSaveUpToDate = true;
        saveUploadFileChooser = new SaveUploadFileChooser(isSaveUpToDate);
        exportFileChooser = new ExportFileChooser();
        view.getDateTextField().setText(LocalDate.now().toString());
    }

    /**
     * Binds the buttons of the view with <Code>ActionListeners</Code>.
     */
    private void addActionListeners() {
        view.getSaveFileButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                manualSaveFile();
            }
        });
        view.getUploadFileButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                uploadFile();
            }
        });
        view.getExportFileButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                exportFile();
            }
        });
        view.getPrintFileButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                printFile();
            }
        });
        view.getAddButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addItem();
            }
        });
        view.getDeleteButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteItem();
            }
        });
        view.getUpdateButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateItem();
            }
        });
        view.getDateComboBox().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectDateFilter();
            }
        });
        view.getApplyDateButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                applyOther();
            }
        });
        view.getClearDateButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearFilter();
            }
        });
        view.getSearchButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                search();
            }
        });
        view.getClearSearchButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearSelection();
            }
        });
    }

    /**
     * Initializes {@link View#getFrame()} to show a confirmation dialog when
     * trying to close without saving.
     */
    private void initFrame() {
        view.getFrame().setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        view.getFrame().addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if (!isSaveUpToDate) {
                    int result = JOptionPane.showConfirmDialog(view.getFrame(), "You haven't saved your changes. Are you sure you want to exit?", "Warning", JOptionPane.YES_NO_OPTION);
                    if (result != JOptionPane.YES_OPTION) {
                        return;
                    }
                }
                System.exit(0);
            }
        });
    }

    /**
     * Initializes {@link #automaticSaveTimer} to {@link Timer#scheduleAtFixedRate(TimerTask, long, long)}
     * {@link #automaticSaveTimerTask}.
     * <p>
     * automaticSaveTimerTask runs {@link #automaticSaveFile()}.
     */
    private void initAutomaticSaveTimer() {
        automaticSaveTimer = new Timer();
        automaticSaveTimerTask = new TimerTask() {
            @Override
            public void run() {
                automaticSaveFile();
            }
        };
        automaticSaveTimer.scheduleAtFixedRate(automaticSaveTimerTask, 600000, 600000);
    }

    /**
     * Fills the text fields in the Table Operation section with the values
     * of the selected row.
     * If no row is selected the text fields get cleared.
     */
    private void fillOperationalTextFields() {
        JTable table = view.getTable();
        int selectedRowIndex;
        if ((selectedRowIndex = table.getSelectedRow()) == -1) {
            view.getDateTextField().setText("");
            view.getDescriptionTextField().setText("");
            view.getAmountTextField().setText("");
            return;
        }
        // Fill the operational text fields with the content of the selected row
        view.getDateTextField().setText(table.getValueAt(selectedRowIndex, 0).toString());
        view.getDescriptionTextField().setText(table.getValueAt(selectedRowIndex, 1).toString());
        view.getAmountTextField().setText(table.getValueAt(selectedRowIndex, 2).toString());
    }

    /**
     * Initializes the table to fill in the text fields in the Table Operation section when selected
     * and sets its selection mode to single.
     */
    private void initTable() {
        view.getTable().getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    fillOperationalTextFields();
                }
            }
        });
        view.getTable().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }

    /**
     * Makes a backup of {@link Model}.
     */
    private void automaticSaveFile() {
        if (model.getTableModel().getRowCount() == 0) {
            return;
        }
        File file = new File("tmp" + File.separator + "backup.txt");
        CustomTXTWriter writer = new CustomTXTWriter();
        try {
            writer.write(model, file);
        } catch (IOException ioe) {
            JOptionPane.showMessageDialog(view.getFrame(), "Auto-save failed", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Shows the save dialog to save the {@link Model} in a .txt file.
     */
    private void manualSaveFile() {
        if (saveUploadFileChooser.showSaveDialog(view.getFrame()) == JFileChooser.APPROVE_OPTION) {
            File file = saveUploadFileChooser.getSelectedFileWithExtension();
            if (file == null) {
                JOptionPane.showMessageDialog(view.getFrame(), "This file type is not supported", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            CustomTXTWriter writer = new CustomTXTWriter();
            try {
                writer.write(model, file);
            } catch (IOException ioe) {
                JOptionPane.showMessageDialog(saveUploadFileChooser, "File save failed", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            isSaveUpToDate = true;
            saveUploadFileChooser.setSaveUpToDate(isSaveUpToDate);
        }
    }

    /**
     * Shows the open dialog to upload a compatible .txt file in the {@link Model}.
     */
    private void uploadFile() {
        if (saveUploadFileChooser.showOpenDialog(view.getFrame()) == JFileChooser.APPROVE_OPTION) {
            File file = saveUploadFileChooser.getSelectedFile();
            CustomTXTReader reader = new CustomTXTReader();
            try {
                if (reader.read(file, model)) {
                    updateValueAmountLabel();
                    isSaveUpToDate = true;
                    saveUploadFileChooser.setSaveUpToDate(isSaveUpToDate);
                } else {
                    JOptionPane.showMessageDialog(view.getFrame(), "File contains incompatible data", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (IOException ioe) {
                JOptionPane.showMessageDialog(saveUploadFileChooser, "File doesn't exist or can't be opened", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * Shows a save dialog to export the {@link Model} in a file (csv, txt, ods).
     */
    private void exportFile() {
        // Show FileChooser (that is set to show 3 filters)
        if (exportFileChooser.showSaveDialog(view.getFrame()) == JFileChooser.APPROVE_OPTION) {
            File file = exportFileChooser.getSelectedFileWithExtension();
            // Check file's extension and assign the correct writer based on the filter chosen
            if (file == null) {
                JOptionPane.showMessageDialog(view.getFrame(), "This file type is not supported", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            String extension = file.getName().substring(file.getName().lastIndexOf("."), file.getName().length());
            CustomWriter writer;
            switch (extension) {
                case ".csv":
                    writer = new CustomCSVWriter();
                    break;
                case ".txt":
                    writer = new CustomTXTWriter();
                    break;
                case ".ods":
                    writer = new CustomODSWriter();
                    break;
                default:
                    JOptionPane.showMessageDialog(view.getFrame(), "This file type is not supported", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
            }
            // Export (Polymorphism)
            try {
                writer.write(model, file);
            } catch (IOException ioe) {
                JOptionPane.showMessageDialog(exportFileChooser, "Exportation failed", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * Shows a print dialog to print the {@link Model}.
     */
    private void printFile() {
        try {
            view.getTable().print();
        } catch (PrinterException pe) {
            JOptionPane.showMessageDialog(view.getFrame(), "Printing paused with error", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Updates {@link View#getValueAmountLabel()} to show the total amount.
     */
    private void updateValueAmountLabel() {
        BigDecimal totalValue = new BigDecimal(0);
        int rowCount = view.getTableSorter().getViewRowCount();

        for (int i = 0; i < rowCount; ++i) {
            int modelRowIndex = view.getTableSorter().convertRowIndexToModel(i);
            BigDecimal value = (BigDecimal) model.getTableModel().getValueAt(modelRowIndex, 2);
            totalValue = totalValue.add(value);
        }
        view.getValueAmountLabel().setText(totalValue + "€");
    }

    /**
     * Creates a {@link BudgetItem} from the values in {@link View#getDateTextField()},
     * {@link View#getDescriptionTextField()} and {@link View#getAmountTextField()}.
     *
     * @return <code>BudgetItem</code> - if the text fields contain compatible data<br>
     * <code>null</code> - otherwise
     */
    private BudgetItem createBudgetItem() {
        // Empty fields check
        if (view.getDateTextField().getText().isEmpty() || view.getDescriptionTextField().getText().isEmpty() || view.getAmountTextField().getText().isEmpty()) {
            if (view.getDateTextField().getText().isEmpty() && !view.getDescriptionTextField().getText().isEmpty() && !view.getAmountTextField().getText().isEmpty()) {
                view.getDateTextField().setText(LocalDate.now().toString());
            } else {
                JOptionPane.showMessageDialog(view.getFrame(), "The Description and the Amount fields must be filled in", "Error", JOptionPane.ERROR_MESSAGE);
                return null;
            }
        }

        LocalDate date;
        // Date format check
        try {
            date = LocalDate.parse(view.getDateTextField().getText());
        } catch (DateTimeParseException dtpe) {
            JOptionPane.showMessageDialog(view.getFrame(), "Enter a valid date (yyyy-MM-dd)", "Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }

        String description = view.getDescriptionTextField().getText();

        BigDecimal amount;
        // Amount format check
        try {
            amount = new BigDecimal(view.getAmountTextField().getText());
        } catch (NumberFormatException nfe) {
            JOptionPane.showMessageDialog(view.getFrame(), "Enter a valid amount", "Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }
        return new BudgetItem(date, description, amount);
    }

    /**
     * Adds the {@link BudgetItem} from the values in {@link View#getDateTextField()},
     * {@link View#getDescriptionTextField()} and {@link View#getAmountTextField()}
     * in the {@link Model}.
     *
     * @see #createBudgetItem()
     */
    private void addItem() {
        BudgetItem item = createBudgetItem();
        if (item == null) {
            return;
        }
        model.addRow(item);
        updateValueAmountLabel();
        // Clear operational text fields
        view.getDescriptionTextField().setText("");
        view.getAmountTextField().setText("");
        isSaveUpToDate = false;
        saveUploadFileChooser.setSaveUpToDate(isSaveUpToDate);
    }

    /**
     * Converts the index of the selected row in {@link View#getTable()} to its
     * equivalent in {@link Model#getTableModel()}.
     *
     * @return <code>index</code> - if true<br>
     * <code>-1</code> - otherwise
     */
    private int convertSelectedRowIndexToModel() {
        int selectedRowIndex = view.getTable().getSelectedRow();
        if (selectedRowIndex != -1) {
            return view.getTable().convertRowIndexToModel(selectedRowIndex);
        }
        JOptionPane.showMessageDialog(view.getFrame(), "No row is selected", "Error", JOptionPane.ERROR_MESSAGE);
        return -1;
    }

    /**
     * Deletes the selected item from {@link Model}.
     */
    private void deleteItem() {
        int modelRowIndex = convertSelectedRowIndexToModel();
        if (modelRowIndex != -1) {
            model.getTableModel().removeRow(modelRowIndex);
            updateValueAmountLabel();
            isSaveUpToDate = false;
            saveUploadFileChooser.setSaveUpToDate(isSaveUpToDate);
        }
    }

    /**
     * Updates the selected item with the values specified in {@link View#getDateTextField()},
     * {@link View#getDescriptionTextField()} and {@link View#getAmountTextField()}.
     *
     * @see #createBudgetItem()
     */
    private void updateItem() {
        int modelRowIndex = convertSelectedRowIndexToModel();
        if (modelRowIndex != -1) {
            BudgetItem item = createBudgetItem();
            if (item != null) {
                model.getTableModel().setValueAt(item.getDate(), modelRowIndex, 0);
                model.getTableModel().setValueAt(item.getDescription(), modelRowIndex, 1);
                model.getTableModel().setValueAt(item.getAmount(), modelRowIndex, 2);
                updateValueAmountLabel();
                isSaveUpToDate = false;
                saveUploadFileChooser.setSaveUpToDate(isSaveUpToDate);
            }
        }
    }

    /**
     * Disables the components that lets the user to freely specify the date range.
     */
    private void disableOtherComponents() {
        view.getStartDateTextField().setEnabled(false);
        view.getEndDateTextField().setEnabled(false);
        view.getApplyDateButton().setEnabled(false);
        view.getClearDateButton().setEnabled(false);
    }

    /**
     * Selects the filter to apply on {@link View#getTable()} given
     * the selected item on {@link View#getDateComboBox()}.
     * <p>
     * The option <code>other</code> allows to freely specify the date range
     * by enabling the right components.
     *
     * @see #applyDate(LocalDate, LocalDate)
     * @see #disableOtherComponents()
     */
    private void selectDateFilter() {
        String selectedItem = (String) view.getDateComboBox().getSelectedItem();
        LocalDate today = LocalDate.now();
        switch (selectedItem) {
            case "Today":
                disableOtherComponents();
                applyDate(today, today);
                break;
            case "This week":
                disableOtherComponents();
                applyDate(today.with(previousOrSame(DayOfWeek.MONDAY)), today.with(nextOrSame(DayOfWeek.SUNDAY)));
                break;
            case "This month":
                disableOtherComponents();
                applyDate(today.with(firstDayOfMonth()), today.with(lastDayOfMonth()));
                break;
            case "This year":
                disableOtherComponents();
                applyDate(today.with(firstDayOfYear()), today.with(lastDayOfYear()));
                break;
            case "Other":
                view.getStartDateTextField().setEnabled(true);
                view.getEndDateTextField().setEnabled(true);
                view.getApplyDateButton().setEnabled(true);
                view.getClearDateButton().setEnabled(true);
                break;
        }
    }

    /**
     * Applies the date filter specified by {@link View#getStartDateTextField()}
     * and {@link View#getEndDateTextField()} on {@link View#getTable()}.
     *
     * @see #applyDate(LocalDate, LocalDate)
     */
    private void applyOther() {
        LocalDate fromDate, toDate;
        if (!view.getStartDateTextField().getText().isEmpty()) {
            try {
                fromDate = LocalDate.parse(view.getStartDateTextField().getText());
            } catch (DateTimeParseException dtpe) {
                JOptionPane.showMessageDialog(view.getFrame(), "Enter valid dates (yyyy-MM-dd)", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        } else {
            fromDate = null;
        }
        if (!view.getEndDateTextField().getText().isEmpty()) {
            try {
                toDate = LocalDate.parse(view.getEndDateTextField().getText());
            } catch (DateTimeParseException dtpe) {
                JOptionPane.showMessageDialog(view.getFrame(), "Enter valid dates (yyyy-MM-dd)", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        } else {
            toDate = null;
        }
        applyDate(fromDate, toDate);

    }

    /**
     * Applies the date filter on {@link View#getTable()}.
     *
     * @param fromDate start date
     * @param toDate end date
     * @see RowFilter
     */
    private void applyDate(LocalDate fromDate, LocalDate toDate) {
        String string1;
        String string2;
        if (fromDate == null && toDate == null) {
            string1 = "****-**-**";
            string2 = "****-**-**";
            view.getTableSorter().setRowFilter(null);
        } else {
            Vector<RowFilter<DefaultTableModel, Integer>> dateFilters = new Vector<>();
            RowFilter<DefaultTableModel, Integer> fromDateFilter;
            RowFilter<DefaultTableModel, Integer> toDateFilter;
            if (fromDate != null) {
                fromDateFilter = new RowFilter<>() {
                    @Override
                    public boolean include(Entry<? extends DefaultTableModel, ? extends Integer> entry) {
                        LocalDate dateValue = (LocalDate) entry.getValue(0);
                        return dateValue.isAfter(fromDate) || dateValue.isEqual(fromDate);
                    }
                };
                dateFilters.add(fromDateFilter);
                string1 = fromDate.toString();
            } else {
                string1 = "****-**-**";
            }
            if (toDate != null) {
                toDateFilter = new RowFilter<>() {
                    @Override
                    public boolean include(Entry<? extends DefaultTableModel, ? extends Integer> entry) {
                        LocalDate dateValue = (LocalDate) entry.getValue(0);
                        return dateValue.isBefore(toDate) || dateValue.isEqual(toDate);
                    }
                };
                dateFilters.add(toDateFilter);
                string2 = toDate.toString();
            } else {
                string2 = "****-**-**";
            }
            if (dateFilters.size() == 2) {
                if (fromDate.isAfter(toDate)) {
                    JOptionPane.showMessageDialog(view.getFrame(), "End date should come after the start date", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }
            view.getTableSorter().setRowFilter(RowFilter.andFilter(dateFilters));
        }
        updateValueAmountLabel();
        // Update timeFrameLabel
        view.getTimeFrameLabel().setText("From " + string1 + " To " + string2);
    }

    /**
     * Clears the date filters applied on {@link View#getTable()}.
     */
    private void clearFilter() {
        view.getStartDateTextField().setText("");
        view.getEndDateTextField().setText("");
        applyOther();
    }

    /**
     * Highlights the first row that matches the string in {@link View#getSearchBar()}.
     */
    private void search() {
        String key = view.getSearchBar().getText().toLowerCase();
        if (key.isEmpty()) {
            view.getTable().clearSelection();
            searchRowIndex = 0;
            previousSearch = "";
            return;
        } else if (!key.equals(previousSearch)) {
            view.getTable().clearSelection();
            searchRowIndex = 0;
            previousSearch = key;
        }

        int rowCount = view.getTableSorter().getViewRowCount();
        int columnCount = model.getTableModel().getColumnCount();
        if (searchRowIndex >= rowCount) {
            searchRowIndex = 0;
        }

        for (; searchRowIndex < rowCount; ++searchRowIndex) {
            int modelRowIndex = view.getTableSorter().convertRowIndexToModel(searchRowIndex);
            for (int searchColumnIndex = 0; searchColumnIndex < columnCount; ++searchColumnIndex) {
                String s = model.getTableModel().getValueAt(modelRowIndex, searchColumnIndex).toString().toLowerCase();
                if (s.contains(key)) {
                    view.getTable().changeSelection(searchRowIndex, searchColumnIndex, false, false);
                    fillOperationalTextFields();
                    ++searchRowIndex;
                    return;
                }
            }
        }
    }

    /**
     * Clears the search and the selected row on {@link View#getTable()}.
     */
    private void clearSelection() {
        view.getSearchBar().setText("");
        search();
    }


}