package cue.parser;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cue.parser.errors.InvalidCommandPatternException;
import cue.parser.errors.MissingCommandBodyException;
import cue.parser.errors.MissingCommandTagException;

public class CommandParser {

    private final static Pattern COMMAND_PATTERN = Pattern.compile("^(\\S+) ?([^/]+)?([\\s\\S]+)?");
    private final static Pattern TAG_PATTERN = Pattern.compile("^(\\S+) (.+)");

    public static class Result {
        private final String keyword;
        private final String body;
        private final HashMap<String, String> tags;

        private Result(String keyword, String body, HashMap<String, String> tags) {
            this.keyword = keyword;
            this.body = body;
            this.tags = tags;
        }

        public String getKeyword() {
            return this.keyword;
        }

        public String getBody() throws MissingCommandBodyException {
            if (this.body == null) {
                throw new MissingCommandBodyException();
            }
            return this.body;
        }

        public String getTag(String tag) throws MissingCommandTagException {
            if (tags.get(tag) == null) {
                throw new MissingCommandTagException(tag);
            }
            return tags.get(tag);
        }
    }

    public static Result parse(String input) throws InvalidCommandPatternException {
        Matcher commandMatcher = COMMAND_PATTERN.matcher(input);

        if (!commandMatcher.find()) {
            throw new InvalidCommandPatternException();
        }

        String keyword = commandMatcher.group(1);
        String body = commandMatcher.group(2);

        // because body is multi-whitespace input, trim whitespace if possible.
        if (body != null) {
            body = body.strip();
        }

        HashMap<String, String> tags = new HashMap<>();

        if (commandMatcher.group(3) != null) {
            String[] tagGroups = commandMatcher.group(3).split("/");

            for (String tagGroup: tagGroups) {
                Matcher tagMatcher = TAG_PATTERN.matcher(tagGroup);

                if (!tagMatcher.find()) {
                    continue;
                }

                if (tagMatcher.group(1) != null && tagMatcher.group(2) != null) {
                    // trim tag content multi-whitespace input as well
                    tags.put(tagMatcher.group(1), tagMatcher.group(2).strip());
                }
            }
        }

        return new Result(keyword, body, tags);
    }
}
