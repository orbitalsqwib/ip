package cue.command;

import cue.Cue;
import cue.gui.CommandableInterface;
import cue.tasks.TaskList;

/**
 * Encloses a collection of shared application utilities that a Command may act upon.
 * Includes utilities such as local storage, user interfaces and termination callback handlers.
 */
public class CommandContext {
    public final TaskList tasklist;
    public final CommandableInterface ui;
    public final Cue cue;

    /**
     * Creates a new command context
     *
     * @param taskList The current task list
     * @param ui       The current UI
     * @param cue      The current Cue agent
     */
    public CommandContext(TaskList taskList, CommandableInterface ui, Cue cue) {
        this.tasklist = taskList;
        this.ui = ui;
        this.cue = cue;
    }
}
