package cue.command.commands;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import cue.command.CommandContext;
import cue.gui.CommandableInterface;
import cue.parser.CommandParser;
import cue.tasks.TaskList;
import cue.tasks.Todo;

public class DeleteCommandTest {
    private CommandContext getMockCommandContext(TaskList taskList) {
        return new CommandContext(taskList, new CommandableInterface() {
            @Override
            public void display(String output) {
            }
        }, null);
    }

    @Test
    public void deleteTaskCommand_execute_success() {
        TaskList taskList = new TaskList();
        CommandContext commandContext = getMockCommandContext(taskList);

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
        CommandContext commandContext = getMockCommandContext(taskList);

        assertThrows(IndexOutOfBoundsException.class, () -> {
            CommandParser.Result input = CommandParser.parse("delete 1");
            new DeleteCommand().execute(commandContext, input);
        });
    }
}
