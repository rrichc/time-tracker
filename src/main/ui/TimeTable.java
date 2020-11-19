package ui;

import model.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class TimeTable {
    private ClientBook clientBook;
    private BillingCategories billingCategories;
    private MasterTimeLog masterTimeLog;
    private JTable table;
    private DefaultTableModel tableModel;
    private TimeSplitPane splitPane;
    private JPanel panel;

    public TimeTable(TimeSplitPane splitPane) {
        this.splitPane = splitPane;
        this.clientBook = splitPane.getClientBook();
        this.billingCategories = splitPane.getBillingCategories();
        this.masterTimeLog = splitPane.getMasterTimeLog();
        initTable();
    }

    // https://www.tutorialspoint.com/how-to-add-a-new-row-to-jtable-with-insertrow-in-java-swing
    // https://www.tutorialspoint.com/how-can-we-disable-the-cell-editing-inside-a-jtable-in-java
    private void initTable() {
        panel = new JPanel();
        tableModel = new DefaultTableModel();
        table = new JTable(tableModel) {
            public boolean editCellAt(int row, int column, java.util.EventObject e) {
                return false;
            }
        };
        createBackButton();
        createUpdateButton();
        initColumns();
        updateTable();
    }

    private void updateTable() {
        Client currentClient = splitPane.getMenuTabs().getCurrentClient();
        BillingCategory currentCategory = splitPane.getMenuTabs().getCurrentCategory();
        ArrayList<TimeEntry> timeEntries = masterTimeLog.getTimeLogForClient(currentClient)
                .getTimeEntriesForBillingCategory(currentCategory);
        removeAllRows();
        for (TimeEntry entry : timeEntries) {
            tableModel.insertRow(tableModel.getRowCount(), new Object[] {
                    entry.getName(),
                    entry.getDescription(),
                    DateTimeParser.parseDateTimeToString(entry.getStartDateTime()),
                    DateTimeParser.parseDateTimeToString(entry.getEndDateTime()),
                    Long.toString(entry.getTimeSpentMinutes()),
                    entry.getCategory().getName(),
                    Double.toString(entry.getCategory().getRatePerHour())
            });
        }
    }

    private void removeAllRows() {
        int numRows = tableModel.getRowCount();
        for (int i = 0; i < numRows; i++) {
            tableModel.removeRow(i);
        }
    }

    private void initColumns() {
        tableModel.addColumn("Entry");
        tableModel.addColumn("Description");
        tableModel.addColumn("Start Time");
        tableModel.addColumn("End Time");
        tableModel.addColumn("Time Spent");
        tableModel.addColumn("Billing Category");
        tableModel.addColumn("Rate");
    }

    private void createBackButton() {
        JButton backButton = new JButton("Back");
        panel.add(new JLabel());
        panel.add(backButton);
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                splitPane.showTimeMenuOptions();
            }
        });
    }

    private void createUpdateButton() {
        JButton updateButton = new JButton("Update");
        panel.add(new JLabel());
        panel.add(updateButton);
        updateButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                for (int i = 0; i < tableModel.getRowCount(); i++) {
                    tableModel.removeRow(i);
                }
                updateTable();
            }
        });
    }

    public JPanel getTable() {
        panel.add(new JScrollPane(this.table));
        panel.setVisible(true);
        return panel;
    }

    public DefaultTableModel getTableModel() {
        return tableModel;
    }
}
