package cue.parser.errors;

import cue.errors.CueException;

/**
 * A Cue exception that should be thrown when a command is missing its body though required.
 * Example: <code>event /from a /to b</code> - the body, which would be the event title, is missing.
 */
public class MissingCommandBodyException extends CueException {

    /**
     * Creates a MissingCommandBodyException informing the user that the body of the command is missing
     */
    public MissingCommandBodyException() {
        super("Expected content after the keyword, but none found!");
    }

}