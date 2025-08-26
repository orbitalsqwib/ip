package cue.tasks;

import java.time.LocalDateTime;

import cue.datetime.StringDateTime;

public class Deadline extends Task{
    private StringDateTime deadline;

    public Deadline(String taskName, String deadline) {
        super(taskName);
        this.deadline = new StringDateTime(deadline);
    }

    @Override
    public String toString() {
        return "[D] " + super.toString() + " (by: " + deadline + ")";
    }

    @Override
    public String encodeData() {
        return deadline.encode();
    }

    @Override
    public boolean isActiveOn(LocalDateTime dateTime) {
        return deadline.isBefore(dateTime) || deadline.isEqual(dateTime);
    }
}
