package task;

public class Task {
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
}
