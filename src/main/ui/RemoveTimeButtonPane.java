package ui;

import model.BillingCategory;
import model.Client;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RemoveTimeButtonPane extends CustomTimeButtonPane {

    public RemoveTimeButtonPane(TimeSplitPane splitPane) {
        super(splitPane);
    }

    @Override
    protected void createPrimaryButton() {
        JButton removeButton = new JButton("Remove");
        panel.add(new JLabel());
        panel.add(removeButton);
        removeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String selectedTimeEntry = splitPane.getListSelectedTimeEntry();
                if (selectedTimeEntry == null || selectedTimeEntry.equals("Empty") || selectedTimeEntry.equals("")) {
                    return;
                } else {
                    Client currentClient = splitPane.getMenuTabs().getCurrentClient();
                    BillingCategory currentCategory = splitPane.getMenuTabs().getCurrentCategory();
                    masterTimeLog.getTimeLogForClient(currentClient)
                            .removeTimeEntry(selectedTimeEntry, currentCategory);
                    splitPane.updateListModel();
                    System.out.println(selectedTimeEntry);
                }
            }
        });
    }
}
