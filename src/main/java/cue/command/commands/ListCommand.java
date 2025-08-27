package cue.command.commands;

import cue.command.Command;
import cue.command.CommandContext;
import cue.errors.CueException;
import cue.parser.CommandParser;

public class ListCommand implements Command {
    @Override
    public void execute(CommandContext context, CommandParser.Result input) throws CueException {
        context.getCli().print("Here are the tasks in your list:");
        context.getCli().printIndented(context.getTaskList().toString());
    }
}
