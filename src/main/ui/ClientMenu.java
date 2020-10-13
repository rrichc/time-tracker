package ui;

import model.*;

import java.util.Scanner;

public class ClientMenu {

    private Scanner input;
    private MasterTimeLog masterTimeLog;
    private BillingCategories billingCategories;
    private ClientBook clientBook;
    private Client currentClient;
    private TimeLog currentTimeLog;
    private BillingMenu billingMenu;

    public ClientMenu(Scanner input, MasterTimeLog masterTimeLog, BillingCategories billingCategories,
                      ClientBook clientBook) {
        this.input = input;
        this.masterTimeLog = masterTimeLog;
        this.billingCategories = billingCategories;
        this.clientBook = clientBook;
    }

    //START OF CLIENT MENU METHODS
    public void displayClientMenu() {
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
        while (!creationSuccess) {
            System.out.println("Please enter a name for the new client or type 'q' to quit.");
            String nameSelected = input.nextLine();
            if (nameSelected.equals("q")) {
                break;
            }
            if (clientBook.createClient(nameSelected)) { //if successful
                creationSuccess = true;
                masterTimeLog.createTimeLog(clientBook.getAClient(nameSelected));
                System.out.println(nameSelected + " has been created.");
            } else {
                System.out.println("Client name already exists. Please enter a unique name.");
            }
        }
    }

    private void removeClientOption() {
        boolean removalSuccess = false;

        while (!removalSuccess) {
            System.out.println("Please enter a client name to remove or type 'q' to quit. ");
            String nameSelected = input.nextLine();
            if (nameSelected.equals("q")) {
                break;
            }
            if (clientBook.removeClient(nameSelected)) { //if successful
                removalSuccess = true;
                masterTimeLog.removeTimeLog(nameSelected);
                System.out.println(nameSelected + " has been removed.");
            } else {
                System.out.println("Client name does not exist. Please type the exact name.");
            }
        }
    }

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

    private boolean clientIsInClientBook(String name) {
        for (Client client : clientBook.getClients()) {
            if (client.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }
    //END OF CLIENT MENU METHODS
}
