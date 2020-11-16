package ui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SelectBillingButtonPane extends CustomBillingButtonPane {

    public SelectBillingButtonPane(BillingSplitPane splitPane) {
        super(splitPane);
    }

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
                    System.out.println(selectedCategory);
                }
            }
        });
    }
}
