package cue.command.commands;

import cue.command.Command;
import cue.command.CommandContext;
import cue.errors.CueException;
import cue.parser.CommandParser;
import cue.tasks.Task;

/**
 * Marks a task as done (or undone) based on the mark (or unmark) command keyword.
 */
public class MarkCommand implements Command {
    @Override
    public void execute(CommandContext context, CommandParser.Result input) throws CueException {
        int targetIndex = Integer.parseInt(input.getBody());
        boolean isTaskDone = input.getKeyword().equals("mark");

        Task targetTask =  context.getTaskList().getTask(targetIndex - 1);
        targetTask.setDone(isTaskDone);

        if (isTaskDone) {
            context.getCli().print("Nice! I've marked this task as done:");
        } else {
            context.getCli().print("OK, I've marked this task as not yet done:");
        }

        context.getCli().printIndented(targetTask.toString());
    }
}
