package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;

import static model.DateTimeParser.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class DateTimeParserTest {

    @BeforeEach
    void runBefore(){
        DateTimeParser parser = new DateTimeParser();
    }

    @Test
    void testParseDateTimeFromString(){
        String dateTimeString = "2020-10-11 12:30";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime dateTime = LocalDateTime.of(2020, Month.OCTOBER, 11, 12, 30);
        LocalDateTime dateTimeAfterConversion = parseDateTimeFromString(dateTimeString);
        assertEquals(dateTime, dateTimeAfterConversion);
    }

    @Test
    void testParseDateTimeFromStringEdgeCase(){
        String dateTimeString = "0001-01-01 00:00";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime dateTime = LocalDateTime.of(0001, Month.JANUARY, 1, 00, 00);
        LocalDateTime dateTimeAfterConversion = parseDateTimeFromString(dateTimeString);
        assertEquals(dateTime, dateTimeAfterConversion);
    }

    @Test
    void testParseDateTimeToString(){
        int year = 2020;
        int month = 07;
        int day = 19;
        int hour = 23;
        int minute = 59;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime dateTime = LocalDateTime.of(year, month, day, hour, minute);
        String dateTimeAfterConversion = parseDateTimeToString(dateTime);
        assertEquals(String.format("%04d" , year) + "-" + String.format("%02d" , month)
                + "-" + String.format("%02d" , day) + " " + String.format("%02d" , hour) + ":"
                + String.format("%02d" , minute), dateTimeAfterConversion);
    }
}