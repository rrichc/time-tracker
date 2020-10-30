package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;


//TimeLog represents a collection of time entries associated with a specific client
public class TimeLog implements Writable {
    private Client client;                  //the client associated with a TimeLog
    private ArrayList<TimeEntry> timeLog;   //collection of time entries

    /*
     * REQUIRES: client must have non-empty fields
     * EFFECTS: initializes an empty ArrayList holding TimeEntry objects
     */
    public TimeLog(Client client) {
        this.timeLog = new ArrayList<TimeEntry>();
        this.client = client;
    }

    /*
     * REQUIRES: category must have non-empty fields, name and description must be non-empty strings.
     *           startDateTime and endDateTime String must be in the format "1986-04-08 12:30"
     *           where the year, month, and day must be greater than 1, and the month must be less than 13,
     *           and day must be less than 31. startDateTime must be before endDateTime.
     * MODIFIES: this
     * EFFECTS:  Adds a new time entry to the collection of entries
     */
    public void createTimeEntry(String name, String description, String startDateTime,
                                String endDateTime, BillingCategory category) {
        this.timeLog.add(new TimeEntry(name, description, startDateTime, endDateTime, category));
    }

    /*
     * REQUIRES: category must have non-empty fields, and name must be a non-empty string
     * MODIFIES: this
     * EFFECTS:  Removes a new time entry to the collection of entries
     */
    public boolean removeTimeEntry(String name, BillingCategory category) {
        for (TimeEntry entry : timeLog) {
            if (entry.getName().equals(name) && entry.getCategory().getName().equals(category.getName())) {
                timeLog.remove(entry);
                return true;
            }
        }
        return false;
    }

    /*
     * REQUIRES: category must have non-empty fields, oldName & name & description must be non-empty strings.
     *           startDateTime and endDateTime String must be in the format "1986-04-08 12:30"
     *           where the year, month, and day must be greater than 1, and the month must be less than 13,
     *           and day must be less than 31. startDateTime must be before endDateTime.
     * MODIFIES: this
     * EFFECTS:  Edits an existing time entry
     */
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

    /*
     * EFFECTS:  Returns the time log of entries
     */
    public ArrayList<TimeEntry> getTimeEntries() {
        return timeLog;
    }

    /*
     * EFFECTS:  Returns the client associated with the time log
     */
    public Client getClient() {
        return client;
    }

    /*
     * REQUIRES: category must have non-empty fields
     * EFFECTS:  Returns the time entries for a particular billing category
     */
    public ArrayList<TimeEntry> getTimeEntriesForBillingCategory(BillingCategory category) {
        ArrayList<TimeEntry> entriesForUser = new ArrayList<TimeEntry>();
        for (TimeEntry entry : timeLog) {
            if (entry.getCategory().getName().equals(category.getName())) {
                entriesForUser.add(entry);
            }
        }
        return entriesForUser;
    }

    /*
     * EFFECTS:  Returns an representation of the timeLog array as a JSONObject
     */
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        for (TimeEntry timeEntry : timeLog) {
            jsonArray.put(timeEntry.toJson());
        }
        json.put("clientTimeLog", client.getName());
        json.put("timeEntries", jsonArray);
        return json;
    }
}
