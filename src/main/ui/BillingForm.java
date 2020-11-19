package ui;

import model.BillingCategories;
import model.ClientBook;
import model.MasterTimeLog;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

public class BillingForm {
    private JPanel springForm;
    private ActionState state;
    private ClientBook clientBook;
    private BillingCategories billingCategories;
    private MasterTimeLog masterTimeLog;
    private BillingSplitPane splitPane;
    private int labelsLength;
    private String[] labels;
    private JTextField[] textField;

    public BillingForm(String[] labels, ActionState state, BillingSplitPane splitPane) {
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
                splitPane.showBillingMenuOptions();
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
                String categoryName = userInput.get("Category Name: ");
                String ratePerHour = userInput.get("Rate per hour: ");
                switch (state) {
                    case ADD: //TODO: Add pop-up/checking for duplicate name
                        billingCategories.createBillingCategory(
                                categoryName, ratePerHour, splitPane.getMenuTabs().getCurrentClient());
                        break;
                    case EDIT: //TODO: Add pop-up/checking for duplicate name
                        billingCategories.editBillingCategory(
                                splitPane.getListSelectedCategory(), categoryName,
                                ratePerHour, splitPane.getMenuTabs().getCurrentClient());
                        break;
                }
                splitPane.updateListModel();
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
