package ui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//Represents a selection button pane for billing categories
public class SelectBillingButtonPane extends CustomBillingButtonPane {

    /*
     * EFFECTS: Initializes the selection button pane for billing categories
     */
    public SelectBillingButtonPane(BillingSplitPane splitPane) {
        super(splitPane);
    }

    /*
     * EFFECTS: Create a select button to select the active client being worked on
     */
    @Override
    protected void createPrimaryButton() {
        JButton selectButton = new JButton("Select");
        panel.add(new JLabel());
        panel.add(selectButton);
        selectButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String selectedCategory = splitPane.getListSelectedCategory();
                if (selectedCategory == null || selectedCategory.equals("Empty") || selectedCategory.equals("")) {
                    return;
                } else {
                    splitPane.setCurrentCategory(selectedCategory);
                    UserInterfaceSound.playSelectSound();
                }
            }
        });
    }
}
