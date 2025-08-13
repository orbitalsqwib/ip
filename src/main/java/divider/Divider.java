package divider;

/**
 * Generates and prints visual dividers.
 */
public class Divider {

    /**
     * The string content of the divider, generated and cached on construction.
     */
    private String divider;

    /**
     * Builds a visual divider using dividerChar that spans a fixed length.
     * Caches the resulting divider in the Divider instance.
     * @param length The length of the divider in characters
     * @param dividerChar The character used to form the divider
     */
    public void initializeDivider(int length, char dividerChar) {
        StringBuilder dividerBuilder = new StringBuilder(length);

        // generate the divider through the string builder
        for (int i = 0; i < length; i++) {
            dividerBuilder.append(dividerChar);
        }
        dividerBuilder.append('\n');

        this.divider = dividerBuilder.toString();
    }

    /**
     * Creates a visual divider using custom characters that spans a fixed length.
     * @param length The length of the divider in characters
     * @param dividerChar The character used to form the divider
     */
    public Divider(int length, char dividerChar) {
        initializeDivider(length, dividerChar);
    }

    /**
     * Creates a visual divider that spans a fixed length.
     * @param length The length of the divider in characters
     */
    public Divider(int length) {
        initializeDivider(length, '─');
    }

    /**
     * Prints the generated divider, without recreating it.
     */
    public void print() {
        System.out.println(divider);
    }
}
