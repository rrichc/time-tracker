package ui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RemoveBillingButtonPane extends CustomBillingButtonPane {

    public RemoveBillingButtonPane(BillingSplitPane splitPane) {
        super(splitPane);
    }

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
                    System.out.println(selectedCategory);
                }
            }
        });
    }
}
