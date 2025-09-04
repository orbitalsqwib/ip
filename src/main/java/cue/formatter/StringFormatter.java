package cue.formatter;

/**
 * Provides utility methods to format strings
 */
public abstract class StringFormatter {
    public static String indent(String originalText) {
        return "  " + originalText.strip().replace("\n", "\n  ");
    }
    public static String joinWithNewlines(String... lines) {
        return String.join("\n", lines);
    }
}
