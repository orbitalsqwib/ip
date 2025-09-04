package cue.command.commands;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import cue.command.CommandContext;
import cue.gui.CommandableInterface;
import cue.parser.CommandParser;
import cue.parser.errors.MissingCommandTagException;
import cue.tasks.Deadline;
import cue.tasks.Event;
import cue.tasks.Task;
import cue.tasks.TaskList;
import cue.tasks.Todo;

public class CreateTaskCommandTest {

    private CommandContext getMockCommandContext(TaskList taskList) {
        return new CommandContext(taskList, new CommandableInterface() {
            @Override
            public void display(String output) {
            }
        }, null);
    }

    @Test
    public void createTaskCommand_executeCreateTodo_success() {
        TaskList taskList = new TaskList();
        CommandContext commandContext = getMockCommandContext(taskList);

        assertDoesNotThrow(() -> {
            CommandParser.Result input = CommandParser.parse("todo write tests");
            new CreateTaskCommand().execute(commandContext, input);
        });

        assertEquals(1, taskList.getSize());

        Task model = new Todo("write tests");
        Task created = taskList.getTask(0);

        assertEquals(model.toString(), created.toString());
        assertEquals(model.isDone(), created.isDone());
    }

    @Test
    public void createTaskCommand_executeCreateDeadline_success() {
        TaskList taskList = new TaskList();
        CommandContext commandContext = getMockCommandContext(taskList);

        assertDoesNotThrow(() -> {
            CommandParser.Result input = CommandParser.parse("deadline write tests /by today");
            new CreateTaskCommand().execute(commandContext, input);
        });

        assertEquals(1, taskList.getSize());

        Task model = new Deadline("write tests", "today");
        Task created = taskList.getTask(0);

        assertEquals(model.toString(), created.toString());
        assertEquals(model.isDone(), created.isDone());
    }

    @Test
    public void createTaskCommand_executeCreateDeadlineMissingTag_exceptionThrown() {
        TaskList taskList = new TaskList();
        CommandContext commandContext = getMockCommandContext(taskList);

        assertThrows(MissingCommandTagException.class, () -> {
            CommandParser.Result input = CommandParser.parse("deadline write tests");
            new CreateTaskCommand().execute(commandContext, input);
        });
    }

    @Test
    public void createTaskCommand_executeCreateEvent_success() {
        TaskList taskList = new TaskList();
        CommandContext commandContext = getMockCommandContext(taskList);

        assertDoesNotThrow(() -> {
            CommandParser.Result input = CommandParser.parse("event write tests /from today /to tomorrow");
            new CreateTaskCommand().execute(commandContext, input);
        });

        assertEquals(1, taskList.getSize());

        Task model = new Event("write tests", "today", "tomorrow");
        Task created = taskList.getTask(0);

        assertEquals(model.toString(), created.toString());
        assertEquals(model.isDone(), created.isDone());
    }

    @Test
    public void createTaskCommand_executeCreateEventMissingTag_exceptionThrown() {
        TaskList taskList = new TaskList();
        CommandContext commandContext = getMockCommandContext(taskList);

        assertThrows(MissingCommandTagException.class, () -> {
            CommandParser.Result input = CommandParser.parse("deadline write tests");
            new CreateTaskCommand().execute(commandContext, input);
        });

        assertThrows(MissingCommandTagException.class, () -> {
            CommandParser.Result input = CommandParser.parse("event write tests /from today");
            new CreateTaskCommand().execute(commandContext, input);
        });

        assertThrows(MissingCommandTagException.class, () -> {
            CommandParser.Result input = CommandParser.parse("deadline write tests /to tomorrow");
            new CreateTaskCommand().execute(commandContext, input);
        });
    }
}
