package ui;

import model.*;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

//ClientMenu represents the first menu users see when they open the application to add, edit, remove
//and select clients to perform actions on. They can also choose to save and load the time tracking environment
public class ClientMenu {

    private Scanner input;
    private MasterTimeLog masterTimeLog;
    private BillingCategories billingCategories;
    private ClientBook clientBook;
    private Client currentClient;
    private TimeLog currentTimeLog;
    private BillingMenu billingMenu;
    private JsonReader jsonReader;
    private JsonWriter jsonWriter;

    /*
     * EFFECTS: Gets the dependencies initialized in TimeTracker and assigns it to class fields
     */
    public ClientMenu(Scanner input, MasterTimeLog masterTimeLog, BillingCategories billingCategories,
                      ClientBook clientBook, JsonReader jsonReader, JsonWriter jsonWriter) {
        this.input = input;
        this.masterTimeLog = masterTimeLog;
        this.billingCategories = billingCategories;
        this.clientBook = clientBook;
        this.jsonReader = jsonReader;
        this.jsonWriter = jsonWriter;
    }

    /*
     * EFFECTS: Gets user input for the client action desired and process that command
     */
    public void displayClientMenu() {
        boolean keepClientMenuGoing = true;

        while (keepClientMenuGoing) {
            displayClientMenuOptions();
            String command = input.nextLine();
            command = command.toLowerCase();

            if (command.equals("q")) {
                keepClientMenuGoing = false;
            } else {
                processClientCommand(command);
            }
        }
        System.out.println("\nThank you for using Time Tracker.");
    }

    /*
     * EFFECTS: Displays the client menu options for desired actions
     */
    private void displayClientMenuOptions() {
        System.out.println("\nClient Menu:");
        System.out.println("\ts -> select client");
        System.out.println("\tc -> create new client");
        System.out.println("\te -> edit client");
        System.out.println("\tr -> remove client");
        System.out.println("\tsa -> save to file");
        System.out.println("\tl -> load from file");
        System.out.println("\tq -> quit");
    }

    /*
     * EFFECTS: Executes the desired client action according to user's input
     */
    private void processClientCommand(String command) {
        if (command.equals("s")) {
            selectClient();
        } else if (command.equals("c")) {
            createClientOption();
        } else if (command.equals("e")) {
            editClientOption();
        } else if (command.equals("r")) {
            removeClientOption();
        } else if (command.equals("sa")) {
            saveWorkRoom();
        } else if (command.equals("l")) {
            loadSave();
        } else {
            System.out.println("Please make a valid selection by entering the corresponding letter for your action.");
        }
    }

    /*
     * MODIFIES: this
     * EFFECTS: Sets the client that the user has selected according to user input,
     *          and assigns it as the current active client.
     *          Proceeds to the BillingMenu based on the currently selected client
     */
    private void selectClient() {
        boolean selectedClientIsInList = false;
        while (!selectedClientIsInList) {
            System.out.println("Please select one of the clients below or type 'q' to quit:");
            displayClients();
            String nameSelected = input.nextLine();
            if (clientIsInClientBook(nameSelected)) {
                selectedClientIsInList = true;
                this.currentClient = clientBook.getAClient(nameSelected);
                this.currentTimeLog = masterTimeLog.getTimeLogForClient(this.currentClient);
                System.out.println(nameSelected + " has been selected.");
                this.billingMenu = new BillingMenu(this.input, this.masterTimeLog, this.billingCategories,
                        this.clientBook, this.currentClient, this.currentTimeLog);
                billingMenu.displayBillingMenu(); //code to continue to BillingCategory
            } else if (nameSelected.equals("q")) {
                break;
            } else {
                System.out.println("Please make a valid selection by entering the exact client name.");
            }
        }
    }

    /*
     * EFFECTS: Displays the clients the user has in their client book. Otherwise displays a message
     *          stating their client book is empty.
     */
    private void displayClients() {
        if (clientBook.getClients().isEmpty()) {
            System.out.println("The client book is empty. Please return to the main menu and add a client.");
        } else {
            for (Client client : clientBook.getClients()) {
                System.out.println(client.getName());
            }
        }
    }

