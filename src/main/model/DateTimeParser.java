package model;

//https://stackoverflow.com/questions/22463062/how-to-parse-format-dates-with-localdatetime-java-8
//https://howtodoinjava.com/java/date-time/format-localdatetime-to-string/
//TODO: Add a class description for DateTimeParser

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateTimeParser {
    static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    //Requires: dateTime String to be in the format "1986-04-08 12:30"
    //Effects: Takes a date & time in the String format, and converts it into a LocalDateTime object format
    public static LocalDateTime parseDateTimeFromString(String dateTime) {
        return LocalDateTime.parse(dateTime, formatter);
    }

    //TODO: Fill out this spec
    public static String parseDateTimeToString(LocalDateTime dateTime) {
        return dateTime.format(formatter);
    }

}

