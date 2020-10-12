package model;

import java.util.ArrayList;

public class BillingCategories {
    private ArrayList<BillingCategory> billingCategories;

    public BillingCategories() {
        this.billingCategories = new ArrayList<BillingCategory>();
    }

    //requires ratePerHour to be non-negative
    public boolean createBillingCategory(String name, String ratePerHour, Client client) {
        boolean nameAlreadyExists = duplicateNameCheck(name, client);
        if (!nameAlreadyExists) {
            this.billingCategories.add(new BillingCategory(name, ratePerHour, client));
            return true;
        } else {
            return false;
        }
    }

    //make sure to test both cases in the && for full coverage
    public boolean removeBillingCategory(String name, Client selectedClient) {
        for (BillingCategory category : billingCategories) {
            if (category.getName().equals(name) && category.getClient().getName().equals(selectedClient.getName())) {
                this.billingCategories.remove(category);
                return true;
            }
        }
        return false;
    }

    //requires ratePerHour to be non-negative
    public boolean editBillingCategory(String oldName, String name, String ratePerHour, Client selectedClient) {
        boolean nameAlreadyExists = duplicateNameCheck(name, selectedClient);
        if (!nameAlreadyExists) {
            for (BillingCategory category : billingCategories) {
                if (category.getName().equals(oldName)
                        && category.getClient().getName().equals(selectedClient.getName())) {
                    category.setName(name);
                    category.setRatePerHour(ratePerHour);
                    return true;
                }
            }
        }
        return false;
    }

//    public void editBillingCategory(String oldName, String name, String ratePerHour, Client client) {
//        createBillingCategory(name, ratePerHour, client);
//        removeBillingCategory(oldName);
//    }

    public boolean duplicateNameCheck(String name, Client client) {
        boolean nameAlreadyExists = false;
        for (BillingCategory category : getBillingCategoriesForClient(client)) {
            if (category.getName().equals(name)) {
                nameAlreadyExists = true;
            }
        }
        return nameAlreadyExists;
    }

    public ArrayList<BillingCategory> getAllBillingCategories() {
        return this.billingCategories;
    }

    public ArrayList<BillingCategory> getBillingCategoriesForClient(Client client) {
        ArrayList<BillingCategory> categoriesForUser = new ArrayList<BillingCategory>();
        for (BillingCategory category : billingCategories) {
            if (category.getClient().getName().equals(client.getName())) {
                categoriesForUser.add(category);
            }
        }
        return categoriesForUser;
    }

    public BillingCategory getABillingCategory(String name) {
        for (BillingCategory category : billingCategories) {
            if (category.getName().equals(name)) {
                return category;
            }
        }
        return null;
    }
}
