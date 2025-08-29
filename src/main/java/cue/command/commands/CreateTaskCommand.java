package cue.command.commands;

import cue.command.Command;
import cue.command.CommandContext;
import cue.errors.CueException;
import cue.errors.UnknownCommandException;
import cue.parser.CommandParser;
import cue.tasks.Deadline;
import cue.tasks.Event;
import cue.tasks.Task;
import cue.tasks.Todo;

/**
 * Creates a new Task and adds it to the current CommandContext's TaskList.
 */
public class CreateTaskCommand implements Command {

    @Override
    public void execute(CommandContext context, CommandParser.Result input) throws CueException {
        Task newTask = switch (input.getKeyword()) {
            case "todo" -> new Todo(input.getBody());
            case "deadline" -> new Deadline(input.getBody(), input.getTag("by"));
            case "event" -> new Event(input.getBody(), input.getTag("from"), input.getTag("to"));
            default -> throw new UnknownCommandException();
        };

        if (newTask != null) {
            context.getTaskList().addTask(newTask);

            context.getCli().print("Got it. I've added this task:");
            context.getCli().printIndented(newTask.toString());
            context.getCli().print("Now you have " + context.getTaskList().getSize() + " tasks in the list");
        }
    }
}
