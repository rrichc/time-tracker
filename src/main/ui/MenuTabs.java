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

/*
 * Class modified from https://docs.oracle.com/javase/tutorial/uiswing/components/tabbedpane.html
 */

import model.*;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;

//MenuTabs manages the display of all Client tab, Billing tab, Time tab
public class MenuTabs implements ActionListener {
    static final int extraWindowWidth = 100;
    CardLayout cardLayout;
    JTabbedPane tabbedPane;

    //Client UI fields
    static final String CLIENTMENU = "Client Menu";
    JPanel clientMenuTab;
    ClientSplitPane selectClientSplitPane;
    ClientSplitPane addClientSplitPane;
    ClientSplitPane editClientSplitPane;
    ClientSplitPane removeClientSplitPane;
    JPanel clientMainPanel;

    //Billing UI fields
    static final String BILLINGMENU = "Billing Menu";
    JPanel billingMenuTab;
    BillingSplitPane selectBillingSplitPane;
    BillingSplitPane addBillingSplitPane;
    BillingSplitPane editBillingSplitPane;
    BillingSplitPane removeBillingSplitPane;
    JPanel billingMainPanel;

    //Time UI fields
    static final String TIMEMENU = "Time Menu";
    JPanel timeMenuTab;
    TimeSplitPane selectTimeSplitPane;
    TimeSplitPane addTimeSplitPane;
    TimeSplitPane editTimeSplitPane;
    TimeSplitPane removeTimeSplitPane;
    JPanel timeMainPanel;

    //Tracks current select client and category
    private Client currentClient;
    private BillingCategory currentCategory;

    public static final String CLIENT_JSON_STORE = "./data/clientBook.json";
    public static final String BILLING_JSON_STORE = "./data/billingCategories.json";
    public static final String TIME_JSON_STORE = "./data/masterTimeLog.json";
    private MasterTimeLog masterTimeLog;
    private BillingCategories billingCategories;
    private ClientBook clientBook;
    private JsonReader jsonReader;
    private JsonWriter jsonWriter;

    /*
     * EFFECTS: Constructs the object
     */
    public MenuTabs() {
        //Empty constructor
    }

    /*
     * MODIFIES: this
     * EFFECTS: Adds each menu tab to the Tab Pane
     */
    public void addMenuTabsToPane(Container pane) {
        init();
        tabbedPane = new JTabbedPane();
        cardLayout = new CardLayout();
        clientPanelSetUp();
        billingPanelSetUp();
        timePanelSetUp();

        tabbedPane.addTab(CLIENTMENU, clientMainPanel);
        tabbedPane.addTab(BILLINGMENU, billingMainPanel);
        tabbedPane.addTab(TIMEMENU, timeMainPanel);

        tabbedPane.setEnabledAt(1, false);
        tabbedPane.setEnabledAt(2, false);

        pane.add(tabbedPane, BorderLayout.CENTER);
    }


    /*
     * MODIFIES: this
     * EFFECTS: Initializes dependencies needed by the Client, Billing, and Time Entry menus
     */
    private void init() {
        this.masterTimeLog = new MasterTimeLog();
        this.billingCategories = new BillingCategories();
        this.clientBook = new ClientBook();
        this.jsonReader = new JsonReader();
        this.jsonWriter = new JsonWriter();

        initMenuOptions();
        initClientSplitPanes();
        initBillingSplitPanes();
        initTimeSplitPanes();
    }

    /*
     * MODIFIES: this
     * EFFECTS: Initializes the menu options for Client, Billing, Time
     */
    private void initMenuOptions() {
        clientMenuTab = new ClientMenuOptions(this).getClientMenuOptions();
        billingMenuTab = new BillingMenuOptions(this).getBillingMenuOptions();
        timeMenuTab = new TimeMenuOptions(this).getTimeMenuOptions();
    }

    /*
     * MODIFIES: this
     * EFFECTS: Creates the client split panes to be displayed in the client tab
     */
    private void initClientSplitPanes() {
        selectClientSplitPane = new ClientSplitPane(this,
                this.clientBook, this.masterTimeLog, ActionState.SELECT);
        addClientSplitPane = new ClientSplitPane(this,
                this.clientBook, this.masterTimeLog, ActionState.ADD);
        editClientSplitPane = new ClientSplitPane(this,
                this.clientBook, this.masterTimeLog, ActionState.EDIT);
        removeClientSplitPane = new ClientSplitPane(this,
                this.clientBook, this.masterTimeLog, ActionState.REMOVE);
    }

    /*
     * MODIFIES: this
     * EFFECTS: Sets up the client panels and adds them to a Card Layout to be able to be switched in and out on demand
     */
    private void clientPanelSetUp() {
        clientMainPanel = new JPanel(cardLayout);
        clientMainPanel.add(clientMenuTab, "clientMenuOptions");
        clientMainPanel.add(selectClientSplitPane.getClientSplitPane(), "selectClientSplitPane");
        clientMainPanel.add(addClientSplitPane.getClientSplitPane(), "addClientSplitPane");
        clientMainPanel.add(editClientSplitPane.getClientSplitPane(), "editClientSplitPane");
        clientMainPanel.add(removeClientSplitPane.getClientSplitPane(), "removeClientSplitPane");
    }

    /*
     * MODIFIES: this
     * EFFECTS: Creates the billing split panes to be displayed in the billing tab
     */
    private void initBillingSplitPanes() {
        selectBillingSplitPane = new BillingSplitPane(this,
                this.clientBook, this.billingCategories, this.masterTimeLog, ActionState.SELECT);
        addBillingSplitPane = new BillingSplitPane(this,
                this.clientBook, this.billingCategories, this.masterTimeLog, ActionState.ADD);
        editBillingSplitPane = new BillingSplitPane(this,
                this.clientBook, this.billingCategories, this.masterTimeLog, ActionState.EDIT);
        removeBillingSplitPane = new BillingSplitPane(this,
                this.clientBook, this.billingCategories, this.masterTimeLog, ActionState.REMOVE);
    }

    /*
     * MODIFIES: this
     * EFFECTS: Sets up the billing panels and adds them to a Card Layout to be able to be switched in and out on demand
     */
    private void billingPanelSetUp() {
        billingMainPanel = new JPanel(cardLayout);
        billingMainPanel.add(billingMenuTab, "billingMenuOptions");
        billingMainPanel.add(selectBillingSplitPane.getBillingSplitPane(), "selectBillingSplitPane");
        billingMainPanel.add(addBillingSplitPane.getBillingSplitPane(), "addBillingSplitPane");
        billingMainPanel.add(editBillingSplitPane.getBillingSplitPane(), "editBillingSplitPane");
        billingMainPanel.add(removeBillingSplitPane.getBillingSplitPane(), "removeBillingSplitPane");
    }

    /*
     * MODIFIES: this
     * EFFECTS: Creates the time split panes to be displayed in the time tab
     */
    private void initTimeSplitPanes() {
        selectTimeSplitPane = new TimeSplitPane(this,
                this.clientBook, this.billingCategories, this.masterTimeLog, ActionState.SELECT);
        addTimeSplitPane = new TimeSplitPane(this,
                this.clientBook, this.billingCategories, this.masterTimeLog, ActionState.ADD);
        editTimeSplitPane = new TimeSplitPane(this,
                this.clientBook, this.billingCategories, this.masterTimeLog, ActionState.EDIT);
        removeTimeSplitPane = new TimeSplitPane(this,
                this.clientBook, this.billingCategories, this.masterTimeLog, ActionState.REMOVE);
    }


    /*
     * MODIFIES: this
     * EFFECTS: Sets up the time panels and adds them to a Card Layout to be able to be switched in and out on demand
     */
    private void timePanelSetUp() {
        timeMainPanel = new JPanel(cardLayout);
        timeMainPanel.add(timeMenuTab, "timeMenuOptions");
        timeMainPanel.add(selectTimeSplitPane.getTimeSplitPane(), "selectTimeSplitPane");
        timeMainPanel.add(addTimeSplitPane.getTimeSplitPane(), "addTimeSplitPane");
        timeMainPanel.add(editTimeSplitPane.getTimeSplitPane(), "editTimeSplitPane");
        timeMainPanel.add(removeTimeSplitPane.getTimeSplitPane(), "removeTimeSplitPane");
    }

    /*
     * MODIFIES: this
     * EFFECTS: Performs the associated client, billing, or time actions on an action event
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        setTabVisibility();
        clientActions(e.getActionCommand());
        billingActions(e.getActionCommand());
        timeActions(e.getActionCommand());
    }

    /*
     * MODIFIES: this
     * EFFECTS: Determines which tabs to show depending on whether a client and/or category has been selected
     */
    public void setTabVisibility() {
        if (this.currentClient == null) {
            tabbedPane.setEnabledAt(1, false);
            tabbedPane.setEnabledAt(2, false);
        } else if (this.currentClient != null) {
            tabbedPane.setEnabledAt(1, true);
        }
        if (this.currentCategory == null) {
            tabbedPane.setEnabledAt(2, false);
        } else if (this.currentClient != null && this.currentCategory != null) {
            tabbedPane.setEnabledAt(2, true);
        }
    }

    /*
     * MODIFIES: this
     * EFFECTS: Shows the according split pane depending on client action
     */
    private void clientActions(String command) {
        updateClientListModels();
        switch (command) {
            case "Select client":
                cardLayout.show(clientMainPanel, "selectClientSplitPane");
                //selectClientSplitPane.updateListModel();
                this.currentCategory = null;
                break;
            case "Add client":
                cardLayout.show(clientMainPanel, "addClientSplitPane");
                //addClientSplitPane.updateListModel();
                break;
            case "Edit client":
                cardLayout.show(clientMainPanel, "editClientSplitPane");
                //editClientSplitPane.updateListModel();
                break;
            case "Remove client":
                cardLayout.show(clientMainPanel, "removeClientSplitPane");
                //removeClientSplitPane.updateListModel();
                break;
            case "Save":
                save();
                break;
            case "Load save":
                loadSave();
                break;
        }
    }

    /*
     * MODIFIES: this
     * EFFECTS: Updates the JList models in each of the split panes
     */
    private void updateClientListModels() {
        selectClientSplitPane.updateListModel();
        addClientSplitPane.updateListModel();
        editClientSplitPane.updateListModel();
        removeClientSplitPane.updateListModel();
    }

    /*
     * MODIFIES: this
     * EFFECTS: Shows the according split pane depending on billing action
     */
    private void billingActions(String command) {
        switch (command) {
            case "Select billing":
                cardLayout.show(billingMainPanel, "selectBillingSplitPane");
                selectBillingSplitPane.updateListModel();
                break;
            case "Add billing":
                cardLayout.show(billingMainPanel, "addBillingSplitPane");
                addBillingSplitPane.updateListModel();
                break;
            case "Edit billing":
                cardLayout.show(billingMainPanel, "editBillingSplitPane");
                editBillingSplitPane.updateListModel();
                break;
            case "Remove billing":
                cardLayout.show(billingMainPanel, "removeBillingSplitPane");
                removeBillingSplitPane.updateListModel();
                break;
        }
    }

    /*
     * MODIFIES: this
     * EFFECTS: Shows the according split pane depending on time entry action
     */
    private void timeActions(String command) {
        switch (command) {
            case "View time entries":
                cardLayout.show(timeMainPanel, "selectTimeSplitPane");
                selectTimeSplitPane.updateModels();
                break;
            case "Add time entry":
                cardLayout.show(timeMainPanel, "addTimeSplitPane");
                addTimeSplitPane.updateModels();
                break;
            case "Edit time entry":
                cardLayout.show(timeMainPanel, "editTimeSplitPane");
                editTimeSplitPane.updateModels();
                break;
            case "Remove time entry":
                cardLayout.show(timeMainPanel, "removeTimeSplitPane");
                removeTimeSplitPane.updateModels();
                break;
        }
    }

    // EFFECTS: loads ClientBook, BillingCategories, MasterTimeLog from JSON file
    private void loadSave() {
        loadClientBook();
        loadBillingCategories();
        loadMasterTimeLog();
    }

    // MODIFIES: this
    // EFFECTS: loads ClientBook from file
    private void loadClientBook() {
        try {
            //this.clientBook = jsonReader.readClientBook(MenuTabs.CLIENT_JSON_STORE);
            this.clientBook.setClients(jsonReader.readClientBook(MenuTabs.CLIENT_JSON_STORE).getClients());
        } catch (IOException e) {
            //
        }
    }

    // MODIFIES: this
    // EFFECTS: loads BillingCategories from file
    private void loadBillingCategories() {
        try {
            //this.billingCategories = jsonReader.readBillingCategories(MenuTabs.BILLING_JSON_STORE, this.clientBook);
            this.billingCategories.setBillingCategories(
                    jsonReader.readBillingCategories(
                            MenuTabs.BILLING_JSON_STORE, this.clientBook).getAllBillingCategories());
        } catch (IOException e) {
            //
        } catch (NegativeRateException e) {
            JOptionPane.showMessageDialog(clientMainPanel, e.getMessage());
        } catch (EmptyNameException e) {
            JOptionPane.showMessageDialog(clientMainPanel, e.getMessage());
        }
    }

    // MODIFIES: this
    // EFFECTS: loads MasterTimeLog from file
    private void loadMasterTimeLog() {
        try {
//            this.masterTimeLog = jsonReader.readMasterTimeLog(MenuTabs.TIME_JSON_STORE,
//                    this.clientBook, this.billingCategories);
            this.masterTimeLog.setMasterTimeLog(jsonReader.readMasterTimeLog(MenuTabs.TIME_JSON_STORE,
                    this.clientBook, this.billingCategories).getMasterTimeLog());
        } catch (IOException e) {
            //
        }
    }

    // EFFECTS: saves the workroom to file
    private void save() {
        try {
            jsonWriter.open(MenuTabs.CLIENT_JSON_STORE);
            jsonWriter.write(this.clientBook);
            jsonWriter.close();
            jsonWriter.open(MenuTabs.BILLING_JSON_STORE);
            jsonWriter.write(this.billingCategories);
            jsonWriter.close();
            jsonWriter.open(MenuTabs.TIME_JSON_STORE);
            jsonWriter.write(this.masterTimeLog);
            jsonWriter.close();
        } catch (FileNotFoundException e) {
            //
        }
    }

    /*
     * MODIFIES: this
     * EFFECTS: Shows the client menu options
     */
    public void displayClientMenuOptions() {
        cardLayout.show(clientMainPanel, "clientMenuOptions");
    }

    /*
     * MODIFIES: this
     * EFFECTS: Shows the billing menu options
     */
    public void displayBillingMenuOptions() {
        cardLayout.show(billingMainPanel, "billingMenuOptions");
    }

    /*
     * MODIFIES: this
     * EFFECTS: Shows the time menu options
     */
    public void displayTimeMenuOptions() {
        cardLayout.show(timeMainPanel, "timeMenuOptions");
    }

    public Client getCurrentClient() {
        return currentClient;
    }

    public void setCurrentClient(Client currentClient) {
        this.currentClient = currentClient;
    }

    public BillingCategory getCurrentCategory() {
        return currentCategory;
    }

    public void setCurrentCategory(BillingCategory currentCategory) {
        this.currentCategory = currentCategory;
    }
}
