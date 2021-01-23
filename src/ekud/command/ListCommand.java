package ekud.command;

import ekud.common.exception.DukeException;
import ekud.storage.Storage;
import ekud.task.Task;
import ekud.task.TaskList;
import ekud.task.TaskWithDateTime;
import ekud.ui.Ui;

import java.time.LocalDate;
import java.util.Vector;

public class ListCommand extends Command {
    private final boolean filter;
    private LocalDate date;

    public ListCommand(LocalDate date) {
        this.filter = true;
        this.date = date;
    }

    public ListCommand() {
        this.filter = false;
    }

    /**
     * Output all tasks if no date specified, tasks due on the day otherwise.
     *
     * @param tasks   the list of tasks
     * @param ui      the user interface
     * @param storage the file writer
     */
    @Override
    public void execute(final TaskList tasks, Ui ui, Storage storage) throws DukeException {
        // list command with no arguments
        if (!filter) {
            if (tasks.isEmpty()) {
                ui.printLines("I know nothing, feed me with something :)");
                return;
            }

            ui.printLines("Get working! You need finish these things:");
            for (int i = 0; i < tasks.size(); i++) {
                ui.printLines(String.format("\t %d. %s", i + 1, tasks.get(i).toString()));
            }
            return;
        }

        // list tasks due on a particular day
        Vector<TaskWithDateTime> toPrint = new Vector<>();
        for (Task task : tasks) {
            if (task instanceof TaskWithDateTime) {
                TaskWithDateTime t = (TaskWithDateTime) task;
                if (t.getDateTime().toLocalDate().equals(date))
                    toPrint.add(t);
            }
        }
        if (toPrint.isEmpty()) {
            ui.printLines(("You're free for the day!"));
        } else {
            ui.printLines("You have these deadlines/events:");
            for (int i = 0; i < toPrint.size(); i++) {
                ui.printLines(String.format("\t %d. %s", i + 1, toPrint.get(i).toString()));
            }
        }
    }
}
