package model;

import java.util.ArrayList;

public class ClientBook {
    private ArrayList<Client> clients;

    public ClientBook() {
        this.clients = new ArrayList<Client>();
    }

    public void createClient(String name) {
        this.clients.add(new Client(name));
    }

    public void removeClient(String name) {
        for (Client client : clients) {
            if (client.getName() == name) {
                this.clients.remove(client);
            }
        }
    }

    public void editClient(String oldName, String name) {
        for (Client client : clients) {
            if (client.getName() == oldName) {
                client.setName(name);
            }
        }
    }

//    public void editClient(String oldName, String name) {
//        createClient(name);
//        removeClient(oldName);
//    }

    public ArrayList<Client> getClients() {
        return this.clients;
    }
}
