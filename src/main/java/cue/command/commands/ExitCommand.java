package cue.command.commands;

import cue.command.Command;
import cue.command.CommandContext;
import cue.errors.CueException;
import cue.parser.CommandParser;

/**
 * Sends a termination signal to the current context.
 */
public class ExitCommand implements Command {
    @Override
    public void execute(CommandContext context, CommandParser.Result input) throws CueException {
        context.ui.display("Bye. Hope to see you again soon!");
        try {
            context.cue.stop();
        } catch (Exception error) {
            throw new CueException(error.getMessage());
        }
    }
}
