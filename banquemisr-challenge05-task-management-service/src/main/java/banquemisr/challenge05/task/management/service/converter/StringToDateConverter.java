package banquemisr.challenge05.task.management.service.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static banquemisr.challenge05.task.management.service.enums.DateFormats.GENERAL_DATE_TIME_FORMAT;

@Component
public class StringToDateConverter implements Converter<String, Date> {

    private final SimpleDateFormat dateFormat = new SimpleDateFormat(GENERAL_DATE_TIME_FORMAT.getValue());

    @Override
    public Date convert(String source) {
        try {
            return dateFormat.parse(source.replace(" ", "+")); // Replace space with "+" for correct parsing
        } catch (ParseException e) {
            throw new IllegalArgumentException("Invalid date format: " + source, e);
        }
    }
}
