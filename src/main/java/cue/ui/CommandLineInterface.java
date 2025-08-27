package cue.ui;

import java.util.Scanner;

import cue.datetime.StringDateTime;
import cue.errors.CueException;
import cue.errors.InvalidFormatException;
import cue.errors.UnknownCommandException;
import cue.parser.CommandParser;
import cue.storage.TaskStorage;
import cue.tasks.Deadline;
import cue.tasks.Event;
import cue.tasks.Task;
import cue.tasks.TaskList;
import cue.tasks.Todo;

public class CommandLineInterface {
    private DividerPrinter dividerPrinter;
    private boolean isRunning;
    private TaskList taskList;
    private Scanner inputScanner;

    private static final String textLogo = "\n  ▄▄▄x  ▄▄▄ x▄▄    ▄▄▄o  \n"
                + "o█   ▀▀  ██  ██  ▄█▄▄x██ \n"
                + "██       ██  ██  ██      \n"
                + " ▀█x▄▄▀  ▀█o▄▀█x  ▀█x▄▄▀ \n";

    private void greet() {
        dividerPrinter.print();
        System.out.println("Hello, I'm\n" + textLogo + "\nWhat can I do for you?");
        dividerPrinter.print();
    }

    private void goodbye() {
        System.out.println("Bye. Hope to see you again soon!");
        dividerPrinter.print();
    }

    private String promptForInput() {
        System.out.print("me > ");
        return inputScanner.nextLine().strip();
    }

    private void printIndented(String originalText) {
        System.out.println("  " + originalText.strip().replace("\n", "\n  "));
    }

    private void handleInput() throws CueException {
        String input = promptForInput();
        dividerPrinter.print();

        CommandParser.Result parsed = CommandParser.parse(input);

        switch (parsed.getKeyword()) {
        case "bye":
            stop();
            break;

        case "list":
            System.out.println("Here are the tasks in your list:");
            printIndented(taskList.toString());
            dividerPrinter.print();
            break;

        case "mark":
        case "unmark": {
            int targetIndex = Integer.parseInt(parsed.getBody());
            boolean isTaskDone = parsed.getKeyword().equals("mark");

            Task targetTask =  taskList.getTask(targetIndex - 1);
            targetTask.setDone(isTaskDone);

            if (isTaskDone) {
                System.out.println("Nice! I've marked this task as done:");
            } else {
                System.out.println("OK, I've marked this task as not yet done:");
            }

            printIndented(targetTask.toString());
            dividerPrinter.print();
            break;
        }

        case "delete": {
            int targetIndex = Integer.parseInt(parsed.getBody());
            Task targetTask = taskList.getTask(targetIndex - 1);
            taskList.removeTask(targetIndex - 1);

            System.out.println("OK, I've removed this task for you:");
            System.out.println("  " + targetTask);
            System.out.println("Now you have " + taskList.getSize() + " tasks in the list");
            dividerPrinter.print();
            break;
        }

        case "summary":
            StringDateTime targetDate = new StringDateTime(parsed.getBody());

            if (targetDate.toLocalDateTime() == null) {
                throw new InvalidFormatException("summary yyyy-MM-dd[@HHmm] ([] denotes an optional section.)");
            }

            System.out.println("Here are the tasks for " + targetDate + ":");

            // filter all relevant tasks
            TaskList filteredTasks = taskList.filterActive(targetDate.toLocalDateTime());

            printIndented(filteredTasks.toString());
            dividerPrinter.print();
            break;

        case "todo":
        case "deadline":
        case "event": {
            Task newTask = switch (parsed.getKeyword()) {
                case "todo" -> new Todo(parsed.getBody());
                case "deadline" -> new Deadline(parsed.getBody(), parsed.getTag("by"));
                case "event" -> new Event(parsed.getBody(), parsed.getTag("from"), parsed.getTag("to"));
                default -> null;
            };

            if (newTask != null) {
                taskList.addTask(newTask);

                System.out.println("Got it. I've added this task:");
                printIndented(newTask.toString());
                System.out.println("Now you have " + taskList.getSize() + " tasks in the list");
                dividerPrinter.print();
            }
            break;
        }

        default:
            throw new UnknownCommandException();
        }
    }

    public CommandLineInterface(int terminalWidth, TaskList taskList) {
        this.dividerPrinter = new DividerPrinter(terminalWidth);
        this.inputScanner = new Scanner(System.in);
        this.isRunning = false;
        this.taskList = taskList;
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
    }

    public void stop() {
        this.isRunning = false;
        inputScanner.close();
        goodbye();
    }
}
