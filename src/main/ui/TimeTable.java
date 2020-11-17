package ui;

import model.BillingCategories;
import model.ClientBook;
import model.MasterTimeLog;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class TimeTable {
    private ClientBook clientBook;
    private BillingCategories billingCategories;
    private MasterTimeLog masterTimeLog;
    private JTable table;

    public TimeTable(TimeSplitPane splitPane) {
        this.clientBook = splitPane.getClientBook();
        this.billingCategories = splitPane.getBillingCategories();
        this.masterTimeLog = splitPane.getMasterTimeLog();
        initTable();
    }

    // https://www.tutorialspoint.com/how-to-add-a-new-row-to-jtable-with-insertrow-in-java-swing
    // https://www.tutorialspoint.com/how-can-we-disable-the-cell-editing-inside-a-jtable-in-java
    private void initTable() {
        DefaultTableModel tableModel = new DefaultTableModel();
        table = new JTable(tableModel) {
            public boolean editCellAt(int row, int column, java.util.EventObject e) {
                return false;
            }
        };
        tableModel.addColumn("Entry");
        tableModel.addColumn("Description");
        tableModel.insertRow(tableModel.getRowCount(), new Object[] { "Entry1", "Desc" });
        tableModel.insertRow(tableModel.getRowCount(), new Object[] { "Entry2", "Desc2" });

    }

    public JPanel getTable() {
        JPanel p = new JPanel();
        p.add(new JScrollPane(this.table));
        //p.setSize(550, 350);
        p.setVisible(true);
        return p;
    }

}
