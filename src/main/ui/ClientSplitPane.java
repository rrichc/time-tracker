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

import model.Client;
import model.ClientBook;
import model.MasterTimeLog;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;

//SplitPaneDemo itself is not a visible component.
//Class based off the https://docs.oracle.com/javase/tutorial/uiswing/components/splitpane.html sample project provided
public class ClientSplitPane implements ListSelectionListener {
    private JList list;
    private JSplitPane splitPane;
    private ClientBook clientBook;
    private MasterTimeLog masterTimeLog;
    private MenuTabs menuTabs;
    public DefaultListModel clientNames;
    private JPanel secondPanel;
    private ActionState state;
    private JScrollPane listScrollPane;
    private String[] labels = {"Client Name: "};
    private String listSelectedClient;


    public ClientSplitPane(MenuTabs menuTabs, ClientBook clientBook, MasterTimeLog masterTimeLog, ActionState state) {
        this.menuTabs = menuTabs;
        this.clientBook = clientBook;
        this.masterTimeLog = masterTimeLog;
        this.state = state;
        clientNames = new DefaultListModel();

        buildClientList();
        createSecondPanel();
        createSplitPane();

    }

    private void addNamesToListModel() {
        //Create the list of client names and put it in a scroll pane.
        if (clientBook.getClients().isEmpty()) {
            this.clientNames.addElement("Empty");
        } else {
            listSelectedClient = clientBook.getClients().get(0).getName();
            for (Client client : clientBook.getClients()) {
                this.clientNames.addElement(client.getName());
            }
        }
    }

    private void buildClientList() {
        addNamesToListModel();
        list = new JList(this.clientNames);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setSelectedIndex(0);
        list.addListSelectionListener(this);
        listScrollPane = new JScrollPane(list);
    }

    private void createSecondPanel() {
        switch (state) {
            case SELECT:
                secondPanel = new SelectClientButtonPane(this).getPanel();
                break;
            case ADD:
                secondPanel = new ClientForm(this.labels, ActionState.ADD, this).getSpringForm();
                break;
            case EDIT:
                secondPanel = new ClientForm(labels, ActionState.EDIT, this).getSpringForm();
                break;
            case REMOVE:
                secondPanel = new RemoveClientButtonPane(this).getPanel();
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
                listSelectedClient =  list.getSelectedValue().toString();
                System.out.println(listSelectedClient);
            }
        }
    }

    public JSplitPane getClientSplitPane() {
        return splitPane;
    }

    public String getListSelectedClient() {
        return listSelectedClient;
    }

    public ClientBook getClientBook() {
        return clientBook;
    }

    public MasterTimeLog getMasterTimeLog() {
        return masterTimeLog;
    }

    public void updateListModel() {
        this.clientNames.clear();
        addNamesToListModel();
    }

    public void showClientMenuOptions() {
        menuTabs.displayClientMenuOptions();
    }

    public void setCurrentClient(String currentClient) {
        menuTabs.setCurrentClient(clientBook.getAClient(currentClient));
    }

    public void setTabVisibility() {
        menuTabs.setTabVisibility();
    }
}


