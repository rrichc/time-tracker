package model;

//https://stackoverflow.com/questions/22463062/how-to-parse-format-dates-with-localdatetime-java-8
//https://howtodoinjava.com/java/date-time/format-localdatetime-to-string/
//Converts Date and Time to and from String and LocalDateTime format
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateTimeParser {
    //format for date and time to be used
    static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    /*
     * REQUIRES: dateTime String must be in the format "1986-04-08 12:30" where the year, month,
     *           and day must be greater than 1, and the month must be less than 13, and day must be less than 31
     * EFFECTS:  Takes a date & time in the String format, and converts it into a LocalDateTime object format
     *           and returns it
     */
    public static LocalDateTime parseDateTimeFromString(String dateTime) {
        return LocalDateTime.parse(dateTime, formatter);
    }

    /*
     * EFFECTS:  Takes a date & time in the LocalDateTime format, and converts it into a String format
     *           and returns it
     */
    public static String parseDateTimeToString(LocalDateTime dateTime) {
        return dateTime.format(formatter);
    }

}

