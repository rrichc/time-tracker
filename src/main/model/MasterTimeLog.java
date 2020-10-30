package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;

//MasterTimeLog represents a collection of TimeLogs
public class MasterTimeLog implements Writable {

    private ArrayList<TimeLog> masterTimeLog;   //represents the collection of TimeLogs

    /*
     * EFFECTS: initializes an empty ArrayList holding TimeLog objects
     */
    public MasterTimeLog() {
        this.masterTimeLog = new ArrayList<TimeLog>();
    }

    /*
     * REQUIRES: client must have non-empty fields
     * EFFECTS: Adds a new TimeLog to the collection
     */
    public void createTimeLog(Client client) {
        this.masterTimeLog.add(new TimeLog(client));
    }

    /*
     * REQUIRES: name must be a non-empty string
     * EFFECTS: Removes a new TimeLog from the collection
     */
    public void removeTimeLog(String name) {
        ArrayList toRemove = new ArrayList();
        for (TimeLog log : masterTimeLog) {
            if (log.getClient().getName().equals(name)) {
                toRemove.add(log);
            }
        }
        masterTimeLog.removeAll(toRemove);
    }

    /*
     * REQUIRES: client must have non-empty fields
     * EFFECTS: Returns the TimeLog for that client, otherwise returns null
     */
    public TimeLog getTimeLogForClient(Client client) {
        for (TimeLog log : masterTimeLog) {
            if (log.getClient().getName().equals(client.getName())) {
                return log;
            }

        }
        return null;
    }

    /*
     * EFFECTS: Returns entire collection of TimeLogs
     */
    public ArrayList<TimeLog> getMasterTimeLog() {
        return masterTimeLog;
    }

    /*
     * EFFECTS:  Returns an representation of the MasterTimeLog as a JSONObject
     */
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        for (TimeLog timeLog : masterTimeLog) {
            jsonArray.put(timeLog.toJson());
        }
        json.put("masterTimeLog", jsonArray);
        return json;
    }
}
