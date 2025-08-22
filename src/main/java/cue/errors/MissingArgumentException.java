package cue.errors;

/**
 * A Cue exception that should be thrown when a command is missing a required argument for its function.
 */
public class MissingArgumentException extends CueException {

    /**
     * Creates a MissingArgumentException with an informative error message detailing which argument is missing.
     * @param missingArg The argument that is missing from the current command.
     */
    public MissingArgumentException(String missingArg) {
        super("No value found for " + missingArg + "!");
    }

    /**
     * Creates a MissingArgumentException with an informative error message detailing which argument is missing.
     * Additionally, specifies concrete steps to fix the issue.
     * @param missingArg The argument that is missing from the current command.
     * @param fixHint A hint for the user on how to provide the missing argument.
     */
    public MissingArgumentException(String missingArg, String fixHint) {
        super("No value found for " + missingArg + "! " + fixHint);
    }
}
