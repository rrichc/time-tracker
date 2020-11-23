package model;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class TimeEntryTest {
    private TimeEntry entry1;
    private Client client;
    private BillingCategory category;

    @Test
    void testTimeEntryCreation() {
        String timeEntryName = "Coding Phase 1";
        String timeEntryDesc = "Phase 1 of project";
        String timeEntryStartDateTime = "2020-10-10 12:30";
        String timeEntryEndDateTime = "2020-10-12 15:30";
        client = new Client("Bob");
        try {
            category = new BillingCategory("Coding", "100", client);
        } catch (NegativeRateException e) {
            fail();
        } catch (EmptyNameException e) {
            fail();
        }
        entry1 = new TimeEntry(timeEntryName, timeEntryDesc, timeEntryStartDateTime, timeEntryEndDateTime, category);
        assertEquals(timeEntryName, entry1.getName());
        assertEquals(timeEntryDesc, entry1.getDescription());
        assertEquals(DateTimeParser.parseDateTimeFromString(timeEntryStartDateTime), entry1.getStartDateTime());
        assertEquals(DateTimeParser.parseDateTimeFromString(timeEntryEndDateTime), entry1.getEndDateTime());
        assertEquals((3*60)+(24*60*2), entry1.getTimeSpentMinutes());
    }

    @Test
    void toStringTest() {
        String timeEntryName = "Coding Phase 1";
        String timeEntryDesc = "Phase 1 of project";
        String timeEntryStartDateTime = "2020-10-10 12:30";
        String timeEntryEndDateTime = "2020-10-12 15:30";
        client = new Client("Bob");
        try {
            category = new BillingCategory("Coding", "100", client);
        } catch (NegativeRateException e) {
            fail();
        } catch (EmptyNameException e) {
            fail();
        }
        entry1 = new TimeEntry(timeEntryName, timeEntryDesc, timeEntryStartDateTime, timeEntryEndDateTime, category);
        assertEquals(String.format("Name: " + timeEntryName + "; "
                + "Description:" + timeEntryDesc + "; "
                + "Start: " + timeEntryStartDateTime + "; "
                + "End: " + timeEntryEndDateTime + "; "
                + "Time Spent: " + Long.toString((3*60)+(24*60*2)) + "; "
                + "Billing Category: " + category.getName() + "; "
                + "Rate: " + Double.toString(category.getRatePerHour())), entry1.toString());
    }
}