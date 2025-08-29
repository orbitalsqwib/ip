package cue.ui;

import java.util.Scanner;

/**
 * Handles user interactions through the command line interface for the Cue
 * agent
 */
public class CommandLineInterface {
    private static final String textLogo = "\n  ▄▄▄x  ▄▄▄ x▄▄    ▄▄▄o  \n"
            + "o█   ▀▀  ██  ██  ▄█▄▄x██ \n"
            + "██       ██  ██  ██      \n"
            + " ▀█x▄▄▀  ▀█o▄▀█x  ▀█x▄▄▀ \n";

    private DividerPrinter dividerPrinter;
    private Scanner inputScanner;

    /**
     * Creates a new command line interface.
     *
     * @param terminalWidth The number of characters wide the command line interface
     *                      should be
     */
    public CommandLineInterface(int terminalWidth) {
        this.dividerPrinter = new DividerPrinter(terminalWidth);
        this.inputScanner = new Scanner(System.in);
    }

    /**
     * Prints a greeting message for the user on the command line.
     */
    public void greet() {
        dividerPrinter.print();
        System.out.println("Hello, I'm\n" + textLogo + "\nWhat can I do for you?");
        dividerPrinter.print();
    }

    public String getInput() {
        System.out.print("me > ");
        return inputScanner.nextLine().strip();
    }

    public void cleanup() {
        inputScanner.close();
    }

    public void printIndented(String originalText) {
        System.out.println("  " + originalText.strip().replace("\n", "\n  "));
    }

    public void print(String originalText) {
        System.out.println(originalText);
    }

    public void printDivider() {
        this.dividerPrinter.print();
    }
}
