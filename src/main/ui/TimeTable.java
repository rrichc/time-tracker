package ui;

import model.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

//Represents the time table that displays all the time entries for a given category for a client
public class TimeTable {
    private MasterTimeLog masterTimeLog;
    private JTable table;
    private DefaultTableModel tableModel;
    private TimeSplitPane splitPane;
    private JPanel panel;

    /*
     * EFFECTS: Creates a TimeTable object and initializes the table
     */
    public TimeTable(TimeSplitPane splitPane) {
        this.splitPane = splitPane;
        this.masterTimeLog = splitPane.getMasterTimeLog();
        initTable();
    }

    // https://www.tutorialspoint.com/how-to-add-a-new-row-to-jtable-with-insertrow-in-java-swing
    // https://www.tutorialspoint.com/how-can-we-disable-the-cell-editing-inside-a-jtable-in-java
    /*
     * MODIFIES: this
     * EFFECTS: initializes the table with a new JPanel and TableModel
     */
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

    /*
     * MODIFIES: this
     * EFFECTS: clears and updates the table based on the current client, billing categories, and master time log
     */
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

    /*
     * MODIFIES: this
     * EFFECTS: removes all rows from the DefaultTableModel
     */
    private void removeAllRows() {
        int numRows = tableModel.getRowCount();
        for (int i = 0; i < numRows; i++) {
            tableModel.removeRow(i);
        }
    }

    /*
     * MODIFIES: this
     * EFFECTS: Adds columns to the table
     */
    private void initColumns() {
        tableModel.addColumn("Entry");
        tableModel.addColumn("Description");
        tableModel.addColumn("Start Time");
        tableModel.addColumn("End Time");
        tableModel.addColumn("Time Spent");
        tableModel.addColumn("Billing Category");
        tableModel.addColumn("Rate");
    }

    /*
     * MODIFIES: this
     * EFFECTS: Creates a back button to go back to the time entry menu
     */
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

    /*
     * MODIFIES: this
     * EFFECTS: Creates an update button to refresh the table
     */
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

    /*
     * MODIFIES: this
     * EFFECTS: Returns the table in a new JScrollPane
     */
    public JPanel getTable() {
        panel.add(new JScrollPane(this.table));
        panel.setVisible(true);
        return panel;
    }

    public DefaultTableModel getTableModel() {
        return tableModel;
    }
}
