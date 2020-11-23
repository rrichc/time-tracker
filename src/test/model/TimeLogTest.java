//package model;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//import java.util.ArrayList;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//class TimeLogTest {
//    String clientName1 = "Client Name 1";
//    Client client1;
//    String categoryName1 = "Category Name 1";
//    String categoryName2 = "Category Name 2";
//    BillingCategory category1;
//    BillingCategory category2;
//    private TimeLog timeLog;
//
//    @BeforeEach
//    void runBefore() {
//        client1 = new Client(clientName1);
//        category1 = new BillingCategory(categoryName1, "10.00", client1);
//        category2 = new BillingCategory(categoryName2, "20.00", client1);
//        timeLog = new TimeLog(client1);
//    }
//
//    @Test
//    void testTimeLogCreation() {
//        assertEquals(0, timeLog.getTimeEntries().size());
//        assertEquals(clientName1, timeLog.getClient().getName());
//    }
//
//    @Test
//    void createTimeEntry() {
//        String entryName = "Task 1";
//        String entryDesc = "Task 1 description";
//        String startDateTime = "2020-10-11 10:00";
//        String endDateTime = "2020-10-11 12:00";
//        timeLog.createTimeEntry(entryName, entryDesc, startDateTime, endDateTime, category1);
//        assertEquals(1, timeLog.getTimeEntries().size());
//        assertEquals(entryName, timeLog.getTimeEntries().get(0).getName());
//        assertEquals(entryDesc, timeLog.getTimeEntries().get(0).getDescription());
//        assertEquals(categoryName1, timeLog.getTimeEntries().get(0).getCategory().getName());
//        assertEquals(startDateTime, DateTimeParser.parseDateTimeToString(timeLog.getTimeEntries().get(0).getStartDateTime()));
//        assertEquals(endDateTime, DateTimeParser.parseDateTimeToString(timeLog.getTimeEntries().get(0).getEndDateTime()));
//    }
//
//    //1
//    //Entry names don't match & the Entry is for that Category
//    @Test
//    void removeTimeEntryNameNoMatchSameCategory() {
//        String entryName1 = "Task 1";
//        String entryDesc1 = "Task 1 description";
//        String startDateTime1 = "2020-10-11 10:00";
//        String endDateTime1 = "2020-10-11 12:00";
//        timeLog.createTimeEntry(entryName1, entryDesc1, startDateTime1, endDateTime1, category1);
//        String entryName2 = "Task 2";
//        boolean removalSuccess = timeLog.removeTimeEntry(entryName2, category1);
//        assertFalse(removalSuccess);
//        assertEquals(1, timeLog.getTimeEntries().size());
//        assertEquals(entryName1, timeLog.getTimeEntries().get(0).getName());
//    }
//
//    //2
//    //Entry names match & the Entry is for that Category
//    @Test
//    void removeTimeEntryNameMatchSameCategory() {
//        String entryName1 = "Task 1";
//        String entryDesc1 = "Task 1 description";
//        String startDateTime1 = "2020-10-11 10:00";
//        String endDateTime1 = "2020-10-11 12:00";
//        timeLog.createTimeEntry(entryName1, entryDesc1, startDateTime1, endDateTime1, category1);
//        String entryName2 = "Task 2";
//        boolean removalSuccess = timeLog.removeTimeEntry(entryName1, category1);
//        assertTrue(removalSuccess);
//        assertEquals(0, timeLog.getTimeEntries().size());
//    }
//
//    //3
//    //Entry names don't match & the Entry is for a different Category
//    @Test
//    void removeTimeEntryNameNoMatchDifferentCategory() {
//        String entryName1 = "Task 1";
//        String entryDesc1 = "Task 1 description";
//        String startDateTime1 = "2020-10-11 10:00";
//        String endDateTime1 = "2020-10-11 12:00";
//        timeLog.createTimeEntry(entryName1, entryDesc1, startDateTime1, endDateTime1, category1);
//        String entryName2 = "Task 2";
//        boolean removalSuccess = timeLog.removeTimeEntry(entryName2, category2);
//        assertFalse(removalSuccess);
//        assertEquals(1, timeLog.getTimeEntries().size());
//        assertEquals(entryName1, timeLog.getTimeEntries().get(0).getName());
//    }
//
//    //4
//    //Entry names match & the Entry is for a different Category
//    @Test
//    void removeTimeEntryNameMatchDifferentCategory() {
//        String entryName1 = "Task 1";
//        String entryDesc1 = "Task 1 description";
//        String startDateTime1 = "2020-10-11 10:00";
//        String endDateTime1 = "2020-10-11 12:00";
//        timeLog.createTimeEntry(entryName1, entryDesc1, startDateTime1, endDateTime1, category1);
//        String entryName2 = "Task 2";
//        boolean removalSuccess = timeLog.removeTimeEntry(entryName1, category2);
//        assertFalse(removalSuccess);
//        assertEquals(1, timeLog.getTimeEntries().size());
//        assertEquals(entryName1, timeLog.getTimeEntries().get(0).getName());
//    }
//
//    //1
//    //Entry names don't match & the Entry is for that Category
//    @Test
//    void editTimeEntryNameNoMatchSameCategory() {
//        String entryName1 = "Task 1";
//        String entryDesc1 = "Task 1 description";
//        String startDateTime1 = "2020-10-11 10:00";
//        String endDateTime1 = "2020-10-11 12:00";
//        timeLog.createTimeEntry(entryName1, entryDesc1, startDateTime1, endDateTime1, category1);
//        String differentName = "Different Name";
//        String entryName2 = "Task 2";
//        String entryDesc2 = "Task 2 description";
//        String startDateTime2 = "2020-10-12 11:00";
//        String endDateTime2 = "2020-10-12 16:00";
//        boolean editSuccess = timeLog.editTimeEntry(differentName, entryName2, entryDesc2, startDateTime2, endDateTime2, category1);
//        assertFalse(editSuccess);
//        assertEquals(1, timeLog.getTimeEntries().size());
//        assertEquals(entryName1, timeLog.getTimeEntries().get(0).getName());
//    }
//
//    //2
//    //Entry names match & the Entry is for that Category
//    @Test
//    void editTimeEntryNameMatchSameCategory() {
//        String entryName1 = "Task 1";
//        String entryDesc1 = "Task 1 description";
//        String startDateTime1 = "2020-10-11 10:00";
//        String endDateTime1 = "2020-10-11 12:00";
//        timeLog.createTimeEntry(entryName1, entryDesc1, startDateTime1, endDateTime1, category1);
//        String entryName2 = "Task 2";
//        String entryDesc2 = "Task 2 description";
//        String startDateTime2 = "2020-10-12 11:00";
//        String endDateTime2 = "2020-10-12 16:00";
//        boolean editSuccess = timeLog.editTimeEntry(entryName1, entryName2, entryDesc2, startDateTime2, endDateTime2, category1);
//        assertTrue(editSuccess);
//        assertEquals(1, timeLog.getTimeEntries().size());
//        assertEquals(entryName2, timeLog.getTimeEntries().get(0).getName());
//    }
//
//    //3
//    //Entry names don't match & the Entry is for a different Category
//    @Test
//    void editTimeEntryNameNoMatchDifferentCategory() {
//        String entryName1 = "Task 1";
//        String entryDesc1 = "Task 1 description";
//        String startDateTime1 = "2020-10-11 10:00";
//        String endDateTime1 = "2020-10-11 12:00";
//        timeLog.createTimeEntry(entryName1, entryDesc1, startDateTime1, endDateTime1, category1);
//        String differentName = "Different Name";
//        String entryName2 = "Task 2";
//        String entryDesc2 = "Task 2 description";
//        String startDateTime2 = "2020-10-12 11:00";
//        String endDateTime2 = "2020-10-12 16:00";
//        boolean editSuccess = timeLog.editTimeEntry(differentName, entryName2, entryDesc2, startDateTime2, endDateTime2, category2);
//        assertFalse(editSuccess);
//        assertEquals(1, timeLog.getTimeEntries().size());
//        assertEquals(entryName1, timeLog.getTimeEntries().get(0).getName());
//    }
//
//    //4
//    //Entry names match & the Entry is for a different Category
//    @Test
//    void editTimeEntryNameMatchDifferentCategory() {
//        String entryName1 = "Task 1";
//        String entryDesc1 = "Task 1 description";
//        String startDateTime1 = "2020-10-11 10:00";
//        String endDateTime1 = "2020-10-11 12:00";
//        timeLog.createTimeEntry(entryName1, entryDesc1, startDateTime1, endDateTime1, category1);
//        String entryName2 = "Task 2";
//        String entryDesc2 = "Task 2 description";
//        String startDateTime2 = "2020-10-12 11:00";
//        String endDateTime2 = "2020-10-12 16:00";
//        boolean editSuccess = timeLog.editTimeEntry(entryName1, entryName2, entryDesc2, startDateTime2, endDateTime2, category2);
//        assertFalse(editSuccess);
//        assertEquals(1, timeLog.getTimeEntries().size());
//        assertEquals(entryName1, timeLog.getTimeEntries().get(0).getName());
//    }
//
//    @Test
//    void testGetTimeEntriesForBillingCategory() {
//        String entryName1 = "Task 1";
//        String entryDesc1 = "Task 1 description";
//        String startDateTime1 = "2020-10-11 10:00";
//        String endDateTime1 = "2020-10-11 12:00";
//        timeLog.createTimeEntry(entryName1, entryDesc1, startDateTime1, endDateTime1, category1);
//        String entryName2 = "Task 2";
//        String entryDesc2 = "Task 2 description";
//        String startDateTime2 = "2020-10-12 11:00";
//        String endDateTime2 = "2020-10-12 16:00";
//        timeLog.createTimeEntry(entryName2, entryDesc2, startDateTime2, endDateTime2, category2);
//
//        ArrayList<TimeEntry> timeEntriesForCategory = timeLog.getTimeEntriesForBillingCategory(category1);
//        assertEquals(1, timeEntriesForCategory.size());
//        assertEquals(entryName1, timeEntriesForCategory.get(0).getName());
//    }
//}