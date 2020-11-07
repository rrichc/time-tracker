package ui;

import model.*;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.util.Scanner;

//This UI class is based on the UI class in the example CPSC 210 TellerApp provided by Paul Carter
//https://github.students.cs.ubc.ca/CPSC210/TellerApp
//TimeTracker starts the Time Tracking Application
public class TimeTracker {
    public static final String CLIENT_JSON_STORE = "./data/clientBook.json";
    public static final String BILLING_JSON_STORE = "./data/billingCategories.json";
    public static final String TIME_JSON_STORE = "./data/masterTimeLog.json";
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
     * Makes use of code from https://docs.oracle.com/javase/tutorial/uiswing/components/tabbedpane.html
     * EFFECTS: Initializes necessary objects and displays the main/client menu
     */
    public void runTimeTracker() {
        init();
//        clientMenu.displayClientMenu();
        try {
            //UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
            UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
        } catch (UnsupportedLookAndFeelException ex) {
            ex.printStackTrace();
        } catch (IllegalAccessException ex) {
            ex.printStackTrace();
        } catch (InstantiationException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        /* Turn off metal's use of bold fonts */
        UIManager.put("swing.boldMetal", Boolean.FALSE);

        //Schedule a job for the event dispatch thread:
        //creating and showing this application's GUI.
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
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

    /**
     * Method taken from https://docs.oracle.com/javase/tutorial/uiswing/components/tabbedpane.html
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event dispatch thread.
     */
    private static void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("Time Tracker");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Create and set up the content pane.
        MenuTabs demo = new MenuTabs();
        demo.addComponentToPane(frame.getContentPane());

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }
}
