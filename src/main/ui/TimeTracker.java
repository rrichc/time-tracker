package ui;

import model.*;

import java.util.Scanner;

//This UI class is based on the UI class in the example CPSC 210 TellerApp provided by Paul Carter
//https://github.students.cs.ubc.ca/CPSC210/TellerApp
//TimeTracker starts the
public class TimeTracker {
    private Scanner input;
    private MasterTimeLog masterTimeLog;
    private BillingCategories billingCategories;
    private ClientBook clientBook;
    private ClientMenu clientMenu;

    /*
     * EFFECTS: Starts the Time Tracking Application
     */
    public TimeTracker() {
        runTimeTracker();
    }

    /*
     * EFFECTS: Initializes necessary objects and displays the main/client menu
     */
    public void runTimeTracker() {
        init();
        clientMenu.displayClientMenu();

    }

    /*
     * MODIFIES: this
     * EFFECTS: Initializes dependencies needed by the Client, Billing, and Time Entry menu down the line
     */
    private void init() {
        this.input = new Scanner(System.in);
        this.masterTimeLog = new MasterTimeLog();
        this.billingCategories = new BillingCategories();
        this.clientBook = new ClientBook();
        this.clientMenu = new ClientMenu(this.input, this.masterTimeLog, this.billingCategories,
                this.clientBook);
    }
}
