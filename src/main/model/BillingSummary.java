package model;

import java.util.ArrayList;

public class BillingSummary {
    private Client client;
    private TimeLog log;

    public BillingSummary(Client client, TimeLog log) {
        this.client = client;
        this.log = log;
    }

    public ArrayList<TimeEntry> printSummary() {
        return log.getTimeEntries();
    }
}
