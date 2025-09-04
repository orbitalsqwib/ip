package cue.command.commands;

import cue.command.Command;
import cue.command.CommandContext;
import cue.datetime.StringDateTime;
import cue.errors.CueException;
import cue.errors.InvalidFormatException;
import cue.formatter.StringFormatter;
import cue.parser.CommandParser;
import cue.tasks.TaskList;

/**
 * Filters and displays a list of tasks that are active at the specified datetime.
 */
public class SummaryCommand implements Command {
    @Override
    public void execute(CommandContext context, CommandParser.Result input) throws CueException {
        StringDateTime targetDate = new StringDateTime(input.getBody());

        if (targetDate.toLocalDateTime() == null) {
            throw new InvalidFormatException("summary yyyy-MM-dd[@HHmm] ([] denotes an optional section.)");
        }

        // filter all relevant tasks
        TaskList filteredTasks = context.tasklist.filterActive(targetDate.toLocalDateTime());

        String output = StringFormatter.joinWithNewlines(
                "Here are the tasks for " + targetDate + ":",
                StringFormatter.indent(filteredTasks.toString()));

        context.ui.display(output);
    }
}
