package ui;

import model.BillingCategories;
import model.ClientBook;
import model.MasterTimeLog;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//Represents a Billing Category Button pane used for selecting or removing Billing Categories
abstract class CustomBillingButtonPane {

    protected JPanel panel;
    protected ClientBook clientBook;
    protected BillingCategories billingCategories;
    protected MasterTimeLog masterTimeLog;
    protected BillingSplitPane splitPane;

    /*
     * EFFECTS: Initializes and creates the back button and primary button depending on the child's implementation
     */
    public CustomBillingButtonPane(BillingSplitPane splitPane) {

        this.splitPane = splitPane;
        this.clientBook = splitPane.getClientBook();
        this.billingCategories = splitPane.getBillingCategories();
        this.masterTimeLog = splitPane.getMasterTimeLog();
        this.panel = new JPanel();
        createPrimaryButton();
        createBackButton();
    }

    /*
     * EFFECTS: To create either a select or remove button depending on the child's implementation
     */
    protected abstract void createPrimaryButton();

    /*
     * EFFECTS: Creates a back button the shows the billing menu
     */
    protected void createBackButton() {
        JButton backButton = new JButton("Back");
        panel.add(new JLabel());
        panel.add(backButton);
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                splitPane.showBillingMenuOptions();
                splitPane.setTabVisibility();
            }
        });
    }

    public JPanel getPanel() {
        return panel;
    }
}
