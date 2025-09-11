package cue.command.commands;

import cue.command.Command;
import cue.command.CommandContext;
import cue.errors.CueException;
import cue.formatter.StringFormatter;
import cue.parser.CommandParser;
import cue.tasks.TaskList;

/**
 * Lists all tasks in a TaskList through the command line interface.
 */
public class ReminderCommand implements Command {
    @Override
    public void execute(CommandContext context, CommandParser.Result input) throws CueException {
        TaskList dateSensitiveTasks = context.tasklist.filterDateSensitive();
        String output = StringFormatter.joinWithNewlines(
                "You have " + dateSensitiveTasks.getSize() + " date sensitive task(s):",
                StringFormatter.indent(dateSensitiveTasks.toString()));

        context.ui.display(output);
    }
}
