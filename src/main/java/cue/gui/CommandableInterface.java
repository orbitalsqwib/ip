package cue.gui;

/**
 * Specifies an interface that accepts command outputs
 */
public interface CommandableInterface {

    /**
     * Should display the specified output string, whether overwriting or appending to the current display.
     * @param output The string to be displayed
     */
    public abstract void display(String output);
}
