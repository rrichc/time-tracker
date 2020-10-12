package model;

//TODO: Add a class description for BillingCategory

public class BillingCategory {

    private String name;
    private double ratePerHour;
    private Client client;

    //requires ratePerHour to be non-negative
    public BillingCategory(String name, String ratePerHour, Client client) {
        this.name = name;
        this.ratePerHour = Double.parseDouble(ratePerHour);
        this.client = client;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getRatePerHour() {
        return ratePerHour;
    }

    public void setRatePerHour(String ratePerHour) {
        this.ratePerHour = Double.parseDouble(ratePerHour);
    }

    public Client getClient() {
        return client;
    }
}
