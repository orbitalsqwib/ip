package cue.command;

import cue.Cue;
import cue.tasks.TaskList;
import cue.ui.CommandLineInterface;

public class CommandContext {
    public final TaskList tasklist;
    public final CommandLineInterface cli;
    public final Cue cue;

    public CommandContext(TaskList taskList, CommandLineInterface cli, Cue cue) {
        this.tasklist = taskList;
        this.cli = cli;
        this.cue = cue;
    }
}
