package cue.errors;

/**
 * Checked Cue exception that provides some standard formatting for Cue related errors.
 */
public class CueException extends Exception {

    /**
     * Constructs a Cue exception with an error message.
     * @param errorMessage An informative error message that can be read by the user to diagnose problems.
     */
    public CueException(String errorMessage) {
        super("Oops, something went wrong: " + errorMessage);
    }
}
