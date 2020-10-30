package persistence;

import org.json.JSONObject;

//Enforces the toJson method on all types that need to be written to a JSON file
public interface Writable {
    // EFFECTS: returns this as JSON object
    JSONObject toJson();
}
