package cue.storage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import cue.errors.MissingSavefileException;
import cue.tasks.Deadline;
import cue.tasks.Event;
import cue.tasks.Task;
import cue.tasks.Todo;

/**
 * Manages the savefile for the Cue agent.
 */
public abstract class TaskStorage {
    private static final Path SAVEFILE_DIR = Paths.get(System.getProperty("user.home"), ".cue_data");
    private static final Path SAVEFILE_PATH = Paths.get(SAVEFILE_DIR.toString(), "savefile.txt");

    /**
     * Loads a list of tasks from the savefile on disk.
     * @return A list of tasks retrieved from the savefile on disk.
     * @throws MissingSavefileException Thrown if no savefile exists on disk at the expected location.
     */
    public static Task[] loadFromDisk() throws MissingSavefileException {
        ArrayList<Task> savedTasks = new ArrayList<>();

        try {
            // if savefile doesn't exist, skip
            if (!Files.exists(SAVEFILE_PATH)) {
                throw new MissingSavefileException();
            }

            // read savefile
            String rawSaveData = Files.readString(SAVEFILE_PATH);

            // parse savefile
            String[] savefileLines = rawSaveData.split("\n");
            for (String line : savefileLines) {
                String[] lineParts = line.strip().split("\\|");

                String[] additionalArgs = null;
                if (lineParts.length < 3) {
                    continue;
                } else if (lineParts.length > 3) {
                    additionalArgs = lineParts[3].split(",");
                }

                Task newTask = null;
                switch (lineParts[0].strip()) {
                case "T":
                    newTask = new Todo(lineParts[2].strip());
                    break;
                case "D":
                    newTask = new Deadline(lineParts[2].strip(), additionalArgs[0].strip());
                    break;
                case "E":
                    newTask = new Event(lineParts[2].strip(), additionalArgs[0].strip(), additionalArgs[1].strip());
                    break;
                default:
                    break;
                }

                // parsing succeeded, add to saved tasks
                if (newTask != null) {
                    newTask.setDone(lineParts[1].strip() == "1");
                    savedTasks.add(newTask);
                }
            }
        } catch (MissingSavefileException | IOException error) {
            return new Task[0];
        }

        return savedTasks.toArray(new Task[savedTasks.size()]);
    }

    /**
     * Saves the specified tasks to the savefile.
     *
     * @param tasks A list of tasks to save
     * @return A boolean indicating the success of the save action.
     */
    public static boolean saveToDisk(Task[] tasks) {
        try {
            // create savefile directory if it doesn't already exist
            if (!Files.exists(SAVEFILE_DIR)) {
                Files.createDirectory(SAVEFILE_DIR);
            }

            // encode tasks
            StringBuilder builder = new StringBuilder();
            for (Task task : tasks) {
                // encode tasks on a best-effort basis
                if (task instanceof Todo) {
                    builder.append("T | ");
                } else if (task instanceof Deadline) {
                    builder.append("D | ");
                } else if (task instanceof Event) {
                    builder.append("E | ");
                } else {
                    continue;
                }

                builder.append(task.encode());
                builder.append('\n');
            }

            // save tasks to file
            Files.write(SAVEFILE_PATH, builder.toString().getBytes());

            return true;
        } catch (IOException err) {
            // handle file output failure
            return false;
        }
    }
}
