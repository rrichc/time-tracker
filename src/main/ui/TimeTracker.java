package ui;

import model.*;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.util.Scanner;

//This UI class is based on the UI class in the example CPSC 210 TellerApp provided by Paul Carter
//https://github.students.cs.ubc.ca/CPSC210/TellerApp
//TimeTracker starts the Time Tracking Application
public class TimeTracker {
    public static final String CLIENT_JSON_STORE = "./data/clientbook.json";
    public static final String BILLING_JSON_STORE = "./data/billingcategories.json";
    public static final String TIME_JSON_STORE = "./data/mastertimelog.json";
    private Scanner input;
    private MasterTimeLog masterTimeLog;
    private BillingCategories billingCategories;
    private ClientBook clientBook;
    private ClientMenu clientMenu;
    private JsonReader jsonReader;
    private JsonWriter jsonWriter;

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
        this.jsonReader = new JsonReader();
        this.jsonWriter = new JsonWriter();
        this.clientMenu = new ClientMenu(this.input, this.masterTimeLog, this.billingCategories,
                this.clientBook, this.jsonReader, this.jsonWriter);
    }
}
