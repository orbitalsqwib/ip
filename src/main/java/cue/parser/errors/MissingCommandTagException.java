package cue.parser.errors;

import cue.errors.CueException;

/**
 * A Cue exception that should be thrown when the tag for a command is missing, though required.
 * Example: <code>event test /to b</code> - the <code>/from</code> tag is missing.
 */
public class MissingCommandTagException extends CueException {

    /**
     * Creates a MissingCommandTagException informing the user of the missing tag
     * @param tag The name of the missing tag
     */
    public MissingCommandTagException(String tag) {
        super("No value found for the /" + tag + " argument!");
    }

}