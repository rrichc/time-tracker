package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

//Class modified from https://docs.oracle.com/javase/tutorial/uiswing/components/tabbedpane.html
public class TimeMenuOptions {
    static final int extraWindowWidth = 100;
    private MenuTabs menuTabs;
    protected JPanel timeMenuOptions;

    //actionPerformed syntax from https://stackoverflow.com/questions/5911565/how-to-add-multiple-actionlisteners-for-multiple-buttons-in-java-swing
    public TimeMenuOptions(MenuTabs menuTabs) {
        this.menuTabs = menuTabs;
        timeMenuOptions = new JPanel() {
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
        timeMenuOptions.setLayout(new BoxLayout(timeMenuOptions, BoxLayout.PAGE_AXIS));
    }

    private void initMenuButtons() {
        JButton selectTimeButton = constructButton("View time entries");
        timeMenuOptions.add(selectTimeButton);
        JButton addTimeButton = constructButton("Add time entry");
        timeMenuOptions.add(addTimeButton);
        JButton editTimeButton = constructButton("Edit time entry");
        timeMenuOptions.add(editTimeButton);
        JButton removeTimeButton = constructButton("Remove time entry");
        timeMenuOptions.add(removeTimeButton);
    }

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

    public JPanel getTimeMenuOptions() {
        return timeMenuOptions;
    }
}
