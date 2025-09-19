# Cue User Guide

Cue is a desktop app for organizing tasks, optimized for use via a Comand Line Interface (CLI) while still retaining a Graphical User Interface (GUI). If you can type fast, Cue can track your tasks faster than traditional GUI apps.

![A screenshot of the Cue application](/docs/Ui.png)

## Quick Start

  1. Ensure you have Java 17 or above installed in your Computer.
Mac users: Ensure you have the precise JDK version prescribed [here](https://se-education.org/guides/tutorials/javaInstallationMac.html).

  2. Download the latest .jar file from [here](https://github.com/orbitalsqwib/ip/releases).

  3. Move the file to a location of your choice

  4. Open a command terminal, `cd` into the folder you put the jar file in, and use the `java -jar cue.jar` command to run the application.

  5. Type the command in the command box and press Enter to execute it. e.g. typing help and pressing Enter will open the help window.
Some example commands you can try:
     - `list` : Lists all tasks.
     - `deadline essay assignment /by 2025-9-26@2359` : Adds a deadline named "essay assignment" due on 26 September 2025 at 2359 hours to the task list.
     - `delete 2` : Deletes the 2nd task in the entire task list.
     - `mark 2` : Marks the 2nd task in the entire task list as done.
     - `bye` : Exits the app.
  6. Refer to the [Features](#features) below for details of each command.

## Features

Notes about the command format:

- Words in UPPER_CASE are the parameters to be supplied by the user.
e.g. in `todo TASK_NAME`, `TASK_NAME` is a parameter which can be used as `todo essay assignment`.

- Items in square brackets are optional. e.g `deadline TASK_NAME /by DATE\[@TIME]` can be used as `deadline essay assignment /by 2025-9-26@2359` or as `deadline essay assignment /by 2025-9-26`.

- Extraneous parameters for commands that do not take in parameters (such as `list` or `reminder`) will be ignored. e.g. if the command specifies `list 123`, it will be interpreted as `list`.

### Listing tasks

Shows a list of all tasks in the task list.

Format: `list`

### Adding tasks

Creates a new task and adds it to the task list.

Format:
- Todos: `todo TASK_NAME`
- Deadlines: `deadline TASK_NAME /by DATE\[@TIME]`
- Events: `event TASK_NAME /from DATE\[@TIME] /to DATE\[@TIME]`

Examples:
- `deadline essay assignment /by 2025-9-26@2359`
- `event japan trip /from 2025-10-2 /to 2025-11-2`

### Marking tasks as done / not done

Marks a task as done / not done. The INDEX refers to the index number shown in the displayed tasklist. The index must be a positive integer 1, 2, 3, …​

Format:
- Mark as done: `mark INDEX`
- Mark as not done: `unmark INDEX`

Examples:
- Running `find assignment` and then `mark 1` will mark the first task in the overall task list as **done**, which may **not** be the first task in the `find assignment` results.
- Running `list` and then `unmark 2` will mark the second item in the tasklist as **not done**.

### Deleting tasks

Removes a task from the task list. The INDEX refers to the index number shown in the displayed tasklist. The index must be a positive integer 1, 2, 3, …​

Format: `delete INDEX`

Examples:
- Running `find assignment` and then `delete 1` will delete the first task in the overall task list, and not the first task in the `find assignment` results.
- Running `list` and then `delete 2` will delete the second item in the tasklist.

### Finding tasks by name

Lists all tasks where name contains SEARCH_STRING.

Format: `find SEARCH_STRING`

Example:
- `find assignment` returns a list of tasks such as
  - \[T][ ] essay assignment
  - \[D][ ] revise assignment operators (by: 3 Sep 2025)

### View all tasks for a given day

Lists all tasks that are active on DATE, such as deadlines yet to elapse, or events occuring on that day.

Format: `summary DATE`

Example:
- `summary 2025-9-2` returns a list of tasks such as
  - \[D][ ] revise assignment operators (by: 3 Sep 2025)
  - \[E][ ] attend concert (from: 2 Sep 2025 @ 05:00pm to: 2 Sep 2025 @ 09:00pm)

### View all time-sensitive tasks

Lists all tasks that are time-sensitive and are set to valid times.

Format: `reminder`

Example:
- `reminder` returns a list of tasks such as
  - \[D][ ] revise assignment operators (by: 3 Sep 2025)
- but not
  - \[D][ ] get milk (by: invaliddate)

### Exiting the application

Quits the application.

Format: `bye`

### Saving the data

All tasklist data is automatically saved after every command to the `~/.cue_data` directory. There is no need to save manually.