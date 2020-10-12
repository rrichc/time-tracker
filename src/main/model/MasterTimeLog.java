package model;

import java.util.ArrayList;

//TODO: Add a class description for MasterTimeLog
public class MasterTimeLog {
    private ArrayList<TimeLog> masterTimeLog;

    public MasterTimeLog() {
        this.masterTimeLog = new ArrayList<TimeLog>();
    }

    //created when a new client is created
    public void createTimeLog(Client client) {
        this.masterTimeLog.add(new TimeLog(client));
    }

    //created when a client is deleted
    public void removeTimeLog(String name) {
        for (TimeLog log : masterTimeLog) {
            if (log.getClient().getName().equals(name)) {
                masterTimeLog.remove(log);
            }
        }
    }

    public TimeLog getTimeLogForClient(Client client) {
        for (TimeLog log : masterTimeLog) {
            if (log.getClient().getName().equals(client.getName())) {
                return log;
            }

        }
        return null;
    }
}
