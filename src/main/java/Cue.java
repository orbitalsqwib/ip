import java.util.ArrayList;
import java.util.Scanner;

import cue.data.SaveFile;
import cue.datetime.StringDateTime;
import cue.divider.*;
import cue.errors.CueException;
import cue.errors.MissingArgumentException;
import cue.errors.UnknownCommandException;
import cue.tasks.*;

public class Cue {
    public static void main(String[] args) {
        Divider div = new Divider(80);
        String logo = "\n  ▄▄▄x  ▄▄▄ x▄▄    ▄▄▄o  \n"
                + "o█   ▀▀  ██  ██  ▄█▄▄x██ \n"
                + "██       ██  ██  ██      \n"
                + " ▀█x▄▄▀  ▀█o▄▀█x  ▀█x▄▄▀ \n";

        div.print();
        System.out.println("Hello, I'm\n" + logo + "\nWhat can I do for you?");
        div.print();

        ArrayList<Task> tasks = SaveFile.load();

        Scanner inputScanner = new Scanner(System.in);
        boolean isExit = false;
        while (!isExit) {
            System.out.print("me > ");
            String input = inputScanner.nextLine().strip();
            div.print();
            try {
                switch (input) {
                    case "bye":
                        isExit = true;
                        break;

                    case "list":
                        System.out.println("Here are the tasks in your list:");
                        for (int i = 0; i < tasks.size(); i++) {
                            System.out.println(" " + (i+1) + ". " + tasks.get(i));
                        }
                        div.print();
                        break;

                    case "":
                        throw new UnknownCommandException();

                    default:
                        if (input.startsWith("mark") || input.startsWith("unmark")) {
                            String[] inputArgs = input.split(" ");

                            int targetIndex = Integer.parseInt(inputArgs[1]);
                            boolean isDone = input.startsWith("mark");
                            Task targetTask =  tasks.get(targetIndex - 1);

                            targetTask.setDone(isDone);

                            if (isDone) {
                                System.out.println("Nice! I've marked this task as done:\n");
                            } else {
                                System.out.println("OK, I've marked this task as not done yet:\n");
                            }

                            System.out.println("  " + targetTask);
                            div.print();
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
                                for (int i = 0; i < tasks.size(); i++) {
                                    if (tasks.get(i).isActiveOn(targetDate.toLocalDateTime())) {
                                        System.out.println(" " + (i+1) + ". " + tasks.get(i));
                                    }
                                }

                               div.print();
                            }
                        } else if (input.startsWith("delete")) {
                            String[] inputArgs = input.split(" ");

                            int targetIndex = Integer.parseInt(inputArgs[1]);
                            Task targetTask = tasks.get(targetIndex - 1);
                            tasks.remove(targetIndex - 1);

                            System.out.println("OK, I've removed this task for you:");
                            System.out.println("  " + targetTask);
                            System.out.println("Now you have " + tasks.size() + " tasks in the list");
                            div.print();
                        } else if (input.startsWith("todo")) {
                            Todo newTodo = new Todo(input.replace("todo", "").strip());
                            tasks.add(newTodo);

                            System.out.println("Got it. I've added this task:");
                            System.out.println("  " + newTodo);
                            System.out.println("Now you have " + tasks.size() + " tasks in the list");
                            div.print();
                        } else if (input.startsWith("deadline")) {
                            String[] inputArgs = input.replace("deadline", "").split(" /by ");

                            // ensure all arguments are provided
                            if (inputArgs.length < 2) {
                                throw new MissingArgumentException("/by",
                                "Please specify the deadline using `... /by [date] ...`.");
                            }

                            Deadline newDeadline = new Deadline(inputArgs[0].strip(), inputArgs[1].strip());
                            tasks.add(newDeadline);

                            System.out.println("Got it. I've added this task:");
                            System.out.println("  " + newDeadline);
                            System.out.println("Now you have " + tasks.size() + " tasks in the list");
                            div.print();
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
                            tasks.add(newEvent);

                            System.out.println("Got it. I've added this task:");
                            System.out.println("  " + newEvent);
                            System.out.println("Now you have " + tasks.size() + " tasks in the list");
                            div.print();
                        } else {
                            throw new UnknownCommandException();
                        }
                        break;
                }
                SaveFile.save(tasks);
            } catch (CueException error) {
                System.out.println(error.getMessage());
                div.print();
            }
        }
        inputScanner.close();

        System.out.println("Bye. Hope to see you again soon!");
        div.print();
    }
}
