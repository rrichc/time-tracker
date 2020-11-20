package ui;

import javax.swing.*;

//This UI class is based on the UI class in the example CPSC 210 TellerApp provided by Paul Carter
//https://github.students.cs.ubc.ca/CPSC210/TellerApp
//TimeTracker starts the Time Tracking Application
public class TimeTracker {

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
        try {
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

    /**
     * Method taken from https://docs.oracle.com/javase/tutorial/uiswing/components/tabbedpane.html
     * EFFECTS: Create the GUI and show it.  For thread safety, this method should be invoked from the
     * event dispatch thread.
     */
    private static void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("Time Tracker");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Create and set up the content pane.
        MenuTabs demo = new MenuTabs();
        demo.addMenuTabsToPane(frame.getContentPane());

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }
}
