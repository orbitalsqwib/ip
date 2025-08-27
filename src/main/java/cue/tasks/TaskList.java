package cue.tasks;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class TaskList {
    private final ArrayList<Task> tasks;

    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    public TaskList(Task[] tasks) {
        this.tasks = new ArrayList<>();

        // add all tasks from src array
        for (Task task: tasks) {
            this.tasks.add(task);
        }
    }

    public void addTask(Task task) {
        this.tasks.add(task);
    }

    public void removeTask(int index) {
        this.tasks.remove(index);
    }

    public Task getTask(int index) {
        return this.tasks.get(index);
    }

    public Task[] getTasks() {
        return this.tasks.toArray(new Task[getSize()]);
    }

    public int getSize() {
        return this.tasks.size();
    }

    public TaskList filterActive(LocalDateTime targetDateTime) {
        TaskList filteredTasks = new TaskList();

        // filter all relevant tasks
        for (int i = 0; i < this.tasks.size(); i++) {
            if (tasks.get(i).isActiveOn(targetDateTime)) {
                filteredTasks.addTask(this.tasks.get(i));
            }
        }

        return filteredTasks;
    }

    @Override
    public String toString() {
        StringBuilder taskListing = new StringBuilder();
        for (int i = 0; i < this.tasks.size(); i++) {
            taskListing.append((i+1) + ". " + this.tasks.get(i) + "\n");
        }
        return taskListing.toString();
    }
}
