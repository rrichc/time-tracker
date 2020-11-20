package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

//Class modified from https://docs.oracle.com/javase/tutorial/uiswing/components/tabbedpane.html
//Represents the menu options for a client
public class ClientMenuOptions {
    static final int extraWindowWidth = 100;
    private MenuTabs menuTabs;
    protected JPanel clientMenuOptions;

    //actionPerformed syntax from https://stackoverflow.com/questions/5911565/how-to-add-multiple-actionlisteners-for-multiple-buttons-in-java-swing
    /*
     * EFFECTS: Creates the client menu options panel
     */
    public ClientMenuOptions(MenuTabs menuTabs) {
        this.menuTabs = menuTabs;
        clientMenuOptions = new JPanel() {
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
        clientMenuOptions.setLayout(new BoxLayout(clientMenuOptions, BoxLayout.PAGE_AXIS));
    }

    /*
     * MODIFIES: this
     * EFFECTS: Initializes all the buttons needed for the menu
     */
    private void initMenuButtons() {
        JButton selectClientButton = constructButton("Select client");
        clientMenuOptions.add(selectClientButton);
        JButton addClientButton = constructButton("Add client");
        clientMenuOptions.add(addClientButton);
        JButton editClientButton = constructButton("Edit client");
        clientMenuOptions.add(editClientButton);
        JButton removeClientButton = constructButton("Remove client");
        clientMenuOptions.add(removeClientButton);
        JButton saveButton = constructButton("Save");
        clientMenuOptions.add(saveButton);
        JButton loadButton = constructButton("Load save");
        clientMenuOptions.add(loadButton);
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

    public JPanel getClientMenuOptions() {
        return clientMenuOptions;
    }
}
