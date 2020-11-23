package model;

import org.json.JSONObject;
import persistence.Writable;

//Represents a billing category that has a name, rate per hour (in dollars), and a client associated with the category
public class BillingCategory implements Writable {

    private String name;        //name of the billing category
    private double ratePerHour; //the rate per hour in dollar associated with the category
    private Client client;      //the client this category falls under

    /*
    * EFFECTS: name, ratePerHour, and client is assigned to the fields in the class object.
    *          ratePerHour is converted from string to double format.
    *          throws a NegativeRateException if the rate is < 0
    *          throws an EmptyNameException is the name is length 0
    *
    */
    public BillingCategory(String name, String ratePerHour, Client client)
            throws NumberFormatException, NegativeRateException, EmptyNameException {
        if (name.length() < 1) {
            throw new EmptyNameException("Please enter a name with at least 1 character.");
        }
        this.name = name;
        this.ratePerHour = Double.parseDouble(ratePerHour);
        if (this.ratePerHour < 0) {
            throw new NegativeRateException("Please enter a positive rate.");
        }
        this.client = client;
    }

    /*
     * EFFECTS: returns name as a string
     */
    public String getName() {
        return name;
    }

    /*
     * MODIFIES: this
     * EFFECTS: sets the name of the billing category to be name
     *          throws an EmptyNameException is the name is length 0
     */
    public void setName(String name) throws EmptyNameException {
        if (name.length() < 1) {
            throw new EmptyNameException("Please enter a name with at least 1 character.");
        }
        this.name = name;
    }

    /*
     * EFFECTS: returns ratePerHour as a double
     */
    public double getRatePerHour() {
        return ratePerHour;
    }

    /*
     * EFFECTS:  ratePerHour is converted from string to double format.
     *          throws a NegativeRateException if the rate is < 0

     */
    public void setRatePerHour(String ratePerHour) throws NegativeRateException, NumberFormatException {
        this.ratePerHour = Double.parseDouble(ratePerHour);
        if (this.ratePerHour < 0) {
            throw new NegativeRateException("Please enter a positive rate.");
        }
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
