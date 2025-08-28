package cue.command;  //same package as the class being tested

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import cue.errors.CueException;
import cue.errors.KeywordCollisionException;
import cue.errors.UnknownCommandException;
import cue.parser.CommandParser.Result;

public class CommandRouterTest {

    private Command getMockCommand() {
        return new Command() {
                @Override
                public void execute(CommandContext context, Result input) throws CueException {}
        };
    }

    @Test
    public void commandRouter_register_success() {
        CommandRouter router = new CommandRouter();
        Command command = getMockCommand();

        assertDoesNotThrow(() -> router.register(command, new String[]{ "A" }));
    }

    @Test
    public void commandRouter_register_duplicateKeyword_exceptionThrown() {
        CommandRouter router = new CommandRouter();
        Command command = getMockCommand();

        assertThrows(KeywordCollisionException.class, () -> {
            router.register(command, new String[]{ "A" });
            router.register(command, new String[]{ "A" });
        });
    }

    @Test
    public void commandRouter_route_success() {
        CommandRouter router = new CommandRouter();
        Command commandA = getMockCommand();
        Command commandB = getMockCommand();

        assertDoesNotThrow(() -> {
            router.register(commandA, new String[]{ "A" });
            router.register(commandB, new String[]{ "B" });
            assertSame(commandA, router.route("A"));
        });
    }

    @Test
    public void commandRouter_route_unknownKeyword_exceptionThrown() {
        CommandRouter router = new CommandRouter();
        Command commandA = getMockCommand();
        Command commandB = getMockCommand();

        assertThrows(UnknownCommandException.class,() -> {
            router.register(commandA, new String[]{ "A" });
            router.register(commandB, new String[]{ "B" });
            router.route("C");
        });
    }
}
