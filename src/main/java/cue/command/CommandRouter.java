package cue.command;

import java.util.HashMap;

import cue.errors.KeywordCollisionException;
import cue.errors.UnknownCommandException;

/**
 * Handles the registration and routing of command keywords to commands
 */
public class CommandRouter {
    private HashMap<String, Command> commandMap;

    /**
     * Creates a new empty CommandRouter;
     */
    public CommandRouter() {
        this.commandMap = new HashMap<>();
    }

    /**
     * Registers a Command to several keywords, and stores it for later routing. Keywords registered must be unique.
     * @param command The Command executable to be registered in the CommandRouter.
     * @param keywords The list of keywords that the Command should be registered to.
     * @throws KeywordCollisionException Thrown if a keyword that is being registered has already been registered.
     */
    public void register(Command command, String[] keywords) throws KeywordCollisionException {
        // ensure that all keywords are non-colliding
        for (String keyword: keywords) {
            if (commandMap.containsKey(keyword)) {
                throw new KeywordCollisionException(keyword);
            }
        }

        // register command once all collision checks are complete
        for (String keyword: keywords) {
            commandMap.put(keyword, command);
        }
    }

    /**
     * Returns the registered Command for a specific keyword.
     * @param keyword The keyword for lookup.
     * @return A Command previously registered to the specified keyword.
     * @throws UnknownCommandException Thrown if no command can be found for the specified keyword.
     */
    public Command route(String keyword) throws UnknownCommandException {
        Command result = commandMap.get(keyword);
        if (result == null) {
            throw new UnknownCommandException();
        }

        return result;
    }
}
