package cue.storage.errors;

import cue.errors.CueException;

/**
 * A Cue exception that should be thrown when the a line in the savefile is malformed and cannot be parsed
 */
public class SavefileLineParseFailure extends CueException {

    /**
     * Creates a InvalidCommandPatternException informing the user that a line in the savefile could not be processed
     *
     * @param line The line that could not be parsed
     */
    public SavefileLineParseFailure(String line) {
        super("Failed to parse the following line: " + line + "!");
    }

}
