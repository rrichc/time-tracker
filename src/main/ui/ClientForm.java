package ui;

import model.ClientBook;
import model.MasterTimeLog;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

//This class represents the input form for clients
public class ClientForm {
    private JPanel springForm;
    private ActionState state;
    private ClientBook clientBook;
    private MasterTimeLog masterTimeLog;
    private ClientSplitPane splitPane;
    private int labelsLength;
    private String[] labels;
    private JTextField[] textField;

    /*
     * REQUIRES: labels should not be empty
     * EFFECTS: Creates an instance of ClientForm to be displayed in the splitpane
     */
    public ClientForm(String[] labels, ActionState state, ClientSplitPane splitPane) {
        this.state = state;
        this.splitPane = splitPane;
        this.clientBook = splitPane.getClientBook();
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
                splitPane.showClientMenuOptions();
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
     *          to add user input to client books
     */
    private ActionListener createActionListener() {
        return new ActionListener() {
            HashMap<String, String> userInput = new HashMap<>();
            public void actionPerformed(ActionEvent e) {
                for (int i = 0; i < labelsLength; i++) {
                    userInput.put(labels[i], textField[i].getText());
                }
                String clientName = userInput.get("Client Name: ");
                switch (state) {
                    case ADD: //TODO: Add pop-up/checking for duplicate name
                        clientBook.createClient(clientName);
                        masterTimeLog.createTimeLog(clientBook.getAClient(clientName));
                        break;
                    case EDIT: //TODO: Add pop-up/checking for duplicate name
                        clientBook.editClient(splitPane.getListSelectedClient(), clientName);
                        break;
                }
                splitPane.updateListModel();
            }
        };
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
