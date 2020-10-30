package persistence;

import model.*;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

//This class is based heavily off of the https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo provided as an example
// Represents a reader that reads workroom from JSON data stored in file
public class JsonReader {
    private ClientBook clientBook;
    private BillingCategories billingCategories;

    // EFFECTS: constructs reader
    public JsonReader() {
    }

    // EFFECTS: reads ClientBook from file and returns it;
    // throws IOException if an error occurs reading data from file
    public ClientBook readClientBook(String source) throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseClientBook(jsonObject);
    }

    // EFFECTS: reads BillCategories from file and returns it;
    // throws IOException if an error occurs reading data from file
    public BillingCategories readBillingCategories(String source, ClientBook clientBook) throws IOException {
        this.clientBook = clientBook;
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseBillingCategories(jsonObject);
    }

    // EFFECTS: reads MasterTimeLog from file and returns it;
    // throws IOException if an error occurs reading data from file
    public MasterTimeLog readMasterTimeLog(String source, ClientBook clientBook,
                                           BillingCategories billingCategories) throws IOException {
        this.clientBook = clientBook;
        this.billingCategories = billingCategories;
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseMasterTimeLog(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    //Start of Client loading methods
    // EFFECTS: parses ClientBook from JSON object and returns it
    private ClientBook parseClientBook(JSONObject jsonObject) {
        ClientBook clientBook = new ClientBook();
        addClients(clientBook, jsonObject);
        return clientBook;
    }

    // MODIFIES: clientBook
    // EFFECTS: parses clients from JSON object and adds them to clientBook
    private void addClients(ClientBook clientBook, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("clients");
        for (Object json : jsonArray) {
            JSONObject nextClient = (JSONObject) json;
            addClient(clientBook, nextClient);
        }
    }

    // MODIFIES: clientBook
    // EFFECTS: parses individual clients from JSON object and adds it to clientBook
    private void addClient(ClientBook clientBook, JSONObject jsonObject) {
        String name = jsonObject.getString("clientName");
        clientBook.createClient(name);
    }
    //End of Client loading methods

    //__________________________________________________________________________________________

    //Start of BillingCategories loading methods
    // EFFECTS: parses BillingCategories from JSON object and returns it
    private BillingCategories parseBillingCategories(JSONObject jsonObject) {
        BillingCategories billingCategories = new BillingCategories();
        addCategories(billingCategories, jsonObject);
        return billingCategories;
    }

    // MODIFIES: billingCategories
    // EFFECTS: parses BillingCategories from JSON object and adds them to BillingCategories
    private void addCategories(BillingCategories billingCategories, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("billingCategories");
        for (Object json : jsonArray) {
            JSONObject nextCategory = (JSONObject) json;
            addCategory(billingCategories, nextCategory);
        }
    }

    // MODIFIES: billingCategories
    // EFFECTS: parses individual categories from JSON object and adds it to BillingCategories
    private void addCategory(BillingCategories billingCategories, JSONObject jsonObject) {
        String name = jsonObject.getString("category");
        String ratePerHour = jsonObject.getString("ratePerHour");
        String clientName = jsonObject.getString("client");
        Client client = clientBook.getAClient(clientName);
        billingCategories.createBillingCategory(name, ratePerHour, client);
    }
    //End of BillingCategories loading methods

    //__________________________________________________________________________________________

    //Start of MasterTimeLog loading methods
    // EFFECTS: parses the MasterTimeLog from JSON object and returns it
    private MasterTimeLog parseMasterTimeLog(JSONObject jsonObject) {
        MasterTimeLog masterTimeLog = new MasterTimeLog();
        addTimeLogs(masterTimeLog, jsonObject);
        return masterTimeLog;
    }

    // MODIFIES: masterTimeLog
    // EFFECTS: parses TimeLogs from JSON object and adds them to MasterTimeLog
    private void addTimeLogs(MasterTimeLog masterTimeLog, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("masterTimeLog");
        for (Object json : jsonArray) {
            JSONObject nextTimeLog = (JSONObject) json;
            addTimeLog(masterTimeLog, nextTimeLog);
        }
    }

    // MODIFIES: masterTimeLog
    // EFFECTS: parses individual TimeLog objects from JSON object and adds it to MasterTimeLog
    private void addTimeLog(MasterTimeLog masterTimeLog, JSONObject jsonObject) {
        String clientName = jsonObject.getString("clientTimeLog");
        Client client = clientBook.getAClient(clientName);
        masterTimeLog.createTimeLog(client);
        JSONArray jsonArray = jsonObject.getJSONArray("timeEntries");
        for (Object json : jsonArray) {
            JSONObject nextTimeEntry = (JSONObject) json;
            addTimeEntry(masterTimeLog.getTimeLogForClient(client), nextTimeEntry, client);
        }
    }

    // MODIFIES: timeLog
    // EFFECTS: parses individual TimeEntry objects from JSON object and adds it to TimeLog
    private void addTimeEntry(TimeLog timeLog, JSONObject jsonObject, Client client) {
        String entryName = jsonObject.getString("timeEntry");
        String description = jsonObject.getString("description");
        String startDateTime = jsonObject.getString("startDateTime");
        String endDateTime = jsonObject.getString("endDateTime");
        String categoryName = jsonObject.getString("category");
        BillingCategory category = billingCategories.getABillingCategory(categoryName, client);
        timeLog.createTimeEntry(entryName, description, startDateTime, endDateTime, category);
    }
    //End of MasterTimeLog loading methods


}
