package controller;
import model.BudgetItem;
import model.Model;
import view.View;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Vector;

public class Controller {
    private Model model;
    private View view;
    int searchRowIndex;
    String previousSearch;

    public Controller(Model model, View view) {
        this.model = model;
        this.view = view;
        init();
    }

    private void init() {
        addActionListeners();
        applyDate();
        previousSearch = "";
    }

    private void addActionListeners() {
        view.getSaveFileButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveFile();
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

        view.getSearchButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                search();
            }
        });
        view.getClearButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clear();
            }
        });
    }
    public void displayView() {
        view.display();
    }

    private void saveFile() {

    }

    private void uploadFile() {

    }

    private void exportFile() {

    }

    private void printFile() {

    }

    private void clearOperationalTextFields() {
        view.getDateTextField().setText("");
        view.getDescriptionTextField().setText("");
        view.getAmountTextField().setText("");
    }

    private void updateValueAmountLabel() {
        BigDecimal totalValue = new BigDecimal(0);
        int rowCount = view.getSorter().getViewRowCount();

        for (int i = 0; i < rowCount; ++i) {
            int modelRowIndex = view.getSorter().convertRowIndexToModel(i);
            BigDecimal value = (BigDecimal) model.getTableModel().getValueAt(modelRowIndex, 2);
            totalValue = totalValue.add(value);
        }

        view.getValueAmountLabel().setText("€" + String.valueOf(totalValue));
    }

    private BudgetItem createBudgetItem() {
        // Empty fields check
        if (view.getDateTextField().getText().equals("") || view.getDescriptionTextField().getText().equals("") || view.getAmountTextField().getText().equals("")) {
            if (view.getDateTextField().getText().equals("") && !view.getDescriptionTextField().getText().equals("") && !view.getAmountTextField().getText().equals("")) {
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                Date date = new Date();
                view.getDateTextField().setText(formatter.format(date));
            }
            else {
                JOptionPane.showMessageDialog(view.getFrame(), "Fill all the fields", "Error", JOptionPane.ERROR_MESSAGE);
                return null;
            }
        }

        Date date;
        // Date format check
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            date = dateFormat.parse(view.getDateTextField().getText());
        } catch (ParseException pe) {
            JOptionPane.showMessageDialog(view.getFrame(), "Enter a valid date", "Error", JOptionPane.ERROR_MESSAGE);
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
        clearOperationalTextFields();
        return new BudgetItem(date, description, amount);
    }

    private void addItem() {
        BudgetItem item = createBudgetItem();
        if (item == null) {
            return;
        }
        model.addRow(item);
        updateValueAmountLabel();
    }

    private int convertRowIndexToModel() {
        int selectedRowIndex = view.getTable().getSelectedRow();
        if (selectedRowIndex != -1) {
            return view.getTable().convertRowIndexToModel(selectedRowIndex);
        }
        JOptionPane.showMessageDialog(view.getFrame(), "No row is selected", "Error", JOptionPane.ERROR_MESSAGE);
        return -1;
    }

    private void deleteItem() {
        int modelRowIndex = convertRowIndexToModel();
        if (modelRowIndex != -1) {
            model.getTableModel().removeRow(modelRowIndex);
            updateValueAmountLabel();
        }
    }

    private void updateItem() {
        int modelRowIndex = convertRowIndexToModel();
        if (modelRowIndex != -1) {
            BudgetItem item = createBudgetItem();
            if (item != null) {
                model.getTableModel().setValueAt(item.getDate(), modelRowIndex, 0);
                model.getTableModel().setValueAt(item.getDescription(), modelRowIndex, 1);
                model.getTableModel().setValueAt(item.getAmount(), modelRowIndex, 2);
                updateValueAmountLabel();
            }
        }
    }

    private void applyDate() {
        Date startDate, endDate;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Vector<RowFilter<DefaultTableModel, Integer>> orAfterDataRangeFilter = new Vector<>();
        Vector<RowFilter<DefaultTableModel, Integer>> orBeforeDataRangeFilter = new Vector<>();
        Vector<RowFilter<DefaultTableModel, Integer>> andDataRangeFilter = new Vector<>();

        String string1, string2;
        if (view.getStartDateTextField().getText().equals("") && view.getEndDateTextField().getText().equals("")) {
            // Apply no filter
            view.getSorter().setRowFilter(null);
            string1 = "****-**-**";
            string2 = "****-**-**";
        }
        else {
            if (!view.getStartDateTextField().getText().equals("")) {
                // Date format check
                try {
                    startDate = dateFormat.parse(view.getStartDateTextField().getText());
                } catch (ParseException pe) {
                    JOptionPane.showMessageDialog(view.getFrame(), "Enter valid dates", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                orAfterDataRangeFilter.add(RowFilter.dateFilter(RowFilter.ComparisonType.AFTER, startDate, 0));
                // Necessary because RowFilter.ComparisonType.AFTER matches only with date strictly greater than startDate
                orAfterDataRangeFilter.add(RowFilter.dateFilter(RowFilter.ComparisonType.EQUAL, startDate, 0));
                andDataRangeFilter.add(RowFilter.orFilter(orAfterDataRangeFilter));
                string1 = view.getStartDateTextField().getText();
            }
            else {
                string1 = "****-**-**";
            }
            if (!view.getEndDateTextField().getText().equals("")) {
                // Date format check
                try {
                    endDate = dateFormat.parse(view.getEndDateTextField().getText());
                } catch (ParseException pe) {
                    JOptionPane.showMessageDialog(view.getFrame(), "Enter valid dates", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                orBeforeDataRangeFilter.add(RowFilter.dateFilter(RowFilter.ComparisonType.BEFORE, endDate, 0));
                // Necessary because RowFilter.ComparisonType.BEFORE matches only with date strictly smaller than endDate
                orBeforeDataRangeFilter.add(RowFilter.dateFilter(RowFilter.ComparisonType.EQUAL, endDate, 0));
                andDataRangeFilter.add(RowFilter.orFilter(orBeforeDataRangeFilter));
                string2 = view.getEndDateTextField().getText();
            }
            else {
                string2 = "****-**-**";
            }
            // Apply filter
            view.getSorter().setRowFilter(RowFilter.andFilter(andDataRangeFilter));
        }

        updateValueAmountLabel();

        // Update timeFrameLabel
        view.getTimeFrameLabel().setText("From " + string1 + " To " + string2);
    }

    private void search() {
        String key = view.getSearchBar().getText();
        if (key.equals("")) {
            view.getTable().clearSelection();
            searchRowIndex = 0;
            previousSearch = "";
            return;
        } else if (!key.equals(previousSearch)) {
            view.getTable().clearSelection();
            searchRowIndex = 0;
            previousSearch = key;
        }

        int rowCount = view.getSorter().getViewRowCount();
        int columnCount = model.getTableModel().getColumnCount();
        if (searchRowIndex >= rowCount) {
            searchRowIndex = 0;
        }

        for (; searchRowIndex < rowCount; ++searchRowIndex) {
            int modelRowIndex = view.getSorter().convertRowIndexToModel(searchRowIndex);
            for (int searchColumnIndex = 0; searchColumnIndex < columnCount; ++searchColumnIndex) {
                String s = model.getTableModel().getValueAt(modelRowIndex, searchColumnIndex).toString();
                if (s.contains(key)) {
                    view.getTable().changeSelection(searchRowIndex, searchColumnIndex, false, false);
                    ++searchRowIndex;
                    return;
                }
            }
        }
    }

    private void clear() {
        view.getSearchBar().setText("");
        search();
    }




}