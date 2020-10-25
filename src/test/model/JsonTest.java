package model;

import static org.junit.jupiter.api.Assertions.assertEquals;

//This test class is based off of the https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo provided as an example
//Is used to assist in testing the JsonReader and JsonWriter
public class JsonTest {
    //EFFECTS: Tests the client book retrieved from Json for appropriate items
    protected void checkClientBookItems(ClientBook clientBook) {
        assertEquals(2, clientBook.getClients().size());
        assertEquals("Client1", clientBook.getClients().get(0).getName());
        assertEquals("Client2", clientBook.getClients().get(1).getName());
    }

    //EFFECTS: Tests the billing categories collection retrieved from Json for appropriate items
    protected void checkBillingCategoriesItems(BillingCategories billingCategories) {
        assertEquals(2, billingCategories.getAllBillingCategories().size());
        checkBillingCategory("Category1",18.00, "Client1", billingCategories.getAllBillingCategories().get(0));
        checkBillingCategory("Category2",11.00, "Client2", billingCategories.getAllBillingCategories().get(1));
    }

    //EFFECTS: Tests each category retrieved from Json for appropriate items
    protected void checkBillingCategory(String name, Double rate, String clientName, BillingCategory category) {
        assertEquals(name, category.getName());
        assertEquals(rate, category.getRatePerHour());
        assertEquals(clientName, category.getClient().getName());
    }

    //EFFECTS: Tests the MasterTimeLog retrieved from Json for appropriate items
    protected void checkMasterTimeLog(MasterTimeLog masterTimeLog) {
        assertEquals(2, masterTimeLog.getMasterTimeLog().size());
        assertEquals( "Client1", masterTimeLog.getMasterTimeLog().get(0).getClient().getName());
        assertEquals( "Client2", masterTimeLog.getMasterTimeLog().get(1).getClient().getName());
        TimeEntry entry1 = masterTimeLog.getMasterTimeLog().get(0).getTimeEntries().get(0);
        TimeEntry entry2 = masterTimeLog.getMasterTimeLog().get(1).getTimeEntries().get(0);
        checkTimeEntry("Task1", "Task1 Desc", "2020-10-08 12:30", "2020-10-08 13:30", "Category1", entry1);
        checkTimeEntry("Task3", "Task3 Desc", "2020-10-14 12:30", "2020-10-14 13:30", "Category2", entry2);
    }

    //EFFECTS: Tests the individual time entry retrieved from Json for appropriate items
    protected void checkTimeEntry(String timeEntry, String description, String startDateTime, String endDateTime, String category, TimeEntry entry) {
        assertEquals(timeEntry, entry.getName());
        assertEquals(description, entry.getDescription());
        assertEquals(startDateTime, DateTimeParser.parseDateTimeToString(entry.getStartDateTime()));
        assertEquals(endDateTime, DateTimeParser.parseDateTimeToString(entry.getEndDateTime()));
        assertEquals(category, entry.getCategory().getName());
    }
}
