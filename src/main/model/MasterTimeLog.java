package model;

import java.util.ArrayList;

//TODO: Add a class description for MasterTimeLog
public class MasterTimeLog {
    private ArrayList<TimeLog> masterTimeLog;

    public MasterTimeLog() {
        this.masterTimeLog = new ArrayList<TimeLog>();
    }

    public void createTimeLog(Client client) {
        this.masterTimeLog.add(new TimeLog(client));
    }

    public void removeTimeEntry(String name) {
        for (TimeLog log : masterTimeLog) {
            if (log.getClient().getName().equals(name)) {
                masterTimeLog.remove(log);
            }
        }
    }
}
