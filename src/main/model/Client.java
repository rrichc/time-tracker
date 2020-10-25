package model;

import org.json.JSONObject;
import persistence.Writable;

//Client represents a client that billing categories and time logs will be associated with
public class Client implements Writable {

    private String name;    //the client's name

    /*
     * REQUIRES: name must be a non-empty string
     * EFFECTS: Assigns the name to be the client name for this object
     */
    public Client(String name) {
        this.name = name;
    }

    /*
     * EFFECTS: returns the name of the client as a string
     */
    public String getName() {
        return name;
    }

    /*
     * REQUIRES: name must a non-empty string
     * MODIFIES: this
     * EFFECTS: sets the name of the client to be name
     */
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("clientName", name);
        return json;
    }
}
