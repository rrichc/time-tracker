package model;

import java.util.ArrayList;
import java.util.List;


//TODO: Add a class description for TimeLog
public class TimeLog {
    private Client client;
    private ArrayList<TimeEntry> timeLog;

    public TimeLog(Client client) {
        this.timeLog = new ArrayList<TimeEntry>();
        this.client = client;

    }

    //requires date strings to be in the right format
    public void createTimeEntry(String name, String description, String startDateTime,
                                String endDateTime, BillingCategory category) {
        this.timeLog.add(new TimeEntry(name, description, startDateTime, endDateTime, category));
    }

    public void removeTimeEntry(String name) {
        for (TimeEntry entry : timeLog) {
            if (entry.getName() == name) {
                timeLog.remove(entry);
            }
        }
    }

//    public void removeTimeEntry(int position) {
//        timeLog.remove(position);
//    }

    //requires date strings to be in the right format
    public void editTimeEntry(String oldName, String name, String description, String startDateTime,
                              String endDateTime, BillingCategory category) {
        createTimeEntry(name, description, startDateTime, endDateTime, category);
        removeTimeEntry(oldName);
    }

    public ArrayList<TimeEntry> getTimeEntries() {
        return timeLog;
    }

    public Client getClient() {
        return client;
    }
}
