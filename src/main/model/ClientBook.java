package model;

import java.util.ArrayList;

public class ClientBook {
    private ArrayList<Client> clients;

    public ClientBook() {
        this.clients = new ArrayList<Client>();
    }

    public boolean createClient(String name) {
        boolean nameAlreadyExists = duplicateNameCheck(name);
        if (!nameAlreadyExists) {
            this.clients.add(new Client(name));
            return true;
        } else {
            return false;
        }
    }

    public boolean removeClient(String name) {
        //clients.removeIf(client -> client.getName() == name);
        for (Client client : clients) {
            if (client.getName().equals(name)) {
                this.clients.remove(client);
                return true;
            }
        }
        return false;
    }

    public boolean editClient(String oldName, String name) {
        boolean nameAlreadyExists = duplicateNameCheck(name);
        if (!nameAlreadyExists) {
            for (Client client : clients) {
                if (client.getName().equals(oldName)) {
                    client.setName(name);
                    return true;
                }
            }
        }
        return false;
    }

//    public void editClient(String oldName, String name) {
//        createClient(name);
//        removeClient(oldName);
//    }

    public boolean duplicateNameCheck(String name) {
        boolean nameAlreadyExists = false;
        for (Client client : clients) {
            if (client.getName().equals(name)) {
                nameAlreadyExists = true;
            }
        }
        return nameAlreadyExists;
    }

    public ArrayList<Client> getClients() {
        return this.clients;
    }

    public Client getAClient(String name) {
        for (Client client : clients) {
            if (client.getName().equals(name)) {
                return client;
            }
        }
        return null;
    }
}
