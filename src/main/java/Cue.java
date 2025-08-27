

import cue.command.CommandContext;
import cue.command.CommandRouter;
import cue.command.commands.CreateTaskCommand;
import cue.command.commands.DeleteCommand;
import cue.command.commands.ExitCommand;
import cue.command.commands.ListCommand;
import cue.command.commands.MarkCommand;
import cue.command.commands.SummaryCommand;

import cue.errors.CueException;
import cue.errors.KeywordCollisionException;

import cue.parser.CommandParser;
import cue.storage.TaskStorage;
import cue.tasks.TaskList;
import cue.ui.CommandLineInterface;

public class Cue {
    private CommandLineInterface cli;
    private TaskList taskList;
    private CommandRouter commandRouter;

    private boolean isRunning;
    private final Runnable stopCue = new Runnable() {
                @Override
                public void run() {
                    isRunning = false;
                }
            };

    private Cue() {
        taskList = new TaskList(TaskStorage.loadFromDisk());
        cli = new CommandLineInterface(80);

         // register commands
        this.commandRouter = new CommandRouter();
        try {
            commandRouter.register(new ExitCommand(), new String[]{ "bye" });
            commandRouter.register(new ListCommand(), new String[]{ "list" });
            commandRouter.register(new MarkCommand(), new String[]{ "mark", "unmark" });
            commandRouter.register(new CreateTaskCommand(), new String[]{ "todo", "deadline", "event" });
            commandRouter.register(new DeleteCommand(), new String[]{ "delete" });
            commandRouter.register(new SummaryCommand(), new String[]{ "summary" });
        } catch (KeywordCollisionException error) {
            // this should not happen, except during development. in this case, throw an exception to warn the dev.
            throw new RuntimeException(error);
        }
    }

    private void handleInput() throws CueException {
        String rawInput = cli.getInput();
        cli.printDivider();

        CommandParser.Result input = CommandParser.parse(rawInput);
        commandRouter
            .route(input.getKeyword())
            .execute(new CommandContext(taskList, cli, stopCue), input);
    }

    private void run() {
        this.isRunning = true;
        cli.greet();

        while (this.isRunning) {
            try {
                handleInput();
                TaskStorage.saveToDisk(taskList.getTasks());
            } catch (CueException error) {
                System.out.println(error.getMessage());
            } finally {
                cli.printDivider();
            }
        }

        cli.cleanup();
    }

    public static void main(String[] args) {
        new Cue().run();
    }
}
