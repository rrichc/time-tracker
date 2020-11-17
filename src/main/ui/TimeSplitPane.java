/*
 * Copyright (c) 1995, 2008, Oracle and/or its affiliates. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *   - Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *
 *   - Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *
 *   - Neither the name of Oracle or the names of its
 *     contributors may be used to endorse or promote products derived
 *     from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */ 

package ui;

import model.*;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.util.ArrayList;

//SplitPaneDemo itself is not a visible component.
//Class based off the https://docs.oracle.com/javase/tutorial/uiswing/components/splitpane.html sample project provided
public class TimeSplitPane implements ListSelectionListener {
    private JList list;
    private JSplitPane splitPane;
    private ClientBook clientBook;
    private BillingCategories billingCategories;
    private MasterTimeLog masterTimeLog;
    private MenuTabs menuTabs;
    public DefaultListModel entryNames;
    private JPanel secondPanel;
    private ActionState state;
    private JScrollPane listScrollPane;
    private String[] labels = {"Time Entry Name: ", "Description: ",
            "Start date & 24H time as yyyy-MM-dd HH:mm : ", "End date & 24H time as yyyy-MM-dd HH:mm : "};
    private String listSelectedTimeEntry;


    public TimeSplitPane(MenuTabs menuTabs, ClientBook clientBook, BillingCategories billingCategories, MasterTimeLog masterTimeLog, ActionState state) {
        this.menuTabs = menuTabs;
        this.clientBook = clientBook;
        this.billingCategories = billingCategories;
        this.masterTimeLog = masterTimeLog;
        this.state = state;
        this.entryNames = new DefaultListModel();

        buildTimeEntryList();
        createSecondPanel();
        createSplitPane();

    }

    private void addNamesToListModel() {
        //Create the list of category names and put it in a scroll pane.
        Client currentClient = menuTabs.getCurrentClient();
        BillingCategory currentCategory = menuTabs.getCurrentCategory();
        ArrayList<TimeEntry> timeEntriesForCategory = masterTimeLog.getTimeLogForClient(currentClient)
                .getTimeEntriesForBillingCategory(currentCategory);
        if (timeEntriesForCategory.isEmpty()) {
            this.entryNames.addElement("Empty");
        } else {
            listSelectedTimeEntry = timeEntriesForCategory.get(0).getName();
            for (TimeEntry entry : timeEntriesForCategory) {
                this.entryNames.addElement(entry.getName());
            }
        }
    }

    private void buildTimeEntryList() {
        addNamesToListModel();
        list = new JList(this.entryNames);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setSelectedIndex(0);
        list.addListSelectionListener(this);
        listScrollPane = new JScrollPane(list);
    }

    private void createSecondPanel() {
        switch (state) {
            case SELECT:
                secondPanel = new TimeTable(this).getTable();
                break;
            case ADD:
                secondPanel = new TimeForm(this.labels, ActionState.ADD, this).getSpringForm();
                break;
            case EDIT:
                secondPanel = new TimeForm(labels, ActionState.EDIT, this).getSpringForm();
                break;
            case REMOVE:
                secondPanel = new RemoveTimeButtonPane(this).getPanel();
                break;
        }
    }

    private void createSplitPane() {
        //Create a split pane with the two scroll panes in it.
        splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
                listScrollPane, secondPanel);
        splitPane.setOneTouchExpandable(true);
        splitPane.setDividerLocation(150);

        //Provide minimum sizes for the two components in the split pane.
        Dimension minimumSize = new Dimension(100, 50);
        listScrollPane.setMinimumSize(minimumSize);
        secondPanel.setMinimumSize(minimumSize);

        //Provide a preferred size for the split pane.
        splitPane.setPreferredSize(new Dimension(400, 200));
    }
    
    //Listens to the list
    public void valueChanged(ListSelectionEvent e) {
        if (!e.getValueIsAdjusting()) { //This line prevents double events
            JList list = (JList)e.getSource();
            if (list.getSelectedValue() != null) {
                listSelectedTimeEntry =  list.getSelectedValue().toString();
                System.out.println(listSelectedTimeEntry);
            }
        }
    }

    public JSplitPane getTimeSplitPane() {
        return splitPane;
    }

    public String getListSelectedTimeEntry() {
        return listSelectedTimeEntry;
    }

    public ClientBook getClientBook() {
        return clientBook;
    }

    public BillingCategories getBillingCategories() {
        return billingCategories;
    }

    public MasterTimeLog getMasterTimeLog() {
        return masterTimeLog;
    }

    public void updateModels() {
        this.entryNames.clear();
        addNamesToListModel();
        //TODO: Figure out how to update the TimeTable from here?
    }

    public void showTimeMenuOptions() {
        menuTabs.displayTimeMenuOptions();
    }

//    public void setCurrentCategory(String currentCategory) {
//        menuTabs.setCurrentCategory(billingCategories
//                .getABillingCategory(currentCategory, menuTabs.getCurrentClient()));
//    }

    public MenuTabs getMenuTabs() {
        return menuTabs;
    }
}


