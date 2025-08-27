package cue.command;

import cue.tasks.TaskList;
import cue.ui.CommandLineInterface;

public class CommandContext {
    private final TaskList tasklist;
    private final CommandLineInterface cli;

    public CommandContext(TaskList taskList, CommandLineInterface cli) {
        this.tasklist = taskList;
        this.cli = cli;
    }

    public TaskList getTaskList() {
        return this.tasklist;
    }

    public CommandLineInterface getCli() {
        return this.cli;
    }
}
