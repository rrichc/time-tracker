package ui;

import model.*;

import java.util.ArrayList;
import java.util.Scanner;

//This UI class is based on the UI class in the example CPSC 210 TellerApp provided by Paul Carter
//https://github.students.cs.ubc.ca/CPSC210/TellerApp
public class TimeTracker {
    private Scanner input;
    private MasterTimeLog masterTimeLog;
    private BillingCategories billingCategories;
    private ClientBook clientBook;
    private Client currentSelectedClient;
    private BillingCategory currentSelectedBillingCategory;
    private TimeLog currentTimeLog;

    public TimeTracker() {
        runTimeTracker();
    }

    public void runTimeTracker() {
        init();
        displayClientMenu();
    }

    private void init() {
        this.input = new Scanner(System.in);
        this.masterTimeLog = new MasterTimeLog();
        this.billingCategories = new BillingCategories();
        this.clientBook = new ClientBook();
        this.currentSelectedClient = null;
        this.currentSelectedBillingCategory = null;
        this.currentTimeLog = null;
    }

    //START OF CLIENT MENU METHODS
    private void displayClientMenu() {
        boolean keepClientMenuGoing = true;
        String command = null;

        while (keepClientMenuGoing) {
            displayClientMenuOptions();
            command = input.nextLine();
            command = command.toLowerCase();

            if (command.equals("q")) {
                keepClientMenuGoing = false;
            } else {
                processClientCommand(command);
            }
        }
        System.out.println("\nThank you for using Time Tracker.");
    }

    private void displayClientMenuOptions() {
        System.out.println("\nClient Menu:");
        System.out.println("\ts -> select client");
        System.out.println("\tc -> create new client");
        System.out.println("\te -> edit client");
        System.out.println("\tr -> remove client");
        System.out.println("\tq -> quit");
    }

    // MODIFIES: this
    // EFFECTS: processes user command
    private void processClientCommand(String command) {
        if (command.equals("s")) {
            selectClient();
        } else if (command.equals("c")) {
            createClientOption();
        } else if (command.equals("e")) {
            editClientOption();
        } else if (command.equals("r")) {
            removeClientOption();
        } else {
            System.out.println("Please make a valid selection by entering the corresponding letter for your action.");
        }
    }

    // EFFECTS:
    private void selectClient() {
        boolean selectedClientIsInList = false;
        String nameSelected = "";

        while (!selectedClientIsInList) {
            System.out.println("Please select one of the clients below or type 'q' to quit:");
            displayClients();
            nameSelected = input.nextLine();
            if (clientIsInClientBook(nameSelected)) {
                selectedClientIsInList = true;
                this.currentSelectedClient = clientBook.getAClient(nameSelected);
                this.currentTimeLog = masterTimeLog.getTimeLogForClient(this.currentSelectedClient);
                System.out.println(nameSelected + " has been selected.");
                displayBillingMenu(); //code to continue to BillingCategory
            } else if (nameSelected.equals("q")) {
                break;
            } else {
                System.out.println("Please make a valid selection by entering the exact client name.");
            }
        }
    }

    private void displayClients() {
        if (clientBook.getClients().isEmpty()) {
            System.out.println("The client book is empty. Please return to the main menu and add a client.");
        } else {
            for (Client client : clientBook.getClients()) {
                System.out.println(client.getName());
            }
        }
    }

    private void createClientOption() {
        boolean creationSuccess = false;
        String nameSelected = "";
        while (!creationSuccess) {
            System.out.println("Please enter a name for the new client or type 'q' to quit. "
                    + "Please ensure the name does not already exist.");
            nameSelected = input.nextLine();
            if (clientBook.createClient(nameSelected)) { //if successful
                creationSuccess = true;
                masterTimeLog.createTimeLog(clientBook.getAClient(nameSelected));
                System.out.println(nameSelected + " has been created.");
            } else if (nameSelected.equals("q")) {
                break;
            } else {
                System.out.println("Client name already exists. Please enter a unique name.");
            }
        }
    }

    private void removeClientOption() {
        boolean removalSuccess = false;
        String nameSelected = "";
        while (!removalSuccess) {
            System.out.println("Please enter a client name to remove or type 'q' to quit. ");
            nameSelected = input.nextLine();
            if (clientBook.removeClient(nameSelected)) { //if successful
                removalSuccess = true;
                masterTimeLog.removeTimeLog(nameSelected);
                System.out.println(nameSelected + " has been removed.");
            } else if (nameSelected.equals("q")) {
                break;
            } else {
                System.out.println("Client name does not exist. Please type the exact name.");
            }
        }
    }

