package cue.command;

import cue.tasks.TaskList;
import cue.ui.CommandLineInterface;

public class CommandContext {
    private final TaskList tasklist;
    private final CommandLineInterface cli;
    private final Runnable quitFn;

    public CommandContext(TaskList taskList, CommandLineInterface cli, Runnable quitFn) {
        this.tasklist = taskList;
        this.cli = cli;
        this.quitFn = quitFn;
    }

    public TaskList getTaskList() {
        return this.tasklist;
    }

    public CommandLineInterface getCli() {
        return this.cli;
    }

    public void quit() {
        quitFn.run();
    }
}
