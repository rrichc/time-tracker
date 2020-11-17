package ui;

import model.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

public class TimeForm {
    private JPanel springForm;
    private ActionState state;
    private ClientBook clientBook;
    private BillingCategories billingCategories;
    private MasterTimeLog masterTimeLog;
    private TimeSplitPane splitPane;
    private int labelsLength;
    private String[] labels;
    private JTextField[] textField;

    public TimeForm(String[] labels, ActionState state, TimeSplitPane splitPane) {
        this.state = state;
        this.splitPane = splitPane;
        this.clientBook = splitPane.getClientBook();
        this.billingCategories = splitPane.getBillingCategories();
        this.masterTimeLog = splitPane.getMasterTimeLog();
        this.labels = labels;
        this.labelsLength = labels.length;
        this.textField = new JTextField[labels.length];
        //Create and populate the panel.
        this.springForm = new JPanel(new SpringLayout());
        for (int i = 0; i < labelsLength; i++) {
            JLabel l = new JLabel(labels[i], JLabel.TRAILING);
            springForm.add(l);
            textField[i] = new JTextField(10);
            l.setLabelFor(textField[i]);
            springForm.add(textField[i]);
        }

        createSubmitButton();
        createBackButton();
        layoutPanel();
    }

    private void createBackButton() {
        JButton backButton = new JButton("Back");
        springForm.add(new JLabel());
        springForm.add(backButton);
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                splitPane.showTimeMenuOptions();
            }
        });
    }

    private void createSubmitButton() {
        JButton submitButton = new JButton("Submit");
        springForm.add(new JLabel());
        springForm.add(submitButton);
        submitButton.addActionListener(createActionListener());
    }

    //TODO: Add Exception handling for empty strings and number format exceptions
    private ActionListener createActionListener() {
        return new ActionListener() {
            HashMap<String, String> userInput = new HashMap<>();
            public void actionPerformed(ActionEvent e) {
                for (int i = 0; i < labelsLength; i++) {
                    userInput.put(labels[i], textField[i].getText());
                }
                String entryName = userInput.get("Time Entry Name: ");
                String desc = userInput.get("Description: ");
                String startDateTime = userInput.get("Start date & 24H time as yyyy-MM-dd HH:mm : ");
                String endDateTime = userInput.get("End date & 24H time as yyyy-MM-dd HH:mm : ");
                Client currentClient = splitPane.getMenuTabs().getCurrentClient();
                BillingCategory currentCategory = splitPane.getMenuTabs().getCurrentCategory();

                switch (state) {
                    case ADD: //TODO: Add pop-up/checking for duplicate name
                        masterTimeLog.getTimeLogForClient(currentClient)
                                .createTimeEntry(entryName, desc, startDateTime, endDateTime, currentCategory);
                        System.out.println(entryName);
                        break;
                    case EDIT: //TODO: Add pop-up/checking for duplicate name
                        masterTimeLog.getTimeLogForClient(currentClient)
                                .editTimeEntry(
                                        splitPane.getListSelectedTimeEntry(), entryName, desc, startDateTime, endDateTime, currentCategory);
                        System.out.println(entryName);
                        break;
                }
                splitPane.updateModels();
            }
        };
    }

    private void layoutPanel() {
        //Lay out the panel.
        SpringUtilities.makeCompactGrid(springForm,
                labelsLength + 1, 2, //rows, cols
                7, 7,        //initX, initY
                7, 7);       //xPad, yPad
    }

    public JPanel getSpringForm() {
        return springForm;
    }
}
