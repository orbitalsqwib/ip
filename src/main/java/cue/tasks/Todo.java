package cue.tasks;

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
}
