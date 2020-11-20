package ui;

import model.BillingCategories;
import model.ClientBook;
import model.MasterTimeLog;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//Represents a Time Entry Button pane used for selecting or removing Time Entries
abstract class CustomTimeButtonPane {

    protected JPanel panel;
    protected ClientBook clientBook;
    protected BillingCategories billingCategories;
    protected MasterTimeLog masterTimeLog;
    protected TimeSplitPane splitPane;

    /*
     * EFFECTS: Initializes and creates the back button and primary button depending on the child's implementation
     */
    public CustomTimeButtonPane(TimeSplitPane splitPane) {

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
     * EFFECTS: Creates a back button the shows the time entry menu
     */
    protected void createBackButton() {
        JButton backButton = new JButton("Back");
        panel.add(new JLabel());
        panel.add(backButton);
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                splitPane.showTimeMenuOptions();
            }
        });
    }

    public JPanel getPanel() {
        return panel;
    }
}
