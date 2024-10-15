package com.example.vladislavnedyalkovemployees.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.List;
import java.util.zip.DataFormatException;

public class DateUtil {

    private static final List<String> DATE_FORMATS = Arrays.asList(
            "yyyy-MM-dd",
            "MM/dd/yyyy",
            "dd-MM-yyyy",
            "dd/MM/yyyy",
            "yyyy/MM/dd",
            "yyyy.MM.dd"
    );

    public static long getOverlappingDays(LocalDate firstEmpStartDate, LocalDate firstEmpEndDate, LocalDate secondEmpStartDate, LocalDate secondEmpEndDate) {
        LocalDate latestStart = firstEmpStartDate.isAfter(secondEmpStartDate) ? firstEmpStartDate : secondEmpStartDate;
        LocalDate earliestEnd = firstEmpEndDate.isBefore(secondEmpEndDate) ? firstEmpEndDate : secondEmpEndDate;

        return ChronoUnit.DAYS.between(latestStart, earliestEnd) > 0 ?
                ChronoUnit.DAYS.between(latestStart, earliestEnd) : 0;
    }

    public static LocalDate parseDate(String date) throws DataFormatException {
        if (date.equalsIgnoreCase("NULL")) {
            return LocalDate.now();
        }

        for (String format : DATE_FORMATS) {
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
                return LocalDate.parse(date, formatter);
            } catch (DateTimeParseException ignored) {
            }
        }

        throw new DataFormatException("Invalid date format: " + date);
    }
}
