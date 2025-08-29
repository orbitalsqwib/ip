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
        Task targetTask = context.getTaskList().getTask(targetIndex - 1);
        context.getTaskList().removeTask(targetIndex - 1);

        context.getCli().print("OK, I've removed this task for you:");
        context.getCli().print("  " + targetTask);
        context.getCli().print("Now you have " + context.getTaskList().getSize() + " tasks in the list");
    }
}
