package ui;

import model.ClientBook;
import model.MasterTimeLog;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//Represents a Client Button pane used for selecting or removing Clients
abstract class CustomClientButtonPane {

    protected JPanel panel;
    protected ClientBook clientBook;
    protected MasterTimeLog masterTimeLog;
    protected ClientSplitPane splitPane;

    /*
     * EFFECTS: Initializes and creates the back button and primary button depending on the child's implementation
     */
    public CustomClientButtonPane(ClientSplitPane splitPane) {

        this.splitPane = splitPane;
        this.clientBook = splitPane.getClientBook();
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
     * EFFECTS: Creates a back button the shows the client menu
     */
    protected void createBackButton() {
        JButton backButton = new JButton("Back");
        panel.add(new JLabel());
        panel.add(backButton);
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                splitPane.showClientMenuOptions();
                splitPane.setTabVisibility();
            }
        });
    }

    public JPanel getPanel() {
        return panel;
    }
}
