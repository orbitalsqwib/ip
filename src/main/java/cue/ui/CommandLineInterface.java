package cue.ui;

import java.util.Scanner;

public class CommandLineInterface {
    private DividerPrinter dividerPrinter;
    private Scanner inputScanner;

    private static final String textLogo = "\n  ▄▄▄x  ▄▄▄ x▄▄    ▄▄▄o  \n"
                + "o█   ▀▀  ██  ██  ▄█▄▄x██ \n"
                + "██       ██  ██  ██      \n"
                + " ▀█x▄▄▀  ▀█o▄▀█x  ▀█x▄▄▀ \n";

    public void greet() {
        dividerPrinter.print();
        System.out.println("Hello, I'm\n" + textLogo + "\nWhat can I do for you?");
        dividerPrinter.print();
    }

    public String getInput() {
        System.out.print("me > ");
        return inputScanner.nextLine().strip();
    }

    public CommandLineInterface(int terminalWidth) {
        this.dividerPrinter = new DividerPrinter(terminalWidth);
        this.inputScanner = new Scanner(System.in);
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
