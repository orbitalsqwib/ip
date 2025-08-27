package cue.ui;

import java.util.Scanner;

import cue.command.CommandContext;
import cue.command.CommandRouter;
import cue.command.commands.CreateTaskCommand;
import cue.command.commands.DeleteCommand;
import cue.command.commands.ExitCommand;
import cue.command.commands.ListCommand;
import cue.command.commands.MarkCommand;
import cue.command.commands.SummaryCommand;
import cue.errors.CueException;
import cue.errors.KeywordCollisionException;
import cue.parser.CommandParser;
import cue.storage.TaskStorage;
import cue.tasks.TaskList;

public class CommandLineInterface {
    private DividerPrinter dividerPrinter;
    private boolean isRunning;
    private TaskList taskList;
    private Scanner inputScanner;
    private CommandRouter commandRouter;

    private static final String textLogo = "\n  ▄▄▄x  ▄▄▄ x▄▄    ▄▄▄o  \n"
                + "o█   ▀▀  ██  ██  ▄█▄▄x██ \n"
                + "██       ██  ██  ██      \n"
                + " ▀█x▄▄▀  ▀█o▄▀█x  ▀█x▄▄▀ \n";

    private void greet() {
        dividerPrinter.print();
        System.out.println("Hello, I'm\n" + textLogo + "\nWhat can I do for you?");
        dividerPrinter.print();
    }

    private String promptForInput() {
        System.out.print("me > ");
        return inputScanner.nextLine().strip();
    }

    private void handleInput() throws CueException {
        String rawInput = promptForInput();
        dividerPrinter.print();

        CommandParser.Result input = CommandParser.parse(rawInput);
        commandRouter
            .route(input.getKeyword())
            .execute(new CommandContext(taskList, this), input);
        dividerPrinter.print();
    }

    public CommandLineInterface(int terminalWidth, TaskList taskList) {
        this.dividerPrinter = new DividerPrinter(terminalWidth);
        this.inputScanner = new Scanner(System.in);
        this.isRunning = false;
        this.taskList = taskList;

        // register commands
        this.commandRouter = new CommandRouter();
        try {
            commandRouter.register(new ExitCommand(), new String[]{ "bye" });
            commandRouter.register(new ListCommand(), new String[]{ "list" });
            commandRouter.register(new MarkCommand(), new String[]{ "mark", "unmark" });
            commandRouter.register(new CreateTaskCommand(), new String[]{ "todo", "deadline", "event" });
            commandRouter.register(new DeleteCommand(), new String[]{ "delete" });
            commandRouter.register(new SummaryCommand(), new String[]{ "summary" });
        } catch (KeywordCollisionException error) {
            // this should not happen, except during development. in this case, throw an exception to warn the dev.
            throw new RuntimeException(error);
        }
    }

    public void start() {
        this.isRunning = true;
        greet();

        while (this.isRunning) {
            try {
                handleInput();
                TaskStorage.saveToDisk(taskList.getTasks());
            } catch (CueException error) {
                System.out.println(error.getMessage());
                dividerPrinter.print();
            }
        }

        inputScanner.close();
    }

    public void stop() {
        this.isRunning = false;
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
