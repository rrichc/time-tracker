package model;

import java.util.ArrayList;

public class BillingCategories {
    private ArrayList<BillingCategory> billingCategories;

    public BillingCategories() {
        this.billingCategories = new ArrayList<BillingCategory>();
    }

    //requires ratePerHour to be non-negative
    public void createBillingCategory(String name, String ratePerHour, Client client) {
        boolean nameAlreadyExists = duplicateNameCheck(name);
        if (!nameAlreadyExists) {
            this.billingCategories.add(new BillingCategory(name, ratePerHour, client));
        } else {
            return; //Return a code to indicate user needs to re-pick?
        }
    }

    public void removeBillingCategory(String name) {
        for (BillingCategory category : billingCategories) {
            if (category.getName().equals(name)) {
                this.billingCategories.remove(category);
            }
        }
    }

    //requires ratePerHour to be non-negative
    public void editBillingCategory(String oldName, String name, String ratePerHour) {
        boolean nameAlreadyExists = duplicateNameCheck(name);
        if (!nameAlreadyExists) {
            for (BillingCategory category : billingCategories) {
                if (category.getName().equals(oldName)) {
                    category.setName(name);
                    category.setRatePerHour(ratePerHour);
                }
            }
        } else {
            return; //Return a code to indicate user needs to re-pick?
        }
    }

//    public void editBillingCategory(String oldName, String name, String ratePerHour, Client client) {
//        createBillingCategory(name, ratePerHour, client);
//        removeBillingCategory(oldName);
//    }

    public boolean duplicateNameCheck(String name) {
        boolean nameAlreadyExists = false;
        for (BillingCategory category : billingCategories) {
            if (category.getName().equals(name)) {
                nameAlreadyExists = true;
            }
        }
        return nameAlreadyExists;
    }

    public ArrayList<BillingCategory> getBillingCategories() {
        return this.billingCategories;
    }
}
