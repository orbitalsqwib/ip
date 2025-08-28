package cue.command.commands;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import cue.command.CommandContext;
import cue.parser.CommandParser;
import cue.tasks.TaskList;
import cue.tasks.Todo;
import cue.ui.CommandLineInterface;

public class DeleteCommandTest {
    @Test
    public void deleteTaskCommand_execute_success() {
        TaskList taskList = new TaskList();
        CommandContext commandContext = new CommandContext(taskList, new CommandLineInterface(80), null);

        taskList.addTask(new Todo("test"));

        assertDoesNotThrow(() -> {
            CommandParser.Result input = CommandParser.parse("delete 1");
            new DeleteCommand().execute(commandContext, input);
        });

        assertEquals(0, taskList.getSize());
    }

    @Test
    public void deleteTaskCommand_executeDeleteOutOfRange_exceptionThrown() {
        TaskList taskList = new TaskList();
        CommandContext commandContext = new CommandContext(taskList, new CommandLineInterface(80), null);

        assertThrows(IndexOutOfBoundsException.class, () -> {
            CommandParser.Result input = CommandParser.parse("delete 1");
            new DeleteCommand().execute(commandContext, input);
        });
    }
}
