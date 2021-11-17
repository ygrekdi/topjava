package ru.javawebinar.topjava.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class DateTimeUtil {
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

    public static boolean isBetweenHalfOpen(LocalTime lt, LocalTime startTime, LocalTime endTime) {
        if(startTime == null){
            startTime = LocalTime.MIN;
        }
        if(endTime == null){
            endTime = LocalTime.MAX;
        }
        return lt.compareTo(startTime) >= 0 && lt.compareTo(endTime) < 0;
    }

    public static boolean isBetweenDate(LocalDate ld, LocalDate startDate, LocalDate endDate) {
        if(startDate == null){
            startDate = LocalDate.MIN;
        }
        if(endDate == null){
            endDate = LocalDate.MAX;
        }
        return ld.compareTo(startDate) >= 0 && ld.compareTo(endDate) <= 0;
    }

    public static String toString(LocalDateTime ldt) {
        return ldt == null ? "" : ldt.format(DATE_TIME_FORMATTER);
    }

    public static LocalDate parseDate(String stringDate){
        if(stringDate == null || stringDate.isEmpty()){
            return null;
        }
        return LocalDate.parse(stringDate, DATE_FORMATTER);
    }

    public static LocalTime parseTime(String stringTime){
        if(stringTime == null || stringTime.isEmpty()){
            return null;
        }
        return LocalTime.parse(stringTime, TIME_FORMATTER);
    }
}

