package model;

import org.json.JSONObject;
import persistence.Writable;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;


/*
 *This class represents a single time entry with a name, description, start and end Date & Time,
 *time spent between start and end in minutes, and associated billing category
*/
public class TimeEntry implements Writable {

    private String name;
    private String description;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private long timeSpentMinutes;
    private BillingCategory category;

    //https://mkyong.com/java8/java-8-difference-between-two-localdate-or-localdatetime/
    /*
     * REQUIRES: All String parameters must be a non-empty,
     *           startDateTime and endDateTime String must be in the format "1986-04-08 12:30"
     *           where the year, month, and day must be greater than 1, and the month must be less than 13,
     *           and day must be less than 31. startDateTime must be before endDateTime.
     * EFFECTS:  Converts the start and end date from string to LocalDateTime format and calculates the different
     *           between the start and end date in minutes. Creates a single time entry with a name,
     *           description, category, start and end date, and time spent in minutes
     *
     */
    public TimeEntry(String name, String description, String startDateTime,
                     String endDateTime, BillingCategory category) {
        this.name = name;
        this.description = description;
        this.startDateTime = DateTimeParser.parseDateTimeFromString(startDateTime);
        this.endDateTime = DateTimeParser.parseDateTimeFromString(endDateTime);
        this.timeSpentMinutes = ChronoUnit.MINUTES.between(this.startDateTime, this.endDateTime);
        this.category = category;
    }

    /*
    * EFFECTS: Returns a string containing the name, description, category,
    *          start and end date, and time spent in minutes
    */
    @Override
    public String toString() {
        return String.format("Name: " + name + "; "
                + "Description:" + description + "; "
                + "Start: " + DateTimeParser.parseDateTimeToString(startDateTime) + "; "
                + "End: " + DateTimeParser.parseDateTimeToString(endDateTime) + "; "
                + "Time Spent: " + Long.toString(timeSpentMinutes) + "; "
                + "Billing Category: " + category.getName() + "; "
                + "Rate: " + Double.toString(category.getRatePerHour())
        );
    }

    /*
     * EFFECTS: Returns the name of the entry in String format
     */
    public String getName() {
        return name;
    }

    /*
     * EFFECTS: Returns the description of the entry in String format
     */
    public String getDescription() {
        return description;
    }

    /*
     * EFFECTS: Returns the startDateTime in LocalDateTime format
     */
    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }

    /*
     * EFFECTS: Returns the endDateTime in LocalDateTime format
     */
    public LocalDateTime getEndDateTime() {
        return endDateTime;
    }

    /*
     * EFFECTS: Returns the timeSpentInMinutes in long format
     */
    public long getTimeSpentMinutes() {
        return timeSpentMinutes;
    }

    /*
     * EFFECTS: Returns a single BillingCategory
     */
    public BillingCategory getCategory() {
        return category;
    }

    /*
     * EFFECTS:  Returns an representation of a TimeEntry object as a JSONObject
     */
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("timeEntry", name);
        json.put("description", description);
        json.put("startDateTime", DateTimeParser.parseDateTimeToString(startDateTime));
        json.put("endDateTime", DateTimeParser.parseDateTimeToString(endDateTime));
        json.put("category", category.getName());

        return json;
    }
}
