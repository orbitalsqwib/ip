package cue.command.commands;

import cue.command.Command;
import cue.command.CommandContext;
import cue.errors.CueException;
import cue.parser.CommandParser;

/**
 * Lists all tasks in a TaskList through the command line interface.
 */
public class ListCommand implements Command {
    @Override
    public void execute(CommandContext context, CommandParser.Result input) throws CueException {
        context.cli.print("Here are the tasks in your list:");
        context.cli.printIndented(context.tasklist.toString());
    }
}
