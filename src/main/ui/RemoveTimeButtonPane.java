package ui;

import model.BillingCategory;
import model.Client;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//Represents a Time Entry Button pane used for removing time entries
public class RemoveTimeButtonPane extends CustomTimeButtonPane {

    /*
     * EFFECTS: Initializes the removal button pane for time entries
     */
    public RemoveTimeButtonPane(TimeSplitPane splitPane) {
        super(splitPane);
    }

    /*
     * EFFECTS: Create a remove button to remove the time entry currently selected
     */
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
                    splitPane.updateModels();
                }
            }
        });
    }
}
