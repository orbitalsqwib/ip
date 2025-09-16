package cue.tasks;

import java.time.LocalDateTime;

/**
 * Models a task that can be completed.
 */
public abstract class Task {
    private boolean isDone;
    private String taskName;

    /**
     * Creates a task with the given name
     *
     * @param taskName The name of the task to create
     */
    public Task(String taskName) {
        this.taskName = taskName;
        this.isDone = false;
    }

    public void setDone(boolean isDone) {
        this.isDone = isDone;
    }

    public boolean isDone() {
        return this.isDone;
    }

    public boolean nameContains(String substring) {
        return this.taskName.contains(substring);
    }

    @Override
    public String toString() {
        char status = ' ';
        if (isDone) {
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
        if (isDone) {
            doneStatus = "1";
        } else {
            doneStatus = "0";
        }
        return doneStatus + " | " + this.taskName + " | " + encodeData();
    }

    /**
     * Specifies if the current task is date sensitive. Child classes should
     * overwrite this as necessary.
     *
     * @return Returns true if the event is date sensitive and false otherwise.
     */
    public boolean isDateSensitive() {
        return false;
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
