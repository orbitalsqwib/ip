package cue.errors;

/**
 * A Cue exception that should be thrown when the savefile for the agent is missing.
 */
public class MissingSavefileException extends CueException {

    /**
     * Creates a MissingSavefileException with an error message informing the user that the savefile is missing.
     */
    public MissingSavefileException() {
        super("No savefile found!");
    }
}
