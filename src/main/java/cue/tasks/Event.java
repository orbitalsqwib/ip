package cue.tasks;

import java.time.LocalDateTime;

import cue.datetime.StringDateTime;

/**
 * A type of Task that is active from a certain datetime to another datetime
 */
public class Event extends Task {
    private StringDateTime from;
    private StringDateTime to;

    /**
     * Creates a new event Task with a name and duration
     *
     * @param taskName The name of the task to create
     * @param from     The datetime that the event starts
     * @param to       The datetime that the event ends
     */
    public Event(String taskName, String from, String to) {
        super(taskName);
        this.from = new StringDateTime(from);
        this.to = new StringDateTime(to);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("[E] " + super.toString() + " (from: " + from + " to: " + to + ")");
        Long daysToStart = from.daysTill(LocalDateTime.now());
        Long daysToEnd = to.daysTill(LocalDateTime.now());
        if (daysToStart != null && daysToStart < 0) {
            builder.append(" (in " + -1 * daysToStart + " days)");
        } else {
            if (daysToEnd == null) {
                builder.append(" (started)");
            } else if (daysToEnd != null && daysToEnd < 0) {
                builder.append(" (ends in " + -1 * daysToEnd + " days)");
            } else {
                builder.append(" (ended)");
            }
        }
        return builder.toString();
    }

    @Override
    public String encodeData() {
        return from.encode() + " , " + to.encode();
    }

    @Override
    public boolean isActiveOn(LocalDateTime dateTime) {
        return (from.isBefore(dateTime) || from.isEqual(dateTime)) && (to.isAfter(dateTime) || to.isEqual(dateTime));
    }

    @Override
    public boolean isDateSensitive() {
        return from.toLocalDateTime() != null;
    }
}