    private void editClientOption() {
        boolean editSuccess = false;
        String nameSelected = "";
        while (!editSuccess) {
            System.out.println("Please enter a client name to edit or type 'q' to quit. ");
            nameSelected = input.nextLine();
            System.out.println("Please enter a new client name.");
            String newName = input.nextLine();
            if (clientBook.editClient(nameSelected, newName)) { //if successful
                editSuccess = true;
                System.out.println(nameSelected + " has been changed to " + newName + ".");
            } else if (nameSelected.equals("q")) {
                break;
            } else {
                System.out.println("Client name does not exist. Please type the exact name.");
            }
        }
    }

    private boolean clientIsInClientBook(String name) {
        for (Client client : clientBook.getClients()) {
            if (client.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }
    //END OF CLIENT MENU METHODS
    //_____________________________________________________________________________________________________________
    //START OF BILLING CATEGORY MENU METHODS

    private void displayBillingMenu() {
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

    // EFFECTS: prompts user to select chequing or savings account and returns it
    private void selectBillingCategory() {
        boolean selectedCategoryIsInList = false;
        String nameSelected = "";

        while (!selectedCategoryIsInList) {
            System.out.println("Please select one of the categories below or type 'q' to quit:");
            displayBillingCategories();
            nameSelected = input.nextLine();
            if (categoryIsInBillingCategories(nameSelected)) {
                selectedCategoryIsInList = true;
                this.currentSelectedBillingCategory = billingCategories.getABillingCategory(nameSelected);
                System.out.println(nameSelected + " has been selected.");
                displayTimeEntryMenu(); //continue to Time Entry menu
            } else if (nameSelected.equals("q")) {
                break;
            } else {
                System.out.println("Please make a valid selection by entering the exact billing category name.");
            }
        }
    }

    private void displayBillingCategories() {
        if (billingCategories.getBillingCategoriesForClient(this.currentSelectedClient).isEmpty()) {
            System.out.println("The list of billing categories is empty. "
                    + "Please return to the billing menu and add a category.");
        } else {
            for (BillingCategory category
                    : billingCategories.getBillingCategoriesForClient(this.currentSelectedClient)) {
                System.out.println(category.getName());
            }
        }
    }

    private void createBillingCategoryOption() {
        boolean creationSuccess = false;
        String nameSelected = "";
        while (!creationSuccess) {
            System.out.println("Please enter a name for the new category or type 'q' to quit. "
                    + "Please ensure the name does not already exist.");
            nameSelected = input.nextLine();
            if (nameSelected.equals("q")) {
                break;
            }
            System.out.println("Please enter a rate per hour in dollars. E.g. 16.50 ");
            String ratePerHour = input.nextLine();
            if (billingCategories.createBillingCategory(nameSelected, ratePerHour, this.currentSelectedClient)) {
                creationSuccess = true;
                System.out.println(nameSelected + " has been created.");
            } else {
                System.out.println("Billing category already exists. Please enter a unique name.");
            }
        }
    }

    private void removeBillingCategoryOption() {
        boolean removalSuccess = false;
        String nameSelected = "";
        while (!removalSuccess) {
            System.out.println("Please enter a billing category to remove or type 'q' to quit. ");
            nameSelected = input.nextLine();
            if (billingCategories.removeBillingCategory(nameSelected, this.currentSelectedClient)) {
                removalSuccess = true;
                System.out.println(nameSelected + " has been removed.");
            } else if (nameSelected.equals("q")) {
                break;
            } else {
                System.out.println("Billing category does not exist. Please type the exact name.");
            }
        }
    }

    private void editBillingCategoryOption() {
        boolean editSuccess = false;
        String nameSelected = "";
        while (!editSuccess) {
            System.out.println("Please enter a billing category to edit or type 'q' to quit. ");
            nameSelected = input.nextLine();
            if (nameSelected.equals("q")) {
                break;
            }
            System.out.println("Please enter a new billing category name.");
            String newName = input.nextLine();
            System.out.println("Please enter a new rate per hour. E.g. 16.50");
            String newRatePerHour = input.nextLine();
            if (billingCategories.editBillingCategory(nameSelected, newName,
                    newRatePerHour, this.currentSelectedClient)) {
                editSuccess = true;
                System.out.println(nameSelected + " has been updated.");
            } else {
                System.out.println("Billing category does not exist. Please type the exact name.");
            }
        }
    }

    private boolean categoryIsInBillingCategories(String name) {
        for (BillingCategory category : billingCategories.getBillingCategoriesForClient(this.currentSelectedClient)) {
            if (category.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }

    //END OF BILLING MENU METHODS
    //_____________________________________________________________________________________________________________
    //START OF TIME TRACKING MENU METHODS

    private void displayTimeEntryMenu() {
        boolean keepTimeEntryMenuGoing = true;
        String command = null;

        while (keepTimeEntryMenuGoing) {
            displayTimeEntryMenuOptions();
            command = input.nextLine();
            command = command.toLowerCase();

            if (command.equals("b")) {
                keepTimeEntryMenuGoing = false;
            } else {
                processTimeEntryCommand(command);
            }
        }
        System.out.println("\nReturning to billing menu");
    }

    private void displayTimeEntryMenuOptions() {
        System.out.println("\nTime Entry Menu:");
        System.out.println("\ts -> see time entries");
        System.out.println("\tc -> create time entry");
        System.out.println("\te -> edit time entry");
        System.out.println("\tr -> remove time entry");
        System.out.println("\tb -> back");
    }

    // MODIFIES: this
    // EFFECTS: processes user command
    private void processTimeEntryCommand(String command) {
        if (command.equals("s")) {
            displayTimeEntriesForCategory();
        } else if (command.equals("c")) {
            createTimeEntryOption();
        } else if (command.equals("e")) {
            editTimeEntryOption();
        } else if (command.equals("r")) {
            removeTimeEntryOption();
        } else {
            System.out.println("Please make a valid selection by entering the corresponding letter for your action.");
        }
    }

    private void displayTimeEntriesForCategory() {
        TimeLog clientTimeLog = masterTimeLog.getTimeLogForClient(this.currentSelectedClient);
        ArrayList<TimeEntry> timeEntriesForBillingCategory =
                clientTimeLog.getTimeEntriesForBillingCategory(this.currentSelectedBillingCategory);
        if (timeEntriesForBillingCategory.isEmpty()) {
            System.out.println("There are no time entries for this billing category. "
                    + "Please return to the time tracking menu and add an entry.");
        } else {
            for (TimeEntry entry
                    : timeEntriesForBillingCategory) {
                System.out.println(entry);
            }
        }
    }

    private void createTimeEntryOption() {
        System.out.println("Please enter a name for the time entry or type 'q' to quit.");
        String nameSelected = input.nextLine();
//        if (nameSelected.equals("q")) {
//            return;
//        }
        System.out.println("Please enter a description.");
        String desc = input.nextLine();

        System.out.println("Please enter a start date and 24 hour time in the format: yyyy-MM-dd HH:mm");
        String startDateTime = input.nextLine();
        System.out.println("Please enter a end date and 24 hour time in the format: yyyy-MM-dd HH:mm");
        String endDateTime = input.nextLine();

        currentTimeLog.createTimeEntry(nameSelected, desc,
                startDateTime, endDateTime, this.currentSelectedBillingCategory);
        System.out.println(nameSelected + " has been created.");
//      currentTimeLog.createTimeEntry("Test", "Test Description",
//      "2020-10-10 10:30", "2020-10-10 11:30", this.currentSelectedBillingCategory);
    }

    private void removeTimeEntryOption() {
        boolean removalSuccess = false;
        String nameSelected = "";
        while (!removalSuccess) {
            System.out.println("Please enter a time entry to remove or type 'q' to quit. ");
            nameSelected = input.nextLine();
            if (currentTimeLog.removeTimeEntry(nameSelected, this.currentSelectedBillingCategory)) { //if successful
                removalSuccess = true;
                System.out.println(nameSelected + " has been removed.");
            } else if (nameSelected.equals("q")) {
                break;
            } else {
                System.out.println("Time entry does not exist. Please type the exact name.");
            }
        }
    }

    private void editTimeEntryOption() {
        boolean editSuccess = false;
        while (!editSuccess) {
            System.out.println("Please enter a time entry to edit or type 'q' to quit. ");
            String nameSelected = input.nextLine();
            if (nameSelected.equals("q")) {
                break;
            }
            System.out.println("Please enter a new time entry name.");
            String newName = input.nextLine();
            System.out.println("Please enter a new description for the time entry.");
            String description = input.nextLine();
            System.out.println("Enter a start date and 24 hour time in the format: yyyy-MM-dd HH:mm");
            String startDateTime = input.nextLine();
            System.out.println("Enter a end date and 24 hour time in the format: yyyy-MM-dd HH:mm");
            String endDateTime = input.nextLine();
            if (currentTimeLog.editTimeEntry(nameSelected, newName, description,
                    startDateTime, endDateTime, this.currentSelectedBillingCategory)) {
                editSuccess = true;
                System.out.println(nameSelected + " has been updated.");
            } else {
                System.out.println("Time entry does not exist. Please type the exact name.");
            }
        }
    }

    //END OF TIME TRACKING MENU METHODS

}
