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
        return "[E] " + super.toString() + " (from: " + from + " to: " + to + ")";
    }

    @Override
    public String encodeData() {
        return from.encode() + " , " + to.encode();
    }

    @Override
    public boolean isActiveOn(LocalDateTime dateTime) {
        return (from.isBefore(dateTime) || from.isEqual(dateTime)) && (to.isAfter(dateTime) || to.isEqual(dateTime));
    }
}
