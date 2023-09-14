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
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.Timer;
import java.awt.print.*;

public class Controller {
    private Model model;
    private View view;
    int searchRowIndex;
    String previousSearch;
    /* saveUploadFileChooser separated from exportFileChooser because:
     *  - Different file filters options
     *  - We want the default file save name to be equal to the uploaded file name
     */
    SaveUploadFileChooser saveUploadFileChooser;
    ExportFileChooser exportFileChooser;
    Timer automaticSaveTimer;
    TimerTask automaticSaveTimerTask;
    boolean isSaveUpToDate;

    public Controller(Model model, View view) {
        this.model = model;
        this.view = view;
        init();
    }

    public void displayView() {
        view.display();
    }

    private void init() {
        initFrame();
        initAutomaticSaveTimer();
        initTable();
        addActionListeners();
        applyDate();
        previousSearch = "";
        isSaveUpToDate = true;
        saveUploadFileChooser = new SaveUploadFileChooser(isSaveUpToDate);
        exportFileChooser = new ExportFileChooser();
    }

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
        view.getApplyDateButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                applyDate();
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

    private void initTable() {
        view.getTable().getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    fillOperationalTextFields();
                }
            }
        });
    }

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

    private void printFile() {
        try {
            view.getTable().print();
        } catch (PrinterException pe) {
            JOptionPane.showMessageDialog(view.getFrame(), "Printing paused with error", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

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

    private BudgetItem createBudgetItem() {
        // Empty fields check
        if (view.getDateTextField().getText().isEmpty() || view.getDescriptionTextField().getText().isEmpty() || view.getAmountTextField().getText().isEmpty()) {
            if (view.getDateTextField().getText().isEmpty() && !view.getDescriptionTextField().getText().isEmpty() && !view.getAmountTextField().getText().isEmpty()) {
                view.getDateTextField().setText(LocalDate.now().toString());
            } else {
                JOptionPane.showMessageDialog(view.getFrame(), "Fill all the fields", "Error", JOptionPane.ERROR_MESSAGE);
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

    private int convertSelectedRowIndexToModel() {
        int selectedRowIndex = view.getTable().getSelectedRow();
        if (selectedRowIndex != -1) {
            return view.getTable().convertRowIndexToModel(selectedRowIndex);
        }
        JOptionPane.showMessageDialog(view.getFrame(), "No row is selected", "Error", JOptionPane.ERROR_MESSAGE);
        return -1;
    }

    private void deleteItem() {
        int modelRowIndex = convertSelectedRowIndexToModel();
        if (modelRowIndex != -1) {
            model.getTableModel().removeRow(modelRowIndex);
            updateValueAmountLabel();
            isSaveUpToDate = false;
            saveUploadFileChooser.setSaveUpToDate(isSaveUpToDate);
        }
    }

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

    private void applyDate() {
        String string1;
        String string2;
        if (view.getStartDateTextField().getText().isEmpty() && view.getEndDateTextField().getText().isEmpty()) {
            // Apply no filter
            string1 = "****-**-**";
            string2 = "****-**-**";
            view.getTableSorter().setRowFilter(null);
        } else {
            Vector<RowFilter<DefaultTableModel, Integer>> dateFilters = new Vector<>();
            RowFilter<DefaultTableModel, Integer> fromDateFilter;
            if (!view.getStartDateTextField().getText().isEmpty()) {
                LocalDate fromDate;
                try {
                    fromDate = LocalDate.parse(view.getStartDateTextField().getText());
                } catch (DateTimeParseException dtpe) {
                    JOptionPane.showMessageDialog(view.getFrame(), "Enter valid dates (yyyy-MM-dd)", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                string1 = view.getStartDateTextField().getText();
                fromDateFilter = new RowFilter<DefaultTableModel, Integer>() {
                    @Override
                    public boolean include(Entry<? extends DefaultTableModel, ? extends Integer> entry) {
                        LocalDate dateValue = (LocalDate) entry.getValue(0);
                        return dateValue.isAfter(fromDate) || dateValue.isEqual(fromDate);
                    }
                };
                dateFilters.add(fromDateFilter);
            } else {
                string1 = "****-**-**";
            }

            RowFilter<DefaultTableModel, Integer> toDateFilter;
            if (!view.getEndDateTextField().getText().isEmpty()) {
                LocalDate toDate;
                try {
                    toDate = LocalDate.parse(view.getEndDateTextField().getText());
                } catch (DateTimeParseException dtpe) {
                    JOptionPane.showMessageDialog(view.getFrame(), "Enter valid dates (yyyy-MM-dd)", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                string2 = view.getEndDateTextField().getText();
                toDateFilter = new RowFilter<DefaultTableModel, Integer>() {
                    @Override
                    public boolean include(Entry<? extends DefaultTableModel, ? extends Integer> entry) {
                        LocalDate dateValue = (LocalDate) entry.getValue(0);
                        return dateValue.isBefore(toDate) || dateValue.isEqual(toDate);
                    }
                };
                dateFilters.add(toDateFilter);
            } else {
                string2 = "****-**-**";
            }

            // Apply filter
            if (dateFilters.size() == 2) {
                LocalDate fromDate = LocalDate.parse(view.getStartDateTextField().getText());
                LocalDate toDate = LocalDate.parse(view.getEndDateTextField().getText());
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

    private void clearFilter() {
        view.getStartDateTextField().setText("");
        view.getEndDateTextField().setText("");
        applyDate();
    }

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

    private void clearSelection() {
        view.getSearchBar().setText("");
        search();
    }


}