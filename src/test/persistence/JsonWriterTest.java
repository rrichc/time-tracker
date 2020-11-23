package persistence;

import model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ui.TimeTracker;

import java.io.FileNotFoundException;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

//This test class is based off of the https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo provided as an example
class JsonWriterTest extends JsonTest {
    private ClientBook clientBook;
    private BillingCategories billingCategories;
    private MasterTimeLog masterTimeLog;
    private JsonReader reader;
    private JsonWriter writer;

    @BeforeEach
    void runBefore() {
        this.clientBook = new ClientBook();
        this.billingCategories = new BillingCategories();
        this.masterTimeLog = new MasterTimeLog();
        this.reader = new JsonReader();
        this.writer = new JsonWriter();
    }

    @Test
    void testWriterInvalidFile() {
        try {
            String filePath = "./data/my\0illegal:fileName.json";
            writer.open(filePath);
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyWorkroom() {
        String clientBookPath = "./data/testWriterClientBookEmpty.json";
        String billingPath = "./data/testWriterBillingCategoriesEmpty.json";
        String timePath = "./data/testWriterMasterTimeLogEmpty.json";
        try {
            writer.open(clientBookPath);
            writer.write(clientBook);
            writer.close();
            writer.open(billingPath);
            writer.write(billingCategories);
            writer.close();
            writer.open(timePath);
            writer.write(masterTimeLog);
            writer.close();

            ClientBook clientBook = reader.readClientBook(clientBookPath);
            assertEquals(0, clientBook.getClients().size());
            BillingCategories billingCategories = reader.readBillingCategories(billingPath, this.clientBook);
            assertEquals(0, billingCategories.getAllBillingCategories().size());
            MasterTimeLog masterTimeLog = reader.readMasterTimeLog(timePath, this.clientBook, this.billingCategories);
            assertEquals(0, masterTimeLog.getMasterTimeLog().size());
        } catch (IOException | NegativeRateException | EmptyNameException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterPopulatedJson() {
        String clientBookPath = "./data/testWriterClientBookWithItems.json";
        String billingPath = "./data/testWriterBillingCategoriesWithItems.json";
        String timePath = "./data/testWriterMasterTimeLogWithItems.json";
        populateFieldsWithItems();
        try {
            saveWorkRoom(clientBookPath, billingPath, timePath);

            this.clientBook = reader.readClientBook(clientBookPath);
            checkClientBookItems(clientBook);
            this.billingCategories = reader.readBillingCategories(billingPath, clientBook);
            checkBillingCategoriesItems(billingCategories);
            this.masterTimeLog = reader.readMasterTimeLog(timePath, clientBook, billingCategories);
            checkMasterTimeLog(masterTimeLog);
        } catch (IOException | NegativeRateException | EmptyNameException e) {
            fail("Exception should not have been thrown");
        }
    }

    //EFFECTS: populates the masterTimeLog, clientBook, and billingCategories with entries
    void populateFieldsWithItems() {
        clientBook.createClient("Client1");
        clientBook.createClient("Client2");
        Client client1 = clientBook.getAClient("Client1");
        Client client2 = clientBook.getAClient("Client2");
        try {
            billingCategories.createBillingCategory("Category1", "18.00", client1);
        } catch (NegativeRateException | EmptyNameException e) {
            fail();
        }
        try {
            billingCategories.createBillingCategory("Category2", "11.00", client2);
        } catch (NegativeRateException | EmptyNameException e) {
            fail();
        }
        masterTimeLog.createTimeLog(clientBook.getAClient("Client1"));
        masterTimeLog.createTimeLog(clientBook.getAClient("Client2"));
        TimeLog timeLog1 = masterTimeLog.getTimeLogForClient(client1);
        TimeLog timeLog2 = masterTimeLog.getTimeLogForClient(client2);
        timeLog1.createTimeEntry("Task1", "Task1 Desc", "2020-10-08 12:30", "2020-10-08 13:30", billingCategories.getABillingCategory("Category1", client1));
        timeLog2.createTimeEntry("Task3", "Task3 Desc", "2020-10-14 12:30", "2020-10-14 13:30", billingCategories.getABillingCategory("Category2", client2));
    }

    //EFFECTS: saves the work environment
    void saveWorkRoom(String clientBookPath, String billingPath, String timePath) {
        try {
            writer.open(clientBookPath);
            writer.write(this.clientBook);
            writer.close();
            writer.open(billingPath);
            writer.write(this.billingCategories);
            writer.close();
            writer.open(timePath);
            writer.write(this.masterTimeLog);
            writer.close();
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file.");
        }
    }
}