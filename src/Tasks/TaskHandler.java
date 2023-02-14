package Tasks;

import java.time.LocalDateTime;

import Tasks.Periodic.*;
import enums.Period;

import java.time.LocalDate;
import java.util.*;

import static enums.Period.*;

final public class TaskHandler {
    final private static Set<Task> tasks = new HashSet<>();
    final private static List<Task> expired = new LinkedList<>();
    final private static Set<Task> removed = new LinkedHashSet<>();
    private static long id = 0;

    public static void addNewTaskInstance(boolean isPersonal, String header, String description, LocalDateTime date, Period period) {
        if (period.equals(ONCE)) tasks.add(new OneTimeTask(id, isPersonal, header, description, date, period));
        else if (period.equals(DAILY)) tasks.add(new DailyTask(id, isPersonal, header, description, date, period));
        else if (period.equals(WEEKLY)) tasks.add(new WeeklyTask(id, isPersonal, header, description, date, period));
        else if (period.equals(MONTHLY)) tasks.add(new MonthlyTask(id, isPersonal, header, description, date, period));
        else if (period.equals(YEARLY)) tasks.add(new YearlyTask(id, isPersonal, header, description, date, period));
        id++;
    }

    public static void printAllTasks() {
        refresh();
        Comparator<Task> comparator = Comparator.comparing(Task::getDateTime);
        tasks.stream().sorted(comparator).forEach(t -> System.out.println("============Active=Task============\n" + t));
    }

    public static void printTodayTasks() {
        refresh();
        printTasksOnSpecificDate(LocalDate.now().getYear(), LocalDate.now().getMonthValue(), LocalDate.now().getDayOfMonth());
    }

    private static void refresh() {
        var iterator = tasks.iterator();
        while (iterator.hasNext()) {
            var task = iterator.next();
            task.refreshDate();
            if (!task.isActual()) {
                expired.add(0, task);
                iterator.remove();
            }
        }
    }

    public static void removeByID(long id) {
        Task t = findByID(id);
        removed.add(t);
        tasks.remove(t);
    }


    public static Task findByID(long id) {
        for (Task task : tasks) {
            if (task.getId() == id) {
                return task;
            }
        }
        return null;
    }


    public static void printRemovedTasks() {
        System.out.println("Removed tasks list:");
        removed.forEach(t -> System.out.println("============Removed=Task============\n" + t));
    }

    public static void printExpiredTasks() {
        refresh();
        System.out.println("Removed tasks list:");
        expired.forEach(t -> System.out.println("============Expired=Task============\n" + t));
    }

    public static void printTasksOnSpecificDate(int year, int month, int day) {
        tasks.stream().filter(t -> t.isActiveAt((LocalDate.of(year, month, day)))).sorted()
                .forEach(t -> System.out.println("============Chosen=Date============\n" + t));
    }

    public static long[] getIdList() {
        refresh();
        return tasks.stream().mapToLong(Task::getId).toArray();
    }
}


