package cue.tasks;

import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * A list of Tasks.
 */
public class TaskList {
    private final ArrayList<Task> tasks;

    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    /**
     * Creates a new TaskList from an array of tasks.
     *
     * @param tasks An array of tasks to populate the TaskList with
     */
    public TaskList(Task[] tasks) {
        this.tasks = new ArrayList<>();

        // add all tasks from src array
        for (Task task : tasks) {
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

    /**
     * Filters all active Tasks for the given datetime
     *
     * @param targetDateTime The target datetime to filter tasks by
     * @return A TaskList containing all active tasks for the target datetime
     */
    public TaskList filterActive(LocalDateTime targetDateTime) {
        return new TaskList(
                tasks.stream().filter(task -> task
                        .isActiveOn(targetDateTime))
                        .toArray(len -> new Task[len]));
    }

    /**
     * Filters all Task descriptions for the given search term
     *
     * @param searchTerm The target term to filter tasks by
     * @return A TaskList containing all active tasks for the target datetime
     */
    public TaskList filterNameContains(String searchTerm) {
        return new TaskList(
                tasks.stream().filter(task -> task
                        .nameContains(searchTerm))
                        .toArray(len -> new Task[len]));
    }

    /**
     * Returns all tasks which are date sensitive
     *
     * @return A TaskList containing all date sensitive tasks
     */
    public TaskList filterDateSensitive() {
        return new TaskList(
                tasks.stream().filter(task -> task
                        .isDateSensitive())
                        .toArray(len -> new Task[len]));
    }

    @Override
    public String toString() {
        StringBuilder taskListing = new StringBuilder();
        for (int i = 0; i < this.tasks.size(); i++) {
            taskListing.append((i + 1) + ". " + this.tasks.get(i) + "\n");
        }
        return taskListing.toString();
    }
}
