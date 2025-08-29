package cue.command.commands;

import cue.command.Command;
import cue.command.CommandContext;
import cue.datetime.StringDateTime;
import cue.errors.CueException;
import cue.errors.InvalidFormatException;
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

        System.out.println("Here are the tasks for " + targetDate + ":");

        // filter all relevant tasks
        TaskList filteredTasks = context.getTaskList().filterActive(targetDate.toLocalDateTime());

        context.getCli().printIndented(filteredTasks.toString());
    }
}
