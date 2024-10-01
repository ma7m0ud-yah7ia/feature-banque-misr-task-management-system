package banquemisr.challenge05.task.management.service.Util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public abstract class StringDateConverters {
    public static String convertDateToString(Date dueDate) {
        return new SimpleDateFormat("dd/MM/yyyy").format(dueDate);
    }

    public static Date convertStringToDate(String dueDateStringValue) {
        try {
            return new SimpleDateFormat("dd/MM/yyyy").parse(dueDateStringValue);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}
