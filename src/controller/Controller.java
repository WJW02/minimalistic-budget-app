package controller;
import model.BudgetItem;
import model.Model;
import view.View;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.time.DateTimeException;
import java.time.LocalDate;

public class Controller {
    private Model model;
    private View view;

    public Controller(Model model, View view) {
        this.model = model;
        this.view = view;
        addActionListeners();
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
            JOptionPane.showMessageDialog(view.getFrame(), "Fill all the fields", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        LocalDate date;
        // Date format check
        try {
            date = LocalDate.parse(view.getDateTextField().getText());
        } catch (DateTimeException dte) {
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

    }

    private void search() {

    }

    private void next() {

    }




}