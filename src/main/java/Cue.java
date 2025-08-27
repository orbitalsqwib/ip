import cue.storage.TaskStorage;
import cue.tasks.TaskList;
import cue.ui.CommandLineInterface;

public class Cue {
    private CommandLineInterface cli;
    private TaskList taskList;

    private Cue() {
        taskList = new TaskList(TaskStorage.loadFromDisk());
        cli = new CommandLineInterface(80, taskList);
    }

    private void run() {
        cli.start();
    }

    public static void main(String[] args) {
        new Cue().run();
    }
}
