package ui;

import model.*;

import java.util.Scanner;

//This UI class is based on the UI class in the example CPSC 210 TellerApp provided by Paul Carter
//https://github.students.cs.ubc.ca/CPSC210/TellerApp
public class TimeTracker {
    private Scanner input;
    private MasterTimeLog masterTimeLog;
    private BillingCategories billingCategories;
    private ClientBook clientBook;
    private ClientMenu clientMenu;

    public TimeTracker() {
        runTimeTracker();
    }

    public void runTimeTracker() {
        init();
        clientMenu.displayClientMenu();

    }

    private void init() {
        this.input = new Scanner(System.in);
        this.masterTimeLog = new MasterTimeLog();
        this.billingCategories = new BillingCategories();
        this.clientBook = new ClientBook();
        this.clientMenu = new ClientMenu(this.input, this.masterTimeLog, this.billingCategories,
                this.clientBook);
    }
}
