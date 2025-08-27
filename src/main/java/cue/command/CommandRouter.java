package cue.command;

import java.util.HashMap;

import cue.errors.KeywordCollisionException;
import cue.errors.UnknownCommandException;

public class CommandRouter {
    private HashMap<String, Command> commandMap;

    public CommandRouter() {
        this.commandMap = new HashMap<>();
    }

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

    public Command route(String keyword) throws UnknownCommandException {
        Command result = commandMap.get(keyword);
        if (result == null) {
            throw new UnknownCommandException();
        }

        return result;
    }
}
