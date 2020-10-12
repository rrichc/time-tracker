package model;

import java.util.ArrayList;

//ClientBook represents a collection of individual Client objects
public class ClientBook {
    private ArrayList<Client> clients;

    /*
     * EFFECTS: initializes an empty ArrayList holding Client objects
     */
    public ClientBook() {
        this.clients = new ArrayList<Client>();
    }

    /*
     * REQUIRES: name must be a non-empty String
     * MODIFIES: this
     * EFFECTS: Creates a client if there is no duplicate name already in the collection, and adds it to the collection.
     *          Returns true if the adding operation was successful, otherwise returns false
     */
    public boolean createClient(String name) {
        boolean nameAlreadyExists = duplicateNameCheck(name);
        if (!nameAlreadyExists) {
            this.clients.add(new Client(name));
            return true;
        } else {
            return false;
        }
    }

    /*
     * REQUIRES: name must be a non-empty String
     * MODIFIES: this
     * EFFECTS: Removes a client in the collection if there is a client with a matching name
     *          Returns true if the removing operation was successful, otherwise returns false
     */
    public boolean removeClient(String name) {
        for (Client client : clients) {
            if (client.getName().equals(name)) {
                this.clients.remove(client);
                return true;
            }
        }
        return false;
    }

    /*
     * REQUIRES: oldName and name must be non-empty Strings
     * MODIFIES: this
     * EFFECTS: Edits a client's name if there is no duplicate name already in the collection.
     *          Returns true if the adding operation was successful, otherwise returns false
     */
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

    /*
     * REQUIRES: name must be non-empty String
     * EFFECTS: Checks if a name is the same as a Client's name already in the collection.
     *          return true if there is a matching duplicate, return false otherwise
     */
    public boolean duplicateNameCheck(String name) {
        boolean nameAlreadyExists = false;
        for (Client client : clients) {
            if (client.getName().equals(name)) {
                nameAlreadyExists = true;
            }
        }
        return nameAlreadyExists;
    }

    /*
     * EFFECTS: Returns the collection of Clients
     */
    public ArrayList<Client> getClients() {
        return this.clients;
    }

    /*
     * REQUIRES: name must be non-empty String
     * EFFECTS: Returns a Client with the matching name, otherwise returns null
     */
    public Client getAClient(String name) {
        for (Client client : clients) {
            if (client.getName().equals(name)) {
                return client;
            }
        }
        return null;
    }
}
