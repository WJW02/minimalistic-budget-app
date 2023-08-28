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

    public Controller(Model model, View view) {
        this.model = model;
        this.view = view;
        init();
    }

    private void init() {
        addActionListeners();
        applyDate();
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
        view.getNextButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                next();
            }
        });
    }
    public void displayView() {
        view.display();
    }

    private void clearOperationalTextFields() {
        view.getDateTextField().setText("");
        view.getDescriptionTextField().setText("");
        view.getAmountTextField().setText("");
    }

    private void saveFile() {

    }

    private void uploadFile() {

    }

    private void exportFile() {

    }

    private void printFile() {

    }

    private void addItem() {
        // Empty fields check
        if (view.getDateTextField().getText().equals("") || view.getDescriptionTextField().getText().equals("") || view.getAmountTextField().getText().equals("")) {
            if (view.getDateTextField().getText().equals("") && !view.getDescriptionTextField().getText().equals("") && !view.getAmountTextField().getText().equals("")) {
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                Date date = new Date();
                view.getDateTextField().setText(formatter.format(date));
            }
            else {
                JOptionPane.showMessageDialog(view.getFrame(), "Fill all the fields", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }

        Date date;
        // Date format check
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            date = dateFormat.parse(view.getDateTextField().getText());
        } catch (ParseException pe) {
            JOptionPane.showMessageDialog(view.getFrame(), "Enter a valid date", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String description = view.getDescriptionTextField().getText();

        BigDecimal amount;
        // Amount format check
        try {
            amount = new BigDecimal(view.getAmountTextField().getText());
        } catch (NumberFormatException nfe) {
            JOptionPane.showMessageDialog(view.getFrame(), "Enter a valid amount", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Add item
        BudgetItem item = new BudgetItem(date, description, amount);
        model.addRow(item);
        clearOperationalTextFields();
    }

    private void deleteItem() {

    }

    private void updateItem() {

    }

    private void applyDate() {
        Date startDate, endDate;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Vector<RowFilter<DefaultTableModel, Integer>> orAfterDataRangeFilter = new Vector<>();
        Vector<RowFilter<DefaultTableModel, Integer>> orBeforeDataRangeFilter = new Vector<>();
        Vector<RowFilter<DefaultTableModel, Integer>> andDataRangeFilter = new Vector<>();

        boolean flag = false;
        String string1, string2;
        if (view.getStartDateTextField().getText().equals("") && view.getEndDateTextField().getText().equals("")) {
            view.getSorter().setRowFilter(null);
            flag = true;
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
        }

        // Apply filter
        if (!flag) {
            view.getSorter().setRowFilter(RowFilter.andFilter(andDataRangeFilter));
        }

        // Change timeFrameLabel
        view.getTimeFrameLabel().setText("From " + string1 + " To " + string2);
    }

    private void search() {

    }

    private void next() {

    }




}