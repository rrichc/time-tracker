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
import java.util.Scanner;

public class MenuTabs implements ActionListener {
    static final int extraWindowWidth = 100;
    CardLayout cardLayout;

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

    //TODO: Also need to keep track in here what is the global (ie. active client, category, timeEntry)
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

    public void addMenuTabsToPane(Container pane) {
        init();
        JTabbedPane tabbedPane = new JTabbedPane();
        cardLayout = new CardLayout();
        clientPanelSetUp();
        billingPanelSetUp();

        tabbedPane.addTab(CLIENTMENU, clientMainPanel);
        tabbedPane.addTab(BILLINGMENU, billingMainPanel);

        //TODO: Tab change listener code - use this to refresh all client, billing, time entry splitplanes with updateListModel
        //http://www.java2s.com/Tutorial/Java/0240__Swing/ListeningforSelectedTabChanges.htm
        ChangeListener changeListener = new ChangeListener() {
            public void stateChanged(ChangeEvent changeEvent) {
                JTabbedPane sourceTabbedPane = (JTabbedPane) changeEvent.getSource();
                int index = sourceTabbedPane.getSelectedIndex();
                System.out.println("Tab changed to: " + sourceTabbedPane.getTitleAt(index));
            }
        };
        tabbedPane.addChangeListener(changeListener);

        pane.add(tabbedPane, BorderLayout.CENTER);
    }


    /*
     * MODIFIES: this
     * EFFECTS: Initializes dependencies needed by the Client, Billing, and Time Entry menu down the line
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
    }

    private void initMenuOptions() {
        clientMenuTab = new ClientMenuOptions(this).getClientMenuOptions();
        billingMenuTab = new BillingMenuOptions(this).getBillingMenuOptions();
    }

    //Create the client "cards".
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


    private void clientPanelSetUp() {
        clientMainPanel = new JPanel(cardLayout);
        clientMainPanel.add(clientMenuTab, "clientMenuOptions");
        clientMainPanel.add(selectClientSplitPane.getClientSplitPane(), "selectClientSplitPane");
        clientMainPanel.add(addClientSplitPane.getClientSplitPane(), "addClientSplitPane");
        clientMainPanel.add(editClientSplitPane.getClientSplitPane(), "editClientSplitPane");
        clientMainPanel.add(removeClientSplitPane.getClientSplitPane(), "removeClientSplitPane");
    }

    //Create the billing "cards".
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


    private void billingPanelSetUp() {
        billingMainPanel = new JPanel(cardLayout);
        billingMainPanel.add(billingMenuTab, "billingMenuOptions");
        billingMainPanel.add(selectBillingSplitPane.getBillingSplitPane(), "selectBillingSplitPane");
        billingMainPanel.add(addBillingSplitPane.getBillingSplitPane(), "addBillingSplitPane");
        billingMainPanel.add(editBillingSplitPane.getBillingSplitPane(), "editBillingSplitPane");
        billingMainPanel.add(removeBillingSplitPane.getBillingSplitPane(), "removeBillingSplitPane");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println(e.getActionCommand());
        clientActions(e.getActionCommand());
        billingActions(e.getActionCommand());
    }

    private void clientActions(String command) {
        switch (command) {
            case "Select client":
                cardLayout.show(clientMainPanel, "selectClientSplitPane");
                selectClientSplitPane.updateListModel();
                break;
            case "Add client":
                cardLayout.show(clientMainPanel, "addClientSplitPane");
                addClientSplitPane.updateListModel();
                break;
            case "Edit client":
                cardLayout.show(clientMainPanel, "editClientSplitPane");
                editClientSplitPane.updateListModel();
                break;
            case "Remove client":
                cardLayout.show(clientMainPanel, "removeClientSplitPane");
                removeClientSplitPane.updateListModel();
                break;
        }
    }

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

    public void displayClientMenuOptions() {
        cardLayout.show(clientMainPanel, "clientMenuOptions");
    }

    public void displayBillingMenuOptions() {
        cardLayout.show(billingMainPanel, "billingMenuOptions");
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
