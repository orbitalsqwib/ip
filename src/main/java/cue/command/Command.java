package cue.command;

import cue.errors.CueException;
import cue.parser.CommandParser;

/**
 * Contains instructions that can be executed by a user through specific keywords.
 * Each command input may be followed by additional arguments or tags, which may change the behaviour of the command.
 */
public interface Command {

    /**
     * Contains arbitrary actions that can act on the application context on behalf of a user, based on their inputs.
     * @param context The current application context, including storage, user interfaces and other utilties.
     * @param input The command input given by the user, parsed into a keyword, body and additional data tags.
     * @throws CueException Thrown when the command runs into an invalid state, or if an invalid action would be done.
     */
    public abstract void execute(CommandContext context, CommandParser.Result input) throws CueException;
}
