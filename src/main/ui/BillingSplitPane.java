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

//Class based off the https://docs.oracle.com/javase/tutorial/uiswing/components/splitpane.html sample project provided\
//Class represents a splitpane for the Billing tab
public class BillingSplitPane implements ListSelectionListener {
    private JList list;
    private JSplitPane splitPane;
    private ClientBook clientBook;
    private BillingCategories billingCategories;
    private MasterTimeLog masterTimeLog;
    private MenuTabs menuTabs;
    public DefaultListModel categoryNames;
    private JPanel secondPanel;
    private ActionState state;
    private JScrollPane listScrollPane;
    private String[] labels = {"Category Name: ", "Rate per hour: "};
    private String listSelectedCategory;


    /*
     * EFFECTS: Constructs the split pane for the Billing tab
     */
    public BillingSplitPane(MenuTabs menuTabs, ClientBook clientBook,
                            BillingCategories billingCategories, MasterTimeLog masterTimeLog,
                            ActionState state) {
        this.menuTabs = menuTabs;
        this.clientBook = clientBook;
        this.billingCategories = billingCategories;
        this.masterTimeLog = masterTimeLog;
        this.state = state;
        categoryNames = new DefaultListModel();

        buildCategoryList();
        createSecondPanel();
        createSplitPane();

    }

    /*
     * MODIFIES: this
     * EFFECTS: Adds the names of categories to be displayed in the JList, or empty if billingcateogies is empty
     */
    private void addNamesToListModel() {
        //Create the list of category names and put it in a scroll pane.
        ArrayList<BillingCategory> categoriesForClient = billingCategories
                .getBillingCategoriesForClient(menuTabs.getCurrentClient());
        if (categoriesForClient.isEmpty()) {
            this.categoryNames.addElement("Empty");
        } else {
            listSelectedCategory = categoriesForClient.get(0).getName();
            for (BillingCategory category : categoriesForClient) {
                this.categoryNames.addElement(category.getName());
            }
        }
    }

    /*
     * MODIFIES: this
     * EFFECTS: Builds the JList and the scroll pane for it
     */
    private void buildCategoryList() {
        addNamesToListModel();
        list = new JList(this.categoryNames);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setSelectedIndex(0);
        list.addListSelectionListener(this);
        listScrollPane = new JScrollPane(list);
    }

    /*
     * MODIFIES: this
     * EFFECTS: Creates the second panel to be displayed in the split pane depending on desired user action
     */
    private void createSecondPanel() {
        switch (state) {
            case SELECT:
                secondPanel = new SelectBillingButtonPane(this).getPanel();
                break;
            case ADD:
                secondPanel = new BillingForm(this.labels, ActionState.ADD, this).getSpringForm();
                break;
            case EDIT:
                secondPanel = new BillingForm(labels, ActionState.EDIT, this).getSpringForm();
                break;
            case REMOVE:
                secondPanel = new RemoveBillingButtonPane(this).getPanel();
                break;
        }
    }

    /*
     * MODIFIES: this
     * EFFECTS: Creates the splitpane with the JList in the scrollpane and second panel
     */
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

    /*
     * MODIFIES: this
     * EFFECTS: Determines what item in the JList is currently selected
     */
    public void valueChanged(ListSelectionEvent e) {
        if (!e.getValueIsAdjusting()) { //This line prevents double events
            JList list = (JList)e.getSource();
            if (list.getSelectedValue() != null) {
                listSelectedCategory =  list.getSelectedValue().toString();
            }
        }
    }

    public JSplitPane getBillingSplitPane() {
        return splitPane;
    }

    public String getListSelectedCategory() {
        return listSelectedCategory;
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

    /*
     * MODIFIES: this
     * EFFECTS: Updates the list model to reflect the current state of BillingCategories
     */
    public void updateListModel() {
        this.categoryNames.clear();
        addNamesToListModel();
    }

    /*
     * EFFECTS: Switches back to displaying the menu options
     */
    public void showBillingMenuOptions() {
        menuTabs.displayBillingMenuOptions();
    }

    /*
     * MODIFIES: menuTabs
     * EFFECTS: Sets the current billing category that has been selected by the user
     */
    public void setCurrentCategory(String currentCategory) {
        menuTabs.setCurrentCategory(billingCategories
                .getABillingCategory(currentCategory, menuTabs.getCurrentClient()));
    }

    public MenuTabs getMenuTabs() {
        return menuTabs;
    }

    /*
     * EFFECTS: Changes the visibility of the tabs
     */
    public void setTabVisibility() {
        menuTabs.setTabVisibility();
    }
}


