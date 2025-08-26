package cue.tasks;

import java.time.LocalDateTime;

import cue.datetime.StringDateTime;

public class Event extends Task {
    private StringDateTime from;
    private StringDateTime to;

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
