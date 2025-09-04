package cue.command.commands;

import cue.command.Command;
import cue.command.CommandContext;
import cue.errors.CueException;
import cue.formatter.StringFormatter;
import cue.parser.CommandParser;

/**
 * Lists all tasks in a TaskList through the command line interface.
 */
public class ListCommand implements Command {
    @Override
    public void execute(CommandContext context, CommandParser.Result input) throws CueException {
        String output = StringFormatter.joinWithNewlines(
                "Here are the tasks in your list:",
                StringFormatter.indent(context.tasklist.toString()));

        context.ui.display(output);
    }
}
