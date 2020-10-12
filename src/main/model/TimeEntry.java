package model;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

//https://mkyong.com/java8/java-8-difference-between-two-localdate-or-localdatetime/
//TODO: Add a class description for TimeEntry

public class TimeEntry {

    private String name;
    private String description;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private long timeSpentMinutes;
    private BillingCategory category;

    public TimeEntry(String name, String description, String startDateTime,
                     String endDateTime, BillingCategory category) {
        this.name = name;
        this.description = description;
        this.startDateTime = DateTimeParser.parseDateTimeFromString(startDateTime);
        this.endDateTime = DateTimeParser.parseDateTimeFromString(endDateTime);
        this.timeSpentMinutes = ChronoUnit.MINUTES.between(this.startDateTime, this.endDateTime);
        this.category = category;
    }

    //For debugging
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

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }

    public LocalDateTime getEndDateTime() {
        return endDateTime;
    }

    public long getTimeSpentMinutes() {
        return timeSpentMinutes;
    }

    public BillingCategory getCategory() {
        return category;
    }
}
