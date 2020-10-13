package ui;

import model.*;

import java.util.Scanner;

public class BillingMenu {

    private Scanner input;
    private MasterTimeLog masterTimeLog;
    private BillingCategories billingCategories;
    private ClientBook clientBook;
    private Client currentClient;
    private BillingCategory currentBillingCategory;
    private TimeLog currentTimeLog;

    TimeEntryMenu timeEntryMenu;

    public BillingMenu(Scanner input, MasterTimeLog masterTimeLog, BillingCategories billingCategories,
                       ClientBook clientBook, Client currentClient,
                       TimeLog currentTimeLog) {
        this.input = input;
        this.masterTimeLog = masterTimeLog;
        this.billingCategories = billingCategories;
        this.clientBook = clientBook;
        this.currentClient = currentClient;
        this.currentTimeLog = currentTimeLog;
    }

    public void displayBillingMenu() {
        boolean keepBillingMenuGoing = true;
        String command = null;

        while (keepBillingMenuGoing) {
            displayBillingMenuOptions();
            command = input.nextLine();
            command = command.toLowerCase();

            if (command.equals("b")) {
                keepBillingMenuGoing = false;
            } else {
                processBillingCommand(command);
            }
        }
        System.out.println("\nReturning to client menu");
    }

    private void displayBillingMenuOptions() {
        System.out.println("\nBilling Menu:");
        System.out.println("\ts -> select billing category");
        System.out.println("\tc -> create billing category");
        System.out.println("\te -> edit billing category");
        System.out.println("\tr -> remove billing category");
        System.out.println("\tb -> back");
    }

    // MODIFIES: this
    // EFFECTS: processes user command
    private void processBillingCommand(String command) {
        if (command.equals("s")) {
            selectBillingCategory();
        } else if (command.equals("c")) {
            createBillingCategoryOption();
        } else if (command.equals("e")) {
            editBillingCategoryOption();
        } else if (command.equals("r")) {
            removeBillingCategoryOption();
        } else {
            System.out.println("Please make a valid selection by entering the corresponding letter for your action.");
        }
    }

    // EFFECTS:
    private void selectBillingCategory() {
        boolean selectedCategoryIsInList = false;
        while (!selectedCategoryIsInList) {
            System.out.println("Please select one of the categories below or type 'q' to quit:");
            displayBillingCategories();
            String nameSelected = input.nextLine();
            if (nameSelected.equals("q")) {
                break;
            }
            if (categoryIsInBillingCategories(nameSelected)) {
                selectedCategoryIsInList = true;
                this.currentBillingCategory = billingCategories.getABillingCategory(nameSelected);
                System.out.println(nameSelected + " has been selected.");
                this.timeEntryMenu = new TimeEntryMenu(this.input, this.masterTimeLog, this.billingCategories,
                        this.clientBook, this.currentClient, this.currentBillingCategory, this.currentTimeLog);
                timeEntryMenu.displayTimeEntryMenu(); //continue to Time Entry menu
            } else {
                System.out.println("Please make a valid selection by entering the exact billing category name.");
            }
        }
    }

    private void displayBillingCategories() {
        if (billingCategories.getBillingCategoriesForClient(this.currentClient).isEmpty()) {
            System.out.println("The list of billing categories is empty. "
                    + "Please return to the billing menu and add a category.");
        } else {
            for (BillingCategory category
                    : billingCategories.getBillingCategoriesForClient(this.currentClient)) {
                System.out.println(category.getName());
            }
        }
    }

    private void createBillingCategoryOption() {
        boolean creationSuccess = false;
        while (!creationSuccess) {
            System.out.println("Please enter a name for the new category or type 'q' to quit. ");
            String nameSelected = input.nextLine();
            if (nameSelected.equals("q")) {
                break;
            }
            System.out.println("Please enter a rate per hour in dollars. E.g. 16.50 ");
            try {
                String ratePerHour = input.nextLine();
                checkNegativeRate(Double.parseDouble(ratePerHour));
                if (billingCategories.createBillingCategory(nameSelected, ratePerHour, this.currentClient)) {
                    creationSuccess = true;
                    System.out.println(nameSelected + " has been created.");
                } else {
                    System.out.println("Billing category already exists. Please enter a unique name.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid non-negative number.");
            }
        }
    }

    private void removeBillingCategoryOption() {
        boolean removalSuccess = false;
        while (!removalSuccess) {
            System.out.println("Please enter a billing category to remove or type 'q' to quit. ");
            String nameSelected = input.nextLine();
            if (nameSelected.equals("q")) {
                break;
            }
            if (billingCategories.removeBillingCategory(nameSelected, this.currentClient)) {
                removalSuccess = true;
                System.out.println(nameSelected + " has been removed.");
            } else {
                System.out.println("Billing category does not exist. Please type the exact name.");
            }
        }
    }

    //TODO: Refactor the rate check into another method for the edit and create option
    private void editBillingCategoryOption() {
        boolean editSuccess = false;
        while (!editSuccess) {
            System.out.println("Please enter a billing category to edit or type 'q' to quit. ");
            String nameSelected = input.nextLine();
            if (nameSelected.equals("q")) {
                break;
            }
            System.out.println("Please enter a new billing category name.");
            String newName = input.nextLine();
            System.out.println("Please enter a new rate per hour. E.g. 16.50");
            try {
                String newRatePerHour = input.nextLine();
                checkNegativeRate(Double.parseDouble(newRatePerHour));
                if (billingCategories.editBillingCategory(nameSelected, newName, newRatePerHour, this.currentClient)) {
                    editSuccess = true;
                    System.out.println(nameSelected + " has been updated.");
                } else {
                    System.out.println("Billing category does not exist. Please type the exact name.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid non-negative number.");
            }
        }
    }

    public void checkNegativeRate(double ratePerHour) {
        if (ratePerHour < 0) {
            throw new NumberFormatException();
        }
    }

    private boolean categoryIsInBillingCategories(String name) {
        for (BillingCategory category : billingCategories.getBillingCategoriesForClient(this.currentClient)) {
            if (category.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }

    //END OF BILLING MENU METHODS

}
