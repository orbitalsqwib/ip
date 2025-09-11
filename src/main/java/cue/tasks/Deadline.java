package cue.tasks;

import java.time.LocalDateTime;

import cue.datetime.StringDateTime;

/**
 * A type of Task that has a datetime where the task should be completed by
 */
public class Deadline extends Task {
    private StringDateTime deadline;

    /**
     * Creates a new deadline Task with a name and a datetime that the task should
     * be completed by.
     *
     * @param taskName The name of the task to create
     * @param deadline The datetime that the task should be completed by
     */
    public Deadline(String taskName, String deadline) {
        super(taskName);
        this.deadline = new StringDateTime(deadline);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("[D] " + super.toString() + " (by: " + deadline + ") help");
        Long daysRemaining = deadline.daysTill(LocalDateTime.now());
        if (daysRemaining != null) {
            builder.append(daysRemaining < 0 ? " (in " + -1 * daysRemaining + " days)" : " (overdue)");
        }
        return builder.toString();
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
