import java.util.Scanner;

import cue.divider.*;
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

        Task[] tasks = new Task[100];
        int nextInput = 0;

        Scanner inputScanner = new Scanner(System.in);
        boolean isExit = false;
        while (!isExit) {
            System.out.print("me > ");
            String input = inputScanner.nextLine().strip();
            div.print();

            switch (input) {
                case "bye":
                    isExit = true;
                    break;

                case "list":
                    for (int i = 0; i < nextInput; i++) {
                        System.out.println(" " + (i+1) + ". " + tasks[i]);
                    }
                    div.print();
                    break;

                default:
                    if (input.startsWith("mark") || input.startsWith("unmark")) {
                        String[] inputArgs = input.split(" ");

                        int targetIndex = Integer.parseInt(inputArgs[1]);
                        boolean isDone = input.startsWith("mark");
                        Task targetTask =  tasks[targetIndex - 1];

                        targetTask.setDone(isDone);

                        if (isDone) {
                            System.out.println("Nice! I've marked this task as done:\n");
                        } else {
                            System.out.println("OK, I've marked this task as not done yet:\n");
                        }

                        System.out.println("  " + targetTask);
                        div.print();
                    } else if (input.startsWith("todo")) {
                        Todo newTodo = new Todo(input.replace("todo", ""));
                        tasks[nextInput++] = newTodo;

                        System.out.println("Got it. I've added this task:");
                        System.out.println("  " + newTodo);
                        System.out.println("Now you have " + nextInput + " tasks in the list");
                        div.print();
                    } else if (input.startsWith("deadline")) {
                        String[] inputArgs = input.replace("deadline", "").split(" /by ");
                        Deadline newDeadline = new Deadline(inputArgs[0], inputArgs[1]);
                        tasks[nextInput++] = newDeadline;

                        System.out.println("Got it. I've added this task:");
                        System.out.println("  " + newDeadline);
                        System.out.println("Now you have " + nextInput + " tasks in the list");
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
                        Event newEvent = new Event(taskName, from, to);
                        tasks[nextInput++] = newEvent;

                        System.out.println("Got it. I've added this task:");
                        System.out.println("  " + newEvent);
                        System.out.println("Now you have " + nextInput + " tasks in the list");
                        div.print();
                    } else {
                        tasks[nextInput++] = new Task(input);
                        System.out.println("added: " + input);
                        div.print();
                    }
                    break;
            }
        }
        inputScanner.close();

        System.out.println("Bye. Hope to see you again soon!");
        div.print();
    }
}
