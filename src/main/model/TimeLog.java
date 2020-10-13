package model;

import java.util.ArrayList;


//TimeLog represents a collection of time entries associated with a specific client
public class TimeLog {
    private Client client;
    private ArrayList<TimeEntry> timeLog;

    /*
     * REQUIRES: client must have non-empty fields
     * EFFECTS: initializes an empty ArrayList holding BillingCategory objects
     */
    public TimeLog(Client client) {
        this.timeLog = new ArrayList<TimeEntry>();
        this.client = client;

    }

    /*
     * REQUIRES: BillingCategory must have non-empty fields,
     * MODIFIES: this
     * EFFECTS:
     */
    public void createTimeEntry(String name, String description, String startDateTime,
                                String endDateTime, BillingCategory category) {
        this.timeLog.add(new TimeEntry(name, description, startDateTime, endDateTime, category));
    }

    //Make sure to test both orders of the && for full coverage
    public boolean removeTimeEntry(String name, BillingCategory category) {
        for (TimeEntry entry : timeLog) {
            if (entry.getName().equals(name) && entry.getCategory().getName().equals(category.getName())) {
                timeLog.remove(entry);
                return true;
            }
        }
        return false;
    }

    //requires date strings to be in the right format
    //Make sure to test both orders of the && for full coverage
    public boolean editTimeEntry(String oldName, String name, String description, String startDateTime,
                              String endDateTime, BillingCategory category) {
        for (TimeEntry entry : timeLog) {
            if (entry.getName().equals(oldName) && entry.getCategory().getName().equals(category.getName())) {
                createTimeEntry(name, description, startDateTime, endDateTime, category);
                removeTimeEntry(oldName, category);
                return true;
            }
        }
        return false;
    }

    public ArrayList<TimeEntry> getTimeEntries() {
        return timeLog;
    }

    public Client getClient() {
        return client;
    }

    public ArrayList<TimeEntry> getTimeEntriesForBillingCategory(BillingCategory category) {
        ArrayList<TimeEntry> entriesForUser = new ArrayList<TimeEntry>();
        for (TimeEntry entry : timeLog) {
            if (entry.getCategory().getName().equals(category.getName())) {
                entriesForUser.add(entry);
            }
        }
        return entriesForUser;
    }
}
