package ui;

import model.*;

import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class TimeEntryMenu {

    private Scanner input;
    private MasterTimeLog masterTimeLog;
    private BillingCategories billingCategories;
    private ClientBook clientBook;
    private Client currentClient;
    private BillingCategory currentBillingCategory;
    private TimeLog currentTimeLog;

    public TimeEntryMenu(Scanner input, MasterTimeLog masterTimeLog, BillingCategories billingCategories,
                         ClientBook clientBook, Client currentClient, BillingCategory currentBillingCategory,
                         TimeLog currentTimeLog) {
        this.input = input;
        this.masterTimeLog = masterTimeLog;
        this.billingCategories = billingCategories;
        this.clientBook = clientBook;
        this.currentClient = currentClient;
        this.currentBillingCategory = currentBillingCategory;
        this.currentTimeLog = currentTimeLog;
    }

    public void displayTimeEntryMenu() {
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
        TimeLog clientTimeLog = masterTimeLog.getTimeLogForClient(this.currentClient);
        ArrayList<TimeEntry> timeEntriesForBillingCategory =
                clientTimeLog.getTimeEntriesForBillingCategory(this.currentBillingCategory);
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

    //TODO: Handle any date exceptions (format & end date before start date)
    private void createTimeEntryOption() {
        System.out.println("Please enter a name for the time entry or type 'q' to quit.");
        String nameSelected = input.nextLine();
        if (nameSelected.equals("q")) {
            return;
        }
        System.out.println("Please enter a description.");
        String desc = input.nextLine();

        System.out.println("Please enter a start date and 24 hour time formatted as: yyyy-MM-dd HH:mm");
        String startDateTime = input.nextLine();
        System.out.println("Please enter a end date and 24 hour time formatted as: yyyy-MM-dd HH:mm");
        String endDateTime = input.nextLine();
        try {
            currentTimeLog.createTimeEntry(nameSelected, desc,
                    startDateTime, endDateTime, this.currentBillingCategory);
            System.out.println(nameSelected + " has been created.");
        } catch (DateTimeParseException e) {
            System.out.println("Entry not created. Please enter the dates in the correct format.");
        }
    }

    private void removeTimeEntryOption() {
        boolean removalSuccess = false;
        while (!removalSuccess) {
            System.out.println("Please enter a time entry to remove or type 'q' to quit. ");
            String nameSelected = input.nextLine();
            if (nameSelected.equals("q")) {
                break;
            }
            if (currentTimeLog.removeTimeEntry(nameSelected, this.currentBillingCategory)) { //if successful
                removalSuccess = true;
                System.out.println(nameSelected + " has been removed.");
            } else {
                System.out.println("Time entry does not exist. Please type the exact name.");
            }
        }
    }

    private List<String> editTimeEntryGetInput() {

        System.out.println("Please enter a new time entry name.");
        String newName = input.nextLine();
        System.out.println("Please enter a new description for the time entry.");
        String description = input.nextLine();
        System.out.println("Enter a start date and 24 hour time formatted as: yyyy-MM-dd HH:mm");
        String startDateTime = input.nextLine();
        System.out.println("Enter a end date and 24 hour time formatted as: yyyy-MM-dd HH:mm");
        String endDateTime = input.nextLine();
        return Arrays.asList(newName, description, startDateTime, endDateTime);
    }

    //TODO: Handle any date exceptions (format & end date before start date)
    private void editTimeEntryOption() {
        boolean editSuccess = false;
        while (!editSuccess) {
            System.out.println("Please enter a time entry to edit or type 'q' to quit. ");
            String nameSelected = input.nextLine();
            if (nameSelected.equals("q")) {
                break;
            }
            List<String> input = editTimeEntryGetInput();
            try {
                if (currentTimeLog.editTimeEntry(nameSelected, input.get(0), input.get(1),
                        input.get(2), input.get(3), this.currentBillingCategory)) {
                    editSuccess = true;
                    System.out.println(nameSelected + " has been updated.");
                } else {
                    System.out.println("Time entry does not exist. Please type the exact name.");
                }
            } catch (DateTimeParseException e) {
                System.out.println("Entry not created. Please enter the dates in the correct format.");
            }
        }
    }

    //                if (currentTimeLog.editTimeEntry(nameSelected, newName, description,
    //                        startDateTime, endDateTime, this.currentBillingCategory)) {
    //                    editSuccess = true;

    //END OF TIME TRACKING MENU METHODS
}
