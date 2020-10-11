package ui;

import model.Client;
import model.MasterTimeLog;
import model.BillingCategories;
import model.ClientBook;

import java.util.Scanner;


//This UI class is based on the UI class in the example CPSC 210 TellerApp provided by Paul Carter
//https://github.students.cs.ubc.ca/CPSC210/TellerApp
public class TimeTracker {
    private Scanner input;
    private MasterTimeLog masterTimeLog;
    private BillingCategories billingCategories;
    private ClientBook clientBook;
    private String currentSelectedClient;

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
        this.currentSelectedClient = "";
    }

    //START OF CLIENT MENU METHODS
    private void displayClientMenu() {
        boolean keepGoing = true;
        String command = null;

        while (keepGoing) {
            displayClientMenuOptions();
            command = input.next();
            command = command.toLowerCase();

            if (command.equals("q")) {
                keepGoing = false;
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

    // EFFECTS: prompts user to select chequing or savings account and returns it
    private void selectClient() {
        boolean selectedClientIsInList = false;
        String nameSelected = "";

        while (!selectedClientIsInList) {
            System.out.println("Please select one of the clients below or type 'q' to quit:");
            displayClients();
            nameSelected = input.next();
            if (clientIsInClientBook(nameSelected)) {
                selectedClientIsInList = true;
                this.currentSelectedClient = nameSelected;
                System.out.println(nameSelected + " has been selected.");
                //TODO: Add code to continue to BillingCategory
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
            System.out.println("Please a name for the new client or type 'q' to quit. "
                    + "Please ensure the name does not already exist.");
            nameSelected = input.next();
            if (clientBook.createClient(nameSelected)) { //if successful
                creationSuccess = true;
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
            System.out.println("Please a client name to remove or type 'q' to quit. ");
            nameSelected = input.next();
            if (clientBook.removeClient(nameSelected)) { //if successful
                removalSuccess = true;
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
            nameSelected = input.next();
            System.out.println("Please enter a new client name.");
            String newName = input.next();
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

    //START OF BILLING CATEGORY MENU METHODS


}
