package model;

//https://stackoverflow.com/questions/22463062/how-to-parse-format-dates-with-localdatetime-java-8
//TODO: Add a class description for DateTimeParser

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateTimeParser {

    //Requires: dateTime String to be in the format "1986-04-08 12:30"
    //Effects: Takes a date & time in the String format, and converts it into a LocalDateTime object format
    public static LocalDateTime parseDateTime(String dateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return LocalDateTime.parse(dateTime, formatter);
    }
}
