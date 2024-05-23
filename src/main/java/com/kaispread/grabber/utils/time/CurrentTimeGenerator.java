package com.kaispread.grabber.utils.time;

import static java.time.ZoneId.*;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.Locale;

public enum CurrentTimeGenerator {
    MONTH_DAY_TIME(new DateTimeFormatterBuilder()
        .appendText(ChronoField.MONTH_OF_YEAR, java.time.format.TextStyle.SHORT_STANDALONE)
        .appendLiteral(" ")
        .appendValue(ChronoField.DAY_OF_MONTH)
        .appendLiteral("일 ")
        .appendValue(ChronoField.HOUR_OF_DAY, 2)
        .appendLiteral(":")
        .appendValue(ChronoField.MINUTE_OF_HOUR, 2)
        .toFormatter(Locale.KOREAN))
    ;

    private final DateTimeFormatter dateTimeFormatter;

    CurrentTimeGenerator(DateTimeFormatter dateTimeFormatter) {
        this.dateTimeFormatter = dateTimeFormatter;
    }

    /*
     * ex) 5월 27일 18:00
     */
    public String getCurrentTime() {
        return ZonedDateTime.now(of("Asia/Seoul"))
                            .format(dateTimeFormatter);
    }
}
