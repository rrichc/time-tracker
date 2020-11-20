package ui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//Represents a Client Button pane used for removing Clients
public class RemoveClientButtonPane extends CustomClientButtonPane {

    /*
     * EFFECTS: Initializes the removal button pane for clients
     */
    public RemoveClientButtonPane(ClientSplitPane splitPane) {
        super(splitPane);
    }

    /*
     * EFFECTS: Create a remove button to remove the client currently selected
     */
    @Override
    protected void createPrimaryButton() {
        JButton removeButton = new JButton("Remove");
        panel.add(new JLabel());
        panel.add(removeButton);
        removeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String selectedClient = splitPane.getListSelectedClient();
                if (selectedClient == null || selectedClient.equals("Empty") || selectedClient.equals("")) {
                    return;
                } else {
                    clientBook.removeClient(selectedClient);
                    masterTimeLog.removeTimeLog(selectedClient);
                    splitPane.updateListModel();
                }
            }
        });
    }
}
