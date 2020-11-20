package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

//Class modified from https://docs.oracle.com/javase/tutorial/uiswing/components/tabbedpane.html
//Represents the menu options for a billing category
public class BillingMenuOptions {
    static final int extraWindowWidth = 100;
    private MenuTabs menuTabs;
    protected JPanel billingMenuOptions;

    //actionPerformed syntax from https://stackoverflow.com/questions/5911565/how-to-add-multiple-actionlisteners-for-multiple-buttons-in-java-swing
    /*
     * EFFECTS: Creates the billing menu options panel
     */
    public BillingMenuOptions(MenuTabs menuTabs) {
        this.menuTabs = menuTabs;
        billingMenuOptions = new JPanel() {
            //Make the panel wider than it really needs, so
            //the window's wide enough for the tabs to stay
            //in one row.
            public Dimension getPreferredSize() {
                Dimension size = super.getPreferredSize();
                size.width += extraWindowWidth;
                return size;
            }
        };
        initMenuButtons();
        billingMenuOptions.setLayout(new BoxLayout(billingMenuOptions, BoxLayout.PAGE_AXIS));
    }

    /*
     * MODIFIES: this
     * EFFECTS: Initializes all the buttons needed for the menu
     */
    private void initMenuButtons() {
        JButton selectBillingButton = constructButton("Select billing");
        billingMenuOptions.add(selectBillingButton);
        JButton addBillingButton = constructButton("Add billing");
        billingMenuOptions.add(addBillingButton);
        JButton editBillingButton = constructButton("Edit billing");
        billingMenuOptions.add(editBillingButton);
        JButton removeBillingButton = constructButton("Remove billing");
        billingMenuOptions.add(removeBillingButton);
    }

    /*
     * EFFECTS: Constructs the buttons for each action
     */
    public JButton constructButton(String actionName) {
        JButton button = new JButton(new AbstractAction(actionName) {
            @Override
            public void actionPerformed(ActionEvent e) {
                // switch panels
                menuTabs.actionPerformed(e);
            }
        });
        return button;
    }

    public JPanel getBillingMenuOptions() {
        return billingMenuOptions;
    }
}
