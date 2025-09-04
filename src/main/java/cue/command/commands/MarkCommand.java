package cue.command.commands;

import cue.command.Command;
import cue.command.CommandContext;
import cue.errors.CueException;
import cue.formatter.StringFormatter;
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

        Task targetTask = context.tasklist.getTask(targetIndex - 1);
        targetTask.setDone(isTaskDone);

        String taskMarkMessage = "Nice! I've marked this task as done:";
        if (!isTaskDone) {
            taskMarkMessage = "OK, I've marked this task as not yet done:";
        }

        String output = StringFormatter.joinWithNewlines(
                taskMarkMessage,
                StringFormatter.indent(targetTask.toString()));

        context.ui.display(output);
    }
}
