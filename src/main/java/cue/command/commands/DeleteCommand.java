package cue.command.commands;

import cue.command.Command;
import cue.command.CommandContext;
import cue.errors.CueException;
import cue.parser.CommandParser;
import cue.tasks.Task;

/**
 * Deletes a Task at a specified index from a TaskList.
 */
public class DeleteCommand implements Command {
    @Override
    public void execute(CommandContext context, CommandParser.Result input) throws CueException {
        int targetIndex = Integer.parseInt(input.getBody());
        Task targetTask = context.tasklist.getTask(targetIndex - 1);
        context.tasklist.removeTask(targetIndex - 1);

        context.cli.print("OK, I've removed this task for you:");
        context.cli.print("  " + targetTask);
        context.cli.print("Now you have " + context.tasklist.getSize() + " tasks in the list");
    }
}
