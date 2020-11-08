package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

//Class modified from https://docs.oracle.com/javase/tutorial/uiswing/components/tabbedpane.html
public class ClientMenuOptions extends JPanel {
    static final int extraWindowWidth = 100;
    MenuTabs menuTabs;
    protected JPanel clientMenuOptions;

    //actionPerformed syntax from https://stackoverflow.com/questions/5911565/how-to-add-multiple-actionlisteners-for-multiple-buttons-in-java-swing
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

    private void initMenuButtons() {
        JButton selectClientButton = new JButton(new AbstractAction("Select client") {
            @Override
            public void actionPerformed(ActionEvent e) {
                // switch panels
                menuTabs.actionPerformed(e);
            }
        });
        clientMenuOptions.add(selectClientButton);
        clientMenuOptions.add(new JButton("Add client"));
        clientMenuOptions.add(new JButton("Edit client"));
    }

    public JPanel getClientMenuOptions() {
        return clientMenuOptions;
    }
}
