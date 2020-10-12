package model;

import java.util.ArrayList;

//BillingCategories represents a collection of billing categories
public class BillingCategories {
    private ArrayList<BillingCategory> billingCategories;   //collection of individual BillingCategory objects

    /*
     * EFFECTS: initializes an empty ArrayList holding BillingCategory objects
     */
    public BillingCategories() {
        this.billingCategories = new ArrayList<BillingCategory>();
    }

    /*
     * REQUIRES: name must be a non-empty String, ratePerHour must be a non-negative integer,
     *           client must have non-empty fields
     * MODIFIES: this
     * EFFECTS:  Creates a BillingCategory if there is no duplicate name already in the collection,
     *           and adds it to the collection. Returns true if the adding operation was successful,
     *           otherwise returns false
     */
    public boolean createBillingCategory(String name, String ratePerHour, Client client) {
        boolean nameAlreadyExists = duplicateNameCheck(name, client);
        if (!nameAlreadyExists) {
            this.billingCategories.add(new BillingCategory(name, ratePerHour, client));
            return true;
        } else {
            return false;
        }
    }

    //TODO: make sure to test both cases in the && for full coverage
    /*
     * REQUIRES: name must be a non-empty String, client must have non-empty fields
     * MODIFIES: this
     * EFFECTS: Removes a BillingCategory in the collection if there is a category with a matching name
     *          AND the category belongs to the currently selected client.
     *          Returns true if the removing operation was successful, otherwise returns false
     */
    public boolean removeBillingCategory(String name, Client selectedClient) {
        for (BillingCategory category : billingCategories) {
            if (category.getName().equals(name) && category.getClient().getName().equals(selectedClient.getName())) {
                this.billingCategories.remove(category);
                return true;
            }
        }
        return false;
    }


    //TODO: make sure to test both cases in the && for full coverage
    /*
     * REQUIRES: oldName and name must be a non-empty String, ratePerHour must a non-negative integer,
     *           client must have non-empty fields
     * MODIFIES: this
     * EFFECTS: Edits a BillingCategory name if there is no duplicate name already in the collection.
     *          AND the category belongs to the currently selected client.
     *          Returns true if the removing operation was successful, otherwise returns false
     */
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

    /*
     * REQUIRES: name must be non-empty String, client must have non-empty fields
     * EFFECTS: Checks if a name is the same as a BillingCategory name already in the collection.
     *          return true if there is a matching duplicate, return false otherwise
     */
    public boolean duplicateNameCheck(String name, Client client) {
        boolean nameAlreadyExists = false;
        for (BillingCategory category : getBillingCategoriesForClient(client)) {
            if (category.getName().equals(name)) {
                nameAlreadyExists = true;
            }
        }
        return nameAlreadyExists;
    }

    /*
     * EFFECTS: Returns the collection of BillingCategories for all clients
     */
    public ArrayList<BillingCategory> getAllBillingCategories() {
        return this.billingCategories;
    }

    /*
     * REQUIRES: client must have non-empty fields
     * EFFECTS: Returns the collection of BillingCategories for matching a particular client
     */
    public ArrayList<BillingCategory> getBillingCategoriesForClient(Client client) {
        ArrayList<BillingCategory> categoriesForUser = new ArrayList<BillingCategory>();
        for (BillingCategory category : billingCategories) {
            if (category.getClient().getName().equals(client.getName())) {
                categoriesForUser.add(category);
            }
        }
        return categoriesForUser;
    }

    /*
     * REQUIRES: name must be non-empty String
     * EFFECTS: Returns a BillingCategory with the matching name, otherwise returns null
     */
    public BillingCategory getABillingCategory(String name) {
        for (BillingCategory category : billingCategories) {
            if (category.getName().equals(name)) {
                return category;
            }
        }
        return null;
    }
}
