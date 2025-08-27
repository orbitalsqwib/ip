package cue.command;

import cue.errors.CueException;
import cue.parser.CommandParser;

public interface Command {
    public abstract void execute(CommandContext context, CommandParser.Result input) throws CueException;
}
