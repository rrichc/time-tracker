package ui;

import model.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

//This class represents the input form for a billing categories
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

    /*
     * REQUIRES: labels should not be empty
     * EFFECTS: Creates an instance of BillingForm to be displayed in the splitpane
     */
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

    /*
     * MODIFIES: this
     * EFFECTS: Creates a back button to move back to the actions billing menu
     */
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

    /*
     * MODIFIES: this
     * EFFECTS: Creates a submit button
     */
    private void createSubmitButton() {
        JButton submitButton = new JButton("Submit");
        springForm.add(new JLabel());
        springForm.add(submitButton);
        submitButton.addActionListener(createActionListener());
    }

    /*
     * MODIFIES: this
     * EFFECTS: Creates a listener for the submit button to handle add and edit actions,
     *          to add user input to billing categories
     */
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
                        addActionHelper(categoryName, ratePerHour);
                        break;
                    case EDIT: //TODO: Add pop-up/checking for duplicate name
                        editActionHelper(categoryName, ratePerHour);
                        break;
                }
                splitPane.updateListModel();
            }
        };
    }

    private void editActionHelper(String categoryName, String ratePerHour) {
        try {
            billingCategories.editBillingCategory(
                    splitPane.getListSelectedCategory(), categoryName,
                    ratePerHour, splitPane.getMenuTabs().getCurrentClient());
        } catch (EmptyNameException error) {
            JOptionPane.showMessageDialog(springForm, "Please enter a name with at least 1 character.");
        } catch (NumberFormatException error) {
            JOptionPane.showMessageDialog(springForm, "Please enter a number.");
        } catch (NegativeRateException error) {
            JOptionPane.showMessageDialog(springForm, error.getMessage());
        }
    }

    private void addActionHelper(String categoryName, String ratePerHour) {
        try {
            billingCategories.createBillingCategory(
                    categoryName, ratePerHour, splitPane.getMenuTabs().getCurrentClient());
        } catch (EmptyNameException error) {
            JOptionPane.showMessageDialog(springForm, "Please enter a name with at least 1 character.");
        } catch (NumberFormatException error) {
            JOptionPane.showMessageDialog(springForm, "Please enter a number.");
        } catch (NegativeRateException error) {
            JOptionPane.showMessageDialog(springForm, error.getMessage());
        }
    }

    /*
     * EFFECTS: Creates a layout for the form panel
     */
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
