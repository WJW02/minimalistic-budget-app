package controller;
import model.BudgetItem;
import model.Model;
import view.View;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.Timer;

public class Controller {
    private Model model;
    private View view;
    int searchRowIndex;
    String previousSearch;
    /* saveUploadFileChooser separated from exportFileChooser because:
     *  - Different file filters options
     *  - Default file save name = uploaded file
     */
    JFileChooser saveUploadFileChooser;
    JFileChooser exportFileChooser;
    Timer automaticSaveTimer;
    TimerTask automaticSaveTimerTask;

    public Controller(Model model, View view) {
        this.model = model;
        this.view = view;
        init();
    }

    private void init() {
        addActionListeners();
        applyDate();
        previousSearch = "";
        saveUploadFileChooser = initSaveUploadFileChooser();
        exportFileChooser = initExportFileChooser();
        automaticSaveTimer = new Timer();
        automaticSaveTimerTask = new TimerTask() {
            @Override
            public void run() {
                automaticSaveFile();
            }
        };
        automaticSaveTimer.scheduleAtFixedRate(automaticSaveTimerTask, 600000, 600000);
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

    private File getSelectedFileWithExtension(JFileChooser fileChooser) {
        File file = fileChooser.getSelectedFile();
        String fileDescription = fileChooser.getFileFilter().getDescription();
        switch (fileDescription) {
            case "TEXT file":
                if (!file.getName().endsWith(".txt")) {
                    file = new File(file + ".txt");
                }
                break;
            case "CVS file":
                if (!file.getName().endsWith(".cvs")) {
                    file = new File(file + ".cvs");
                }
                break;
            case "ODF file":
                if (!file.getName().endsWith(".odf")) {
                    file = new File(file + ".odf");
                }
                break;
            default:
                JOptionPane.showMessageDialog(view.getFrame(), "Extension not available", "Error", JOptionPane.ERROR_MESSAGE);
                break;
        }
        return file;
    }

    private JFileChooser initSaveUploadFileChooser() {
        JFileChooser saveUploadFileChooser = new JFileChooser() {
            @Override
            public void approveSelection() {
                File file = getSelectedFileWithExtension(this);

                /*
                if (!file.getName().endsWith(".txt")) {
                    file = new File(file + ".txt");
                }
                 */
                if (file.exists() && getDialogType() == SAVE_DIALOG) {
                    int result = JOptionPane.showConfirmDialog(this, "File already exists, do you want to overwrite it?", "Error", JOptionPane.YES_NO_OPTION);
                    if (result != JOptionPane.YES_OPTION) {
                        return;
                    }
                }
                else if (!file.exists() && getDialogType() == OPEN_DIALOG) {
                    JOptionPane.showMessageDialog(view.getFrame(), "File doesn't exist or can't be opened", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                super.approveSelection();
            }
        };
        saveUploadFileChooser.removeChoosableFileFilter(saveUploadFileChooser.getFileFilter());  //remove the default file filter
        FileNameExtensionFilter filter = new FileNameExtensionFilter("TEXT file", "txt");
        saveUploadFileChooser.addChoosableFileFilter(filter);
        return saveUploadFileChooser;
    }

    public JFileChooser initExportFileChooser() {
        JFileChooser exportFileChooser = new JFileChooser() {
            @Override
            public void approveSelection() {
                File file = getSelectedFileWithExtension(this);
                if (file.exists() && getDialogType() == SAVE_DIALOG) {
                    int result = JOptionPane.showConfirmDialog(this, "File already exists, do you want to overwrite it?", "Error", JOptionPane.YES_NO_OPTION);
                    if (result != JOptionPane.YES_OPTION) {
                        return;
                    }
                }
                super.approveSelection();
            }
        };
        exportFileChooser.removeChoosableFileFilter(exportFileChooser.getFileFilter());  //remove the default file filter
        exportFileChooser.addChoosableFileFilter(new FileNameExtensionFilter("CSV file", "csv"));
        exportFileChooser.addChoosableFileFilter(new FileNameExtensionFilter("TEXT file", "txt"));
        exportFileChooser.addChoosableFileFilter(new FileNameExtensionFilter("ODF file", "odf"));
        return exportFileChooser;
    }

    public void displayView() {
        view.display();
    }

    private void automaticSaveFile() {
        if (model.getTableModel().getRowCount() == 0) {
            return;
        }
        File file = new File("tmp" + File.separator + "backup.txt");
        CustomTXTWriter writer = new CustomTXTWriter();
        writer.write(model, view, file);
    }

    private void manualSaveFile() {
        if (saveUploadFileChooser.showSaveDialog(view.getFrame()) == JFileChooser.APPROVE_OPTION) {
            File file = getSelectedFileWithExtension(saveUploadFileChooser);
            CustomTXTWriter writer = new CustomTXTWriter();
            writer.write(model, view, file);
        }
    }

    private void uploadFile() {
        if (saveUploadFileChooser.showOpenDialog(view.getFrame()) == JFileChooser.APPROVE_OPTION) {
            File file = saveUploadFileChooser.getSelectedFile();
            try (BufferedReader reader = new BufferedReader(new FileReader(file.getAbsolutePath()))) {
                // By reading lines it forces the data structure to be exact
                // That helps to identify not compatible data better
                String line;
                boolean flag = false;
                Vector<BudgetItem> budgetItems = new Vector<>();
                while ((line = reader.readLine()) != null) {
                    String[] components = line.split("\t");
                    if (components.length != 3) {
                        flag = true;
                        break;
                    }
                    // Not using already created methods because of different error handling and efficiency reasons (and laziness to refactor)
                    LocalDate date;
                    // Date format check
                    try {
                        date = LocalDate.parse(components[0]);
                    } catch (DateTimeParseException dtpe) {
                        flag = true;
                        break;
                    }

                    String description = components[1];

                    BigDecimal amount;
                    // Amount format check
                    try {
                        amount = new BigDecimal(components[2]);
                    } catch (NumberFormatException nfe) {
                        flag = true;
                        break;
                    }
                    budgetItems.add(new BudgetItem(date, description, amount));
                }
                if (flag) {
                    JOptionPane.showMessageDialog(view.getFrame(), "File contains incompatible data", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    // Clear table model
                    model.getTableModel().setRowCount(0);
                    // Add new items to table model
                    for (int i = 0; i < budgetItems.size(); ++i) {
                        BudgetItem item = budgetItems.get(i);
                        model.addRow(item);
                    }
                    updateValueAmountLabel();
                }
            } catch (IOException ioe) {
                JOptionPane.showMessageDialog(view.getFrame(), "File doesn't exist or can't be opened", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void exportFile() {
        // Show FileChooser (that is set to show 3 filters)
        if (exportFileChooser.showSaveDialog(view.getFrame()) == JFileChooser.APPROVE_OPTION) {
            File file = getSelectedFileWithExtension(exportFileChooser);
            // Get selected file
            // Check what filter was chosen:
            //  - Force the extension based on the filter chosen
            //  - Create the exporter based on the filter chosen
            String extension = file.getName().substring(file.getName().lastIndexOf("."), file.getName().length());
            CustomWriter writer;
            switch (extension) {
                case ".csv":
                    return;
                case ".txt":
                    writer = new CustomTXTWriter();
                    break;
                case ".odf":
                    return;
                default:
                    JOptionPane.showMessageDialog(view.getFrame(), "Exportation failed", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
            }
            // Export (Write to file) (Polymorphism)
            writer.write(model, view, file);
        }
    }

    private void printFile() {

    }

    private void clearOperationalTextFields() {
        // view.getDateTextField().setText("");
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

        view.getValueAmountLabel().setText(String.valueOf(totalValue) + "€");
    }

    private BudgetItem createBudgetItem() {
        // Empty fields check
        if (view.getDateTextField().getText().equals("") || view.getDescriptionTextField().getText().equals("") || view.getAmountTextField().getText().equals("")) {
            if (view.getDateTextField().getText().equals("") && !view.getDescriptionTextField().getText().equals("") && !view.getAmountTextField().getText().equals("")) {
                view.getDateTextField().setText(LocalDate.now().toString());
            }
            else {
                JOptionPane.showMessageDialog(view.getFrame(), "Fill all the fields", "Error", JOptionPane.ERROR_MESSAGE);
                return null;
            }
        }

        LocalDate date;
        // Date format check
        try {
            date = LocalDate.parse(view.getDateTextField().getText());
        } catch (DateTimeParseException dtpe) {
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
        String string1 = "****-**-**";
        String string2 = "****-**-**";
        if (view.getStartDateTextField().getText().equals("") && view.getEndDateTextField().getText().equals("")) {
            // Apply no filter
            string1 = "****-**-**";
            string2 = "****-**-**";
            view.getSorter().setRowFilter(null);
        }
        else {
            Vector<RowFilter<DefaultTableModel, Integer>> dateFilters = new Vector<>();
            RowFilter<DefaultTableModel, Integer> fromDateFilter;
            if (!view.getStartDateTextField().getText().equals("")) {
                 fromDateFilter = new RowFilter<DefaultTableModel, Integer>() {
                    @Override
                    public boolean include(Entry<? extends DefaultTableModel, ? extends Integer> entry) {
                        LocalDate dateValue = (LocalDate) entry.getValue(0);
                        LocalDate fromDate = LocalDate.parse(view.getStartDateTextField().getText());
                        return dateValue.isAfter(fromDate) || dateValue.isEqual(fromDate);
                    }
                 };
                 dateFilters.add(fromDateFilter);
            } else {
                string1 = "****-**-**";
            }

            RowFilter<DefaultTableModel, Integer> toDateFilter;
            if (!view.getEndDateTextField().getText().equals("")) {
                toDateFilter = new RowFilter<DefaultTableModel, Integer>() {
                    @Override
                    public boolean include(Entry<? extends DefaultTableModel, ? extends Integer> entry) {
                        LocalDate dateValue = (LocalDate) entry.getValue(0);
                        LocalDate toDate = LocalDate.parse(view.getEndDateTextField().getText());
                        return dateValue.isBefore(toDate) || dateValue.isEqual(toDate);
                    }
                };
                dateFilters.add(toDateFilter);
            } else {
                string2 = "****-**-**";
            }

            // Apply filter
            view.getSorter().setRowFilter(RowFilter.andFilter(dateFilters));
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