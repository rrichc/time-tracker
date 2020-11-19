package ui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SelectClientButtonPane extends CustomClientButtonPane {

    public SelectClientButtonPane(ClientSplitPane splitPane) {
        super(splitPane);
    }

    @Override
    protected void createPrimaryButton() {
        JButton selectButton = new JButton("Select");
        panel.add(new JLabel());
        panel.add(selectButton);
        selectButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String selectedClient = splitPane.getListSelectedClient();
                if (selectedClient == null || selectedClient.equals("Empty") || selectedClient.equals("")) {
                    return;
                } else {
                    splitPane.setCurrentClient(selectedClient);
                    UserInterfaceSound.playSelectSound();
                }
            }
        });
    }
}
