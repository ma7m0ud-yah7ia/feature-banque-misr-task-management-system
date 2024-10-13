package banquemisr.challenge05.task.management.service.enums;

import lombok.Getter;

@Getter
public enum DateFormats {
    GENERAL_DATE_TIME_FORMAT("yyyy-MM-dd'T'HH:mm:ss.SSSXXX"),
    GENERAL_DATE_FORMAT("yyyy-MM-dd");

    private final String value;

    DateFormats(String inputValue) {
        this.value = inputValue;
    }

}
