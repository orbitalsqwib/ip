package cue.ui;

import java.util.Scanner;

import cue.datetime.StringDateTime;
import cue.errors.CueException;
import cue.errors.MissingArgumentException;
import cue.errors.UnknownCommandException;
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

        switch (input) {
                case "bye":
                    stop();
                    break;

                case "list":
                    System.out.println("Here are the tasks in your list:");
                    printIndented(taskList.toString());
                    dividerPrinter.print();
                    break;

                case "":
                    throw new UnknownCommandException();

                default:
                    if (input.startsWith("mark") || input.startsWith("unmark")) {
                        String[] inputArgs = input.split(" ");

                        int targetIndex = Integer.parseInt(inputArgs[1]);
                        boolean isDone = input.startsWith("mark");
                        Task targetTask =  taskList.getTask(targetIndex - 1);

                        targetTask.setDone(isDone);

                        if (isDone) {
                            System.out.println("Nice! I've marked this task as done:\n");
                        } else {
                            System.out.println("OK, I've marked this task as not done yet:\n");
                        }

                        System.out.println("  " + targetTask);
                        dividerPrinter.print();
                    } else if (input.startsWith("summary")) {
                        String[] inputArgs = input.split(" ");

                        if (inputArgs.length < 2) {
                            throw new MissingArgumentException("target date/time");
                        }

                        StringDateTime targetDate = new StringDateTime(inputArgs[1]);

                        if (targetDate.toLocalDateTime() == null) {
                            System.out.println(
                                "Sorry, please use the format ([]: optional fields): summary yyyy-MM-dd[@HHmm]");
                        } else {
                            System.out.println("Here are the tasks for " + targetDate + ":");

                            // filter all relevant tasks
                            TaskList filteredTasks = taskList.filterActive(targetDate.toLocalDateTime());

                            printIndented(filteredTasks.toString());
                            dividerPrinter.print();
                        }
                    } else if (input.startsWith("delete")) {
                        String[] inputArgs = input.split(" ");

                        int targetIndex = Integer.parseInt(inputArgs[1]);
                        Task targetTask = taskList.getTask(targetIndex - 1);
                        taskList.removeTask(targetIndex - 1);

                        System.out.println("OK, I've removed this task for you:");
                        System.out.println("  " + targetTask);
                        System.out.println("Now you have " + taskList.getSize() + " tasks in the list");
                        dividerPrinter.print();
                    } else if (input.startsWith("todo")) {
                        Todo newTodo = new Todo(input.replace("todo", "").strip());
                        taskList.addTask(newTodo);

                        System.out.println("Got it. I've added this task:");
                        System.out.println("  " + newTodo);
                        System.out.println("Now you have " + taskList.getSize() + " tasks in the list");
                        dividerPrinter.print();
                    } else if (input.startsWith("deadline")) {
                        String[] inputArgs = input.replace("deadline", "").split(" /by ");

                        // ensure all arguments are provided
                        if (inputArgs.length < 2) {
                            throw new MissingArgumentException("/by",
                            "Please specify the deadline using `... /by [date] ...`.");
                        }

                        Deadline newDeadline = new Deadline(inputArgs[0].strip(), inputArgs[1].strip());
                        taskList.addTask(newDeadline);

                        System.out.println("Got it. I've added this task:");
                        System.out.println("  " + newDeadline);
                        System.out.println("Now you have " + taskList.getSize() + " tasks in the list");
                        dividerPrinter.print();
                    } else if (input.startsWith("event")) {
                        String[] inputArgs = input.replace("event", "").split("/");
                        String taskName = inputArgs[0].strip();
                        String from = "";
                        String to = "";
                        for (int argIndex = 1; argIndex < inputArgs.length; argIndex++) {
                            if (inputArgs[argIndex].startsWith("from")) {
                                from = inputArgs[argIndex].replace("from ", "").strip();
                            } else if (inputArgs[argIndex].startsWith("to")) {
                                to = inputArgs[argIndex].replace("to ", "").strip();
                            }
                        }

                        // ensure all arguments are provided
                        if (from == "") {
                            throw new MissingArgumentException("/from",
                            "Please provide the start date using `... /from [date] ...`.");
                        } else if (to == "") {
                            throw new MissingArgumentException("/to",
                            "Please provide the end date using `... /to [date] ...`.");
                        }

                        Event newEvent = new Event(taskName, from, to);
                        taskList.addTask(newEvent);

                        System.out.println("Got it. I've added this task:");
                        System.out.println("  " + newEvent);
                        System.out.println("Now you have " + taskList.getSize() + " tasks in the list");
                        dividerPrinter.print();
                    } else {
                        throw new UnknownCommandException();
                    }
                    break;
            }
            TaskStorage.saveToDisk(taskList.getTasks());
    }

    public CommandLineInterface(int terminalWidth) {
        this.dividerPrinter = new DividerPrinter(terminalWidth);
        this.taskList = new TaskList(TaskStorage.loadFromDisk());
        this.inputScanner = new Scanner(System.in);
        this.isRunning = false;
    }

    public void start() {
        this.isRunning = true;
        greet();

        while (this.isRunning) {
            try {
                handleInput();
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