    /*
     * MODIFIES: this
     * EFFECTS: Creates a new client in the client book based on the name the user has entered
     */
    private void createClientOption() {
        boolean creationSuccess = false;
        while (!creationSuccess) {
            System.out.println("Please enter a name for the new client or type 'q' to quit.");
            String nameSelected = input.nextLine();
            if (nameSelected.equals("q")) {
                break;
            }
            if (clientBook.createClient(nameSelected)) {
                creationSuccess = true;
                masterTimeLog.createTimeLog(clientBook.getAClient(nameSelected));
                System.out.println(nameSelected + " has been created.");
            } else {
                System.out.println("Client name already exists. Please enter a unique name.");
            }
        }
    }

    /*
     * MODIFIES: this
     * EFFECTS: Removes a client in the client book based on the name the user has entered
     */
    private void removeClientOption() {
        boolean removalSuccess = false;

        while (!removalSuccess) {
            System.out.println("Please enter a client name to remove or type 'q' to quit. ");
            String nameSelected = input.nextLine();
            if (nameSelected.equals("q")) {
                break;
            }
            if (clientBook.removeClient(nameSelected)) {
                removalSuccess = true;
                masterTimeLog.removeTimeLog(nameSelected);
                System.out.println(nameSelected + " has been removed.");
            } else {
                System.out.println("Client name does not exist. Please type the exact name.");
            }
        }
    }

    /*
     * MODIFIES: this
     * EFFECTS: Edits a client in the client book based on the name the user has entered, and new name entered
     */
    private void editClientOption() {
        boolean editSuccess = false;
        while (!editSuccess) {
            System.out.println("Please enter a client name to edit or type 'q' to quit. ");
            String nameSelected = input.nextLine();
            if (nameSelected.equals("q")) {
                break;
            }
            System.out.println("Please enter a new client name.");
            String newName = input.nextLine();
            if (clientBook.editClient(nameSelected, newName)) { //if successful
                editSuccess = true;
                System.out.println(nameSelected + " has been changed to " + newName + ".");
            } else {
                System.out.println("Client name does not exist. Please type the exact name.");
            }
        }
    }

    /*
     * EFFECTS: Checks if a client is in the client book depending on the name provided
     *          and returns true if it is, false if not
     */
    private boolean clientIsInClientBook(String name) {
        for (Client client : clientBook.getClients()) {
            if (client.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }

    // EFFECTS: loads ClientBook, BillingCategories, MasterTimeLog from JSON file
    private void loadSave() {
        loadClientBook();
        loadBillingCategories();
        loadMasterTimeLog();
    }

    // MODIFIES: this
    // EFFECTS: loads ClientBook from file
    private void loadClientBook() {
        try {
            this.clientBook = jsonReader.readClientBook(MenuTabs.CLIENT_JSON_STORE);
            System.out.println("Loaded client book from last save.");
        } catch (IOException e) {
            System.out.println("Unable to read client book from file.");
        }
    }

    // MODIFIES: this
    // EFFECTS: loads BillingCategories from file
    private void loadBillingCategories() {
        try {
            this.billingCategories = jsonReader.readBillingCategories(MenuTabs.BILLING_JSON_STORE, this.clientBook);
            System.out.println("Loaded billing categories from last save.");
        } catch (IOException e) {
            System.out.println("Unable to read billing categories from file.");
        }
    }

    // MODIFIES: this
    // EFFECTS: loads MasterTimeLog from file
    private void loadMasterTimeLog() {
        try {
            this.masterTimeLog = jsonReader.readMasterTimeLog(MenuTabs.TIME_JSON_STORE,
                    this.clientBook, this.billingCategories);
            System.out.println("Loaded time entries from last save.");
        } catch (IOException e) {
            System.out.println("Unable to read time entries from file.");
        }
    }

    // EFFECTS: saves the workroom to file
    private void saveWorkRoom() {
        try {
            jsonWriter.open(MenuTabs.CLIENT_JSON_STORE);
            jsonWriter.write(this.clientBook);
            jsonWriter.close();
            jsonWriter.open(MenuTabs.BILLING_JSON_STORE);
            jsonWriter.write(this.billingCategories);
            jsonWriter.close();
            jsonWriter.open(MenuTabs.TIME_JSON_STORE);
            jsonWriter.write(this.masterTimeLog);
            jsonWriter.close();
            System.out.println("Saved");
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file.");
        }
    }
}
