package cue.errors;

/**
 * A Cue exception that should be thrown when the chatbot recieves an unknown command.
 */
public class UnknownCommandException extends CueException {

    /**
     * Creates a UnknownCommandException with a standardised message
     */
    public UnknownCommandException() {
        super("Sorry, I don't recognise this command...");
    }
}
