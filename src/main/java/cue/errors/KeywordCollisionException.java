package cue.errors;

/**
 * A Cue exception that should be thrown when more than one command is registered to a single keyword
 */
public class KeywordCollisionException extends CueException {

    /**
     * Creates a KeywordCollisionException with an informative error message detailing the collided keyword to the user.
     */
    public KeywordCollisionException(String collidedKeyword) {
        super("Sorry, you can't register more than one command to the keyword: " + collidedKeyword);
    }
}
