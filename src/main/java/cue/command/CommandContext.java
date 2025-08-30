package cue.command;

import cue.Cue;
import cue.tasks.TaskList;
import cue.ui.CommandLineInterface;

/**
 * Encloses a collection of shared application utilities that a Command may act upon.
 * Includes utilities such as local storage, user interfaces and termination callback handlers.
 */
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
