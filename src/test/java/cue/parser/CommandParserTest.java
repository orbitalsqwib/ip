package cue.parser;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import cue.parser.CommandParser.Result;
import cue.parser.errors.InvalidCommandPatternException;
import cue.parser.errors.MissingCommandBodyException;
import cue.parser.errors.MissingCommandTagException;

public class CommandParserTest {
    @Test
    public void commandParser_parseSingleKeyword_success() {
        assertDoesNotThrow(() -> assertEquals("test", CommandParser.parse("test").getKeyword()));
    }

    @Test
    public void commandParser_parseEmptyString_exceptionThrown() {
        assertThrows(InvalidCommandPatternException.class, () -> CommandParser.parse(""));
    }

    @Test
    public void commandParser_parseSingleKeywordGetBody_exceptionThrown() {
        assertThrows(MissingCommandBodyException.class, () -> CommandParser.parse("test").getBody());
    }

    @Test
    public void commandParser_parseKeywordAndBody_success() {
        assertDoesNotThrow(() -> {
            Result input = CommandParser.parse("test body");
            assertEquals("test", input.getKeyword());
            assertEquals("body", input.getBody());
        });
    }

    @Test
    public void commandParser_parseKeywordAndMultiwordBody_success() {
        assertDoesNotThrow(() -> {
            Result input = CommandParser.parse("test body with many words");
            assertEquals("test", input.getKeyword());
            assertEquals("body with many words", input.getBody());
        });
    }

    @Test
    public void commandParser_parseKeywordAndMultiwordBodyWithTags_success() {
        assertDoesNotThrow(() -> {
            Result input = CommandParser.parse("test body with many words /tag1 a /tag2 b");
            assertEquals("test", input.getKeyword());
            assertEquals("body with many words", input.getBody());
            assertEquals("a", input.getTag("tag1"));
            assertEquals("b", input.getTag("tag2"));
        });
    }

    @Test
    public void commandParser_parseGetUnknownTag_exceptionThrown() {
        assertThrows(MissingCommandTagException.class,
            () -> CommandParser.parse("test body with many words /tag1 a /tag2 b").getTag("tag3"));
    }
}
