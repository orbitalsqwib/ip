import java.util.Scanner;

import divider.*;
import task.*;

public class Cue {
    public static void main(String[] args) {
        Divider div = new Divider(80);
        String logo = "\n  ▄▄▄x  ▄▄▄ x▄▄    ▄▄▄o  \n"
                + "o█   ▀▀  ██  ██  ▄█▄▄x██ \n"
                + "██       ██  ██  ██      \n"
                + " ▀█x▄▄▀  ▀█o▄▀█x  ▀█x▄▄▀ \n";

        div.print();
        System.out.println("Hello, I'm\n" + logo + "\nWhat can I do for you?\n");
        div.print();

        Task[] tasks = new Task[100];
        int nextInput = 0;

        Scanner inputScanner = new Scanner(System.in);
        boolean isExit = false;
        while (!isExit) {
            System.out.print("me > ");
            String input = inputScanner.nextLine().strip();
            System.out.println();
            div.print();

            if (input.equals("bye")) {
                isExit = true;
            } else if (input.equals("list")) {
                for (int i = 0; i < nextInput; i++) {
                    System.out.println(" " + (i+1) + ". " + tasks[i] + "\n");
                }
                div.print();
            } else if (input.startsWith("mark") || input.startsWith("unmark")) {
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

                System.out.println("  " + targetTask + "\n");
                div.print();
            } else {
                tasks[nextInput++] = new Task(input);
                System.out.println("added: " + input + "\n");
                div.print();
            }
        }
        inputScanner.close();

        System.out.println("Bye. Hope to see you again soon!\n");
        div.print();
    }
}
