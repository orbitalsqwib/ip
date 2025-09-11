package cue;

import java.io.IOException;

import cue.command.CommandContext;
import cue.command.CommandRouter;
import cue.command.commands.CreateTaskCommand;
import cue.command.commands.DeleteCommand;
import cue.command.commands.ExitCommand;
import cue.command.commands.FindCommand;
import cue.command.commands.ListCommand;
import cue.command.commands.MarkCommand;
import cue.command.commands.ReminderCommand;
import cue.command.commands.SummaryCommand;
import cue.errors.CueException;
import cue.errors.KeywordCollisionException;
import cue.errors.MissingSavefileException;
import cue.gui.CommandableInterface;
import cue.gui.components.MainWindow;
import cue.parser.CommandParser;
import cue.storage.TaskStorage;
import cue.tasks.TaskList;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * The main Cue agent.
 */
public class Cue extends Application {
    private CommandableInterface currentUi;
    private TaskList taskList;
    private CommandRouter commandRouter;

    /**
     * Creates a new Cue instance
     */
    public Cue() {
        try {
            taskList = new TaskList(TaskStorage.loadFromDisk());
        } catch (MissingSavefileException error) {
            taskList = new TaskList();
        }

        // register commands
        this.commandRouter = new CommandRouter();
        try {
            commandRouter.register(new ExitCommand(), new String[]{ "bye" });
            commandRouter.register(new ListCommand(), new String[]{ "list" });
            commandRouter.register(new MarkCommand(), new String[]{ "mark", "unmark" });
            commandRouter.register(new CreateTaskCommand(), new String[]{ "todo", "deadline", "event" });
            commandRouter.register(new DeleteCommand(), new String[]{ "delete" });
            commandRouter.register(new SummaryCommand(), new String[]{ "summary" });
            commandRouter.register(new FindCommand(), new String[] { "find" });
            commandRouter.register(new ReminderCommand(), new String[] { "reminder" });
        } catch (KeywordCollisionException error) {
            // this should not happen, except during development. in this case, throw an exception to warn the dev.
            throw new RuntimeException(error);
        }

        // initialize current ui
        this.currentUi = null;
    }

    /**
     * Attempts to parse a given user input to a command, then executes that
     * command.
     *
     * @param input An input, formatted as a command.
     * @throws CueException Thrown if the command is unrecognized or invalid.
     */
    public void respond(String input) throws CueException {
        CommandParser.Result parseResult = CommandParser.parse(input);
        commandRouter
                .route(parseResult.getKeyword())
                .execute(createCommandContext(), parseResult);
        TaskStorage.saveToDisk(taskList.getTasks());
    }

    public void setCurrentUi(CommandableInterface ui) {
        this.currentUi = ui;
    }

    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Cue.class.getResource("/view/MainWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();
            Scene scene = new Scene(ap);
            stage.setScene(scene);
            stage.setTitle("Cue");
            fxmlLoader.<MainWindow>getController().setCue(this);
            setCurrentUi(fxmlLoader.<MainWindow>getController());
            stage.show();

            // remind user on startup, once cue is ready
            try {
                respond("reminder");
            } catch (CueException error) {
                // do nothing
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        Platform.exit();
    }

    private CommandContext createCommandContext() {
        return new CommandContext(taskList, currentUi, this);
    }
}
