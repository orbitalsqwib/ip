package cue.command.commands;

import cue.command.Command;
import cue.command.CommandContext;
import cue.errors.CueException;
import cue.formatter.StringFormatter;
import cue.parser.CommandParser.Result;
import cue.tasks.TaskList;

/**
 * Filters tasks in the tasklist that contain a given substring
 */
public class FindCommand implements Command {
    @Override
    public void execute(CommandContext context, Result input) throws CueException {
        TaskList filteredTasks = context.tasklist.filterNameContains(input.getBody());

        String output = StringFormatter.joinWithNewlines(
                "Here are all tasks that match the search term \"" + input.getBody() + "\":",
                StringFormatter.indent(filteredTasks.toString()));

        context.ui.display(output);
    }
}
