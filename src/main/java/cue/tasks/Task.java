package cue.tasks;

public abstract class Task {
    private boolean done;
    private String taskName;

    public Task(String taskName) {
        this.taskName = taskName;
        this.done = false;
    }

    public void setDone(boolean isDone) {
        this.done = isDone;
    }

    @Override
    public String toString() {
        char status = ' ';
        if (this.done) {
            status = 'x';
        }

        return "[" + status + "] " + this.taskName;
    }

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
}
