package ui;

import model.ClientBook;
import model.MasterTimeLog;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

abstract class CustomClientButtonPane {

    protected JPanel panel;
    protected ClientBook clientBook;
    protected MasterTimeLog masterTimeLog;
    protected ClientSplitPane splitPane;

    public CustomClientButtonPane(ClientSplitPane splitPane) {

        this.splitPane = splitPane;
        this.clientBook = splitPane.getClientBook();
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
                splitPane.showClientMenuOptions();
            }
        });
    }

    public JPanel getPanel() {
        return panel;
    }
}
