package persistence;

import model.ClientBook;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

//This class is based heavily off of the https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo provided as an example
// Represents a writer that writes JSON representations to file
public class JsonWriter {
    private static final int TAB = 4;
    private PrintWriter writer;

    // EFFECTS: constructs writer
    public JsonWriter() {
    }

    // MODIFIES: this
    // EFFECTS: opens writer; throws FileNotFoundException if destination file cannot
    // be opened for writing
    public void open(String destination) throws FileNotFoundException {
       writer = new PrintWriter(new File(destination));
    }

    // MODIFIES: this
    // EFFECTS: writes JSON representation of workroom to file
    public void write(ClientBook clientBook) {
        JSONObject json = clientBook.toJson();
        saveToFile(json.toString(TAB));
    }

    // MODIFIES: this
    // EFFECTS: closes writer
    public void close() {
        writer.close();
    }

    // MODIFIES: this
    // EFFECTS: writes string to file
    private void saveToFile(String json) {
        writer.print(json);
    }
}
