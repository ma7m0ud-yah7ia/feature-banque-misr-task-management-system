package banquemisr.challenge05.task.management.service.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public abstract class StringDateConverters {
    public static String convertDateToString(Date dueDate, String pattern) {
        return new SimpleDateFormat(pattern).format(dueDate);
    }

    public static Date convertStringToDate(String dueDateStringValue, String pattern) {
        try {
            return new SimpleDateFormat(pattern).parse(dueDateStringValue);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public static Date convertToSystemDateTime(String inputDate) {
        // Define the input date format (assuming input is in ISO-8601 format)
        DateTimeFormatter formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME;

        // Parse the input date string into a ZonedDateTime object
        ZonedDateTime zonedDateTime = ZonedDateTime.parse(inputDate, formatter);

        // Define the target time zone (UTC+2)
        ZoneId targetZone = ZoneId.of("+02:00");

        // Convert the input date to the target time zone

        // Format the converted date back to a string in ISO-8601 format
        return Date.from(zonedDateTime.withZoneSameInstant(targetZone).toInstant());
    }

    public static Date getSystemDateTime() {
        // Define the input date format (assuming input is in ISO-8601 format)
        DateTimeFormatter formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME;

        // Parse the input date string into a ZonedDateTime object
        ZonedDateTime zonedDateTime = ZonedDateTime.parse(convertDateToString(new Date(),"yyyy-MM-dd'T'HH:mm:ss.SSSXXX"), formatter);

        // Define the target time zone (UTC+2)
        ZoneId targetZone = ZoneId.of("+02:00");

        // Format the converted date back to a string in ISO-8601 format
        return Date.from(zonedDateTime.withZoneSameInstant(targetZone).toInstant());
    }

}
