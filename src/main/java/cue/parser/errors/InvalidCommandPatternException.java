package cue.parser.errors;

import cue.errors.CueException;

/**
 * A Cue exception that should be thrown when the command pattern is malformed and cannot be parsed
 * Example: <code>event /from a /to b</code> - the body, which would be the event title, is missing.
 */
public class InvalidCommandPatternException extends CueException {

    /**
     * Creates a InvalidCommandPatternException informing the user that the command pattern is invalid.
     */
    public InvalidCommandPatternException() {
        super("This command cannot be recognized!");
    }

}
