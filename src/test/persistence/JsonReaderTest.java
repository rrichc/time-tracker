package persistence;

import model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

//This test class is based off of the https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo provided as an example
class JsonReaderTest extends JsonTest {
    private ClientBook clientBook;
    private BillingCategories billingCategories;
    private MasterTimeLog masterTimeLog;
    private JsonReader reader;

    @BeforeEach
    void runBefore() {
        this.clientBook = new ClientBook();
        this.billingCategories = new BillingCategories();
        this.masterTimeLog = new MasterTimeLog();
        this.reader = new JsonReader();
    }

    @Test
    void testReaderNonExistentFile() {
        String filePath = "./data/noSuchFile.json";
        JsonReader reader = new JsonReader();
        try {
            ClientBook clientBook = reader.readClientBook(filePath);
            BillingCategories billingCategories = reader.readBillingCategories(filePath, clientBook);
            MasterTimeLog masterTimeLog = reader.readMasterTimeLog(filePath, clientBook, billingCategories);
            fail("IOException expected");
        } catch (IOException | NegativeRateException | EmptyNameException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyJson() {
        String clientBookPath = "./data/testReaderClientBookEmpty.json";
        String billingPath = "./data/testReaderBillingCategoriesEmpty.json";
        String timePath = "./data/testReaderMasterTimeLogEmpty.json";
        try {
            ClientBook clientBook = reader.readClientBook(clientBookPath);
            assertEquals(0, clientBook.getClients().size());
            BillingCategories billingCategories = reader.readBillingCategories(billingPath, this.clientBook);
            assertEquals(0, billingCategories.getAllBillingCategories().size());
            MasterTimeLog masterTimeLog = reader.readMasterTimeLog(timePath, this.clientBook, this.billingCategories);
            assertEquals(0, masterTimeLog.getMasterTimeLog().size());
        } catch (IOException | NegativeRateException | EmptyNameException e) {
            fail("Couldn't read from file");
        }
    }



    @Test
    void testReaderPopulatedJson() {
        String clientBookPath = "./data/testReaderClientBookWithItems.json";
        String billingPath = "./data/testReaderBillingCategoriesWithItems.json";
        String timePath = "./data/testReaderMasterTimeLogWithItems.json";
        try {
            this.clientBook = reader.readClientBook(clientBookPath);
            checkClientBookItems(clientBook);
            this.billingCategories = reader.readBillingCategories(billingPath, clientBook);
            checkBillingCategoriesItems(billingCategories);
            this.masterTimeLog = reader.readMasterTimeLog(timePath, clientBook, billingCategories);
            checkMasterTimeLog(masterTimeLog);
        } catch (IOException | NegativeRateException | EmptyNameException e) {
            fail("Couldn't read from file");
        }
    }



}