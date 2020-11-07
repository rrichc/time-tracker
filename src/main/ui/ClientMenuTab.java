package ui;

import javax.swing.*;
import java.awt.*;

public class ClientMenuTab extends JPanel {
    static final int extraWindowWidth = 100;
    protected JPanel tab;

    public ClientMenuTab() {
        tab = new JPanel() {
            //Make the panel wider than it really needs, so
            //the window's wide enough for the tabs to stay
            //in one row.
            public Dimension getPreferredSize() {
                Dimension size = super.getPreferredSize();
                size.width += extraWindowWidth;
                return size;
            }
        };
        tab.add(new JButton("Button 1"));
        tab.add(new JButton("Button 2"));
        tab.add(new JButton("Button 3"));
        tab.setLayout(new BoxLayout(tab, BoxLayout.PAGE_AXIS));
    }

    public JPanel getTab() {
        return tab;
    }
}
