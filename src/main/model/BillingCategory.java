package model;

import org.json.JSONObject;
import persistence.Writable;

//Represents a billing category that has a name, rate per hour (in dollars), and a client associated with the category
public class BillingCategory implements Writable {

    private String name;        //name of the billing category
    private double ratePerHour; //the rate per hour in dollar associated with the category
    private Client client;      //the client this category falls under

    /*
    * REQUIRES: name must be a non-empty String, raterPerHour must be a non-negative number in String representation,
    *           client must contain non-empty fields
    * EFFECTS: name, ratePerHour, and client is assigned to the fields in the class object.
    *          ratePerHour is converted from string to double format.
    */
    public BillingCategory(String name, String ratePerHour, Client client) {
        this.name = name;
        this.ratePerHour = Double.parseDouble(ratePerHour);
        this.client = client;
    }

    /*
     * EFFECTS: returns name as a string
     */
    public String getName() {
        return name;
    }

    /*
     * REQUIRES: name must be a non-empty String
     * MODIFIES: this
     * EFFECTS: sets the name of the billing category to be name
     */
    public void setName(String name) {
        this.name = name;
    }

    /*
     * EFFECTS: returns ratePerHour as a double
     */
    public double getRatePerHour() {
        return ratePerHour;
    }

    /*
     * REQUIRES: Rate must be a non-negative number in String representation,
     * EFFECTS:  ratePerHour is converted from string to double format.
     */
    public void setRatePerHour(String ratePerHour) {
        this.ratePerHour = Double.parseDouble(ratePerHour);
    }

    /*
     * EFFECTS: returns the Client associated with this category
     */
    public Client getClient() {
        return client;
    }

    /*
     * EFFECTS:  Returns an representation of the BillingCategory as a JSONObject
     */
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("category", name);
        json.put("ratePerHour", String.valueOf(ratePerHour));
        json.put("client", client.getName());
        return json;
    }
}
