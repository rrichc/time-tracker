package ui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RemoveClientButtonPane extends CustomButtonPane {

    public RemoveClientButtonPane(ClientSplitPane splitPane) {
        super(splitPane);
    }

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
                    System.out.println(selectedClient);
                }
            }
        });
    }
}
