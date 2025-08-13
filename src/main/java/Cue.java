import java.util.Scanner;

import divider.*;

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

        String[] inputs = new String[100];
        int nextInput = 0;

        Scanner inputScanner = new Scanner(System.in);
        while (true) {
            System.out.print("me > ");
            String input = inputScanner.nextLine().strip();
            System.out.println();
            div.print();

            if (input.equals("bye")) {
                break;
            } else if (input.equals("list")) {
                for (int i = 0; i < nextInput; i++) {
                    System.out.println(" " + (i+1) + ". " + inputs[i] + "\n");
                }
                div.print();
            } else {
                inputs[nextInput++] = input;
                System.out.println("added: " + input + "\n");
                div.print();
            }
        }
        inputScanner.close();

        System.out.println("Bye. Hope to see you again soon!\n");
        div.print();
    }
}
