package cue.command.commands;

import cue.command.Command;
import cue.command.CommandContext;
import cue.errors.CueException;
import cue.parser.CommandParser.Result;
import cue.tasks.TaskList;

/**
 * Filters tasks in the tasklist that contain a given substring
 */
public class FindCommand implements Command {
    @Override
    public void execute(CommandContext context, Result input) throws CueException {
        TaskList filteredTasks = context.tasklist.filterNameContains(input.getBody());

        context.cli.print("Here are all tasks that match the search term \"" + input.getBody() + "\":");
        context.cli.printIndented(filteredTasks.toString());
    }
}
