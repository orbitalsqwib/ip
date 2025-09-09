package cue.datetime;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoField;

/**
 * Represents a String object that may be upgraded to a LocalDateTime object
 * given that its contained value is parseable.
 */
public class StringDateTime {
    private static final DateTimeFormatter PARSE_FORMAT = new DateTimeFormatterBuilder()
            .appendPattern("yyyy-M-d[@HHmm]")
            .parseDefaulting(ChronoField.HOUR_OF_DAY, 0)
            .parseDefaulting(ChronoField.MINUTE_OF_HOUR, 0)
            .toFormatter();

    private static final DateTimeFormatter PRINT_FORMAT_W_TIME = DateTimeFormatter.ofPattern(
            "MMM d yyyy @ hh:mma");
    private static final DateTimeFormatter PRINT_FORMAT = DateTimeFormatter.ofPattern(
            "MMM d yyyy");

    private boolean isPlainString;
    private LocalDateTime parsedDateTime;
    private String rawDateTime;

    /**
     * Creates a StringDateTime object containing the rawDateTime, which is
     * upgraded to a LocalDateTime on a best effort basis.
     *
     * @param rawDateTime The datetime value to be stored
     */
    public StringDateTime(String rawDateTime) {
        this.rawDateTime = rawDateTime;
        this.parsedDateTime = null;

        try {
            this.parsedDateTime = LocalDateTime.parse(rawDateTime, PARSE_FORMAT);
            this.isPlainString = false;
        } catch (DateTimeParseException err) {
            this.isPlainString = true;
        }
    }

    public boolean isBefore(LocalDateTime dateTime) {
        return !this.isPlainString && this.parsedDateTime.isBefore(dateTime);
    }

    public boolean isAfter(LocalDateTime dateTime) {
        return !this.isPlainString && this.parsedDateTime.isAfter(dateTime);
    }

    public boolean isEqual(LocalDateTime dateTime) {
        return !this.isPlainString && this.parsedDateTime.isEqual(dateTime);
    }

    public String encode() {
        return rawDateTime;
    }

    public LocalDateTime toLocalDateTime() {
        return parsedDateTime;
    }

    @Override
    public String toString() {
        if (isPlainString || parsedDateTime == null) {
            return rawDateTime;
        }

        if (parsedDateTime.getHour() != 0 && parsedDateTime.getMinute() != 0) {
            return parsedDateTime.format(PRINT_FORMAT_W_TIME);
        }

        return parsedDateTime.format(PRINT_FORMAT);
    }
}
