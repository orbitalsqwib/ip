package cue.command.commands;

import cue.command.Command;
import cue.command.CommandContext;
import cue.errors.CueException;
import cue.parser.CommandParser;

public class ExitCommand implements Command {
    @Override
    public void execute(CommandContext context, CommandParser.Result input) throws CueException {
        context.getCli().print("Bye. Hope to see you again soon!");
        context.getCli().stop();
    }
}
