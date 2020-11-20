package ui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//Represents a Billing Button pane used for removing Billing Categories
public class RemoveBillingButtonPane extends CustomBillingButtonPane {

    /*
     * EFFECTS: Initializes the removal button pane for Billing Categories
     */
    public RemoveBillingButtonPane(BillingSplitPane splitPane) {
        super(splitPane);
    }

    /*
     * EFFECTS: Create a remove button to remove the billing category currently selected
     */
    @Override
    protected void createPrimaryButton() {
        JButton removeButton = new JButton("Remove");
        panel.add(new JLabel());
        panel.add(removeButton);
        removeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String selectedCategory = splitPane.getListSelectedCategory();
                if (selectedCategory == null || selectedCategory.equals("Empty") || selectedCategory.equals("")) {
                    return;
                } else {
                    billingCategories.removeBillingCategory(
                            selectedCategory, splitPane.getMenuTabs().getCurrentClient());
                    splitPane.updateListModel();
                }
            }
        });
    }
}
