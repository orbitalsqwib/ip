package cue.tasks;

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
}
