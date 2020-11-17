package ui;

import model.BillingCategories;
import model.ClientBook;
import model.MasterTimeLog;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

abstract class CustomBillingButtonPane {

    protected JPanel panel;
    protected ClientBook clientBook;
    protected BillingCategories billingCategories;
    protected MasterTimeLog masterTimeLog;
    protected BillingSplitPane splitPane;

    public CustomBillingButtonPane(BillingSplitPane splitPane) {

        this.splitPane = splitPane;
        this.clientBook = splitPane.getClientBook();
        this.billingCategories = splitPane.getBillingCategories();
        this.masterTimeLog = splitPane.getMasterTimeLog();
        this.panel = new JPanel();
        createPrimaryButton();
        createBackButton();
    }

    protected abstract void createPrimaryButton();

    protected void createBackButton() {
        JButton backButton = new JButton("Back");
        panel.add(new JLabel());
        panel.add(backButton);
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                splitPane.showBillingMenuOptions();
            }
        });
    }

    public JPanel getPanel() {
        return panel;
    }
}