package cue.tasks;

import java.time.LocalDateTime;

/**
 * Models a task that can be completed.
 */
public abstract class Task {
    private boolean done;
    private String taskName;

    /**
     * Creates a task with the given name
     *
     * @param taskName The name of the task to create
     */
    public Task(String taskName) {
        this.taskName = taskName;
        this.done = false;
    }

    public void setDone(boolean isDone) {
        this.done = isDone;
    }

    public boolean isDone() {
        return this.done;
    }

    public boolean nameContains(String substring) {
        return this.taskName.contains(substring);
    }

    @Override
    public String toString() {
        char status = ' ';
        if (this.done) {
            status = 'x';
        }

        return "[" + status + "] " + this.taskName;
    }

    /**
     * Converts the task's data into a form that can be easily saved to the disk.
     *
     * @return
     */
    public String encode() {
        String doneStatus;
        if (this.done) {
            doneStatus = "1";
        } else {
            doneStatus = "0";
        }
        return doneStatus + " | " + this.taskName + " | " + encodeData();
    }

    /**
     * Encodes task data (excluding task title and completion status) in a comma-delimited string.
     * @return A string containing specialised data of the associated task, separated by commas.
     */
    public abstract String encodeData();

    /**
     * Checks if the task is active on a specific date/time
     */
    public abstract boolean isActiveOn(LocalDateTime dateTime);
}
