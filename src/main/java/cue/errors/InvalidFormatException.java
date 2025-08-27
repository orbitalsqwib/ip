package cue.errors;

/**
 * A Cue exception that should be thrown when a user-inputted string fails to meet an expected format.
 */
public class InvalidFormatException extends CueException {
    /**
     * Creates a InvalidFormatException with an informative error message detailing the expected format to the user.
     */
    public InvalidFormatException(String expectedFormat) {
        super("The command did not meet the expected format! The expected format is: " + expectedFormat);
    }
}
