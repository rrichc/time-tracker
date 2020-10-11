package model;

import java.util.ArrayList;

public class BillingCategories {
    private ArrayList<BillingCategory> billingCategories;

    public BillingCategories() {
        this.billingCategories = new ArrayList<BillingCategory>();
    }

    //requires ratePerHour to be non-negative
    public void createBillingCategory(String name, String ratePerHour, Client client) {
        this.billingCategories.add(new BillingCategory(name, ratePerHour, client));
    }

    public void removeBillingCategory(String name) {
        for (BillingCategory category : billingCategories) {
            if (category.getName() == name) {
                this.billingCategories.remove(category);
            }
        }
    }

    //requires ratePerHour to be non-negative
    public void editBillingCategory(String oldName, String name, String ratePerHour) {
        for (BillingCategory category : billingCategories) {
            if (category.getName() == oldName) {
                category.setName(name);
                category.setRatePerHour(ratePerHour);
            }
        }
    }

//    public void editBillingCategory(String oldName, String name, String ratePerHour, Client client) {
//        createBillingCategory(name, ratePerHour, client);
//        removeBillingCategory(oldName);
//    }

    public ArrayList<BillingCategory> getBillingCategories() {
        return this.billingCategories;
    }
}
