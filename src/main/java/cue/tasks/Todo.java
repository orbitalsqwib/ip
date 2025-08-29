package cue.tasks;

import java.time.LocalDateTime;

/**
 * A simple Task that only has a name.
 */
public class Todo extends Task {
    public Todo(String taskName) {
        super(taskName);
    }

    @Override
    public String toString() {
        return "[T] " + super.toString();
    }

    @Override
    public String encodeData() {
        return "";
    }

    @Override
    public boolean isActiveOn(LocalDateTime dateTime) {
        return this.isDone();
    }
}
