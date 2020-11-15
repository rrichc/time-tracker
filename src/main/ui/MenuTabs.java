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

import model.BillingCategories;
import model.ClientBook;
import model.MasterTimeLog;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Scanner;

public class MenuTabs implements ActionListener {
    static final String CLIENTMENU = "Client Menu";
    static final String TEXTPANEL = "Placeholder";
    static final int extraWindowWidth = 100;
    JPanel clientMenuTab;
    ClientSplitPane addClientSplitPane;
    ClientSplitPane editClientSplitPane;
    //TODO: Also need to keep track in here what is the global (ie. active client, category, timeEntry)
    CardLayout cardLayout;
    JPanel clientMainPanel;

    public static final String CLIENT_JSON_STORE = "./data/clientBook.json";
    public static final String BILLING_JSON_STORE = "./data/billingCategories.json";
    public static final String TIME_JSON_STORE = "./data/masterTimeLog.json";
    private Scanner input;
    private MasterTimeLog masterTimeLog;
    private BillingCategories billingCategories;
    private ClientBook clientBook;
    private JsonReader jsonReader;
    private JsonWriter jsonWriter;

    public void addMenuTabsToPane(Container pane) {
        init();
        JTabbedPane tabbedPane = new JTabbedPane();

        cardLayout = new CardLayout();
        clientMainPanel = new JPanel(cardLayout);
        clientMainPanel.add(clientMenuTab, "clientMenuOptions");
        clientMainPanel.add(addClientSplitPane.getClientSplitPane(), "addClientSplitPane");
        clientMainPanel.add(editClientSplitPane.getClientSplitPane(), "editClientSplitPane");

        //TODO: Temp - remove later
        JPanel card2 = new JPanel();
        card2.add(new JTextField("TextField", 20));

        tabbedPane.addTab(CLIENTMENU, clientMainPanel);
        tabbedPane.addTab(TEXTPANEL, card2);

        pane.add(tabbedPane, BorderLayout.CENTER);
    }

    /*
     * MODIFIES: this
     * EFFECTS: Initializes dependencies needed by the Client, Billing, and Time Entry menu down the line
     */
    private void init() {
        this.input = new Scanner(System.in);
        this.masterTimeLog = new MasterTimeLog();
        this.billingCategories = new BillingCategories();
        this.clientBook = new ClientBook();
        this.jsonReader = new JsonReader();
        this.jsonWriter = new JsonWriter();

        initMenuOptions();
        initSplitPanes();
    }

    private void initMenuOptions() {
        clientMenuTab = new ClientMenuOptions(this).getClientMenuOptions();
    }

    //Create the "cards".
    private void initSplitPanes() {
        addClientSplitPane = new ClientSplitPane(this,
                this.clientBook, this.masterTimeLog, ActionState.ADD);
        editClientSplitPane = new ClientSplitPane(this,
                this.clientBook, this.masterTimeLog, ActionState.EDIT);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println(e.getActionCommand());
        switch (e.getActionCommand()) {
            case "Add client":
                cardLayout.show(clientMainPanel, "addClientSplitPane");
                addClientSplitPane.updateListModel();
                break;
            case "Edit client":
                cardLayout.show(clientMainPanel, "editClientSplitPane");
                editClientSplitPane.updateListModel();
                break;
        }
    }

    public void displayClientMenuOptions() {
        cardLayout.show(clientMainPanel, "clientMenuOptions");
    }
}
