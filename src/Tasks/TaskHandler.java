package Tasks;

import enums.Period;
import exceptions.PastCallException;

import java.time.LocalDate;
import java.util.*;

import static enums.Period.*;

final public class TaskHandler {
    final private static Set<Task> tasks = new TreeSet<>();
    final private static List<Task> expired = new LinkedList<>();
    final private static Set<Task> removed = new LinkedHashSet<>();

    public static void addNewTaskInstance(String header, String description, int year, int month, int day, int hrs, int mins, Period period, boolean isPersonal) throws PastCallException {
        tasks.add(new Task(header, description,
                year == -1 ? LocalDate.now().getYear() : year,
                month == -1 ? LocalDate.now().getMonthValue() : month,
                day == -1 ? LocalDate.now().getDayOfMonth() : day,
                hrs, mins, period, isPersonal));
    }

    public static void printAllTasks() {
        for (Task task : tasks) {
            System.out.println("============Active=Task============");
            if (task.isEqualPeriod(ONCE)) System.out.println(task.getFullData());
            else if (task.isEqualPeriod(DAILY)) System.out.println(task.getTaskAndTime());
            else if (task.isEqualPeriod(WEEKLY))
                System.out.println(task + "\n" + task.getTime() + "\n" + task.getDate().getDayOfWeek());
            else if (task.isEqualPeriod(MONTHLY))
                System.out.println(task + "\n" + task.getTime() + "\n" + task.getDate().getDayOfMonth());
            else if (task.isEqualPeriod(YEARLY))
                System.out.println(task + "\n" + task.getTime() + "\n" + task.getDate().getMonth() + " " + task.getDate().getDayOfMonth());
        }
    }

    public static void printDailyTasks() {
        LocalDate date = LocalDate.now();
        refresh(date);
        printTasksOnSpecificDate(date.getYear(), date.getMonthValue(), date.getDayOfMonth());
    }

    private static void refresh(LocalDate date) {
        var iterator = tasks.iterator();
        while (iterator.hasNext()) {
            var task = iterator.next();
            if (task.isEqualPeriod(ONCE) && task.getDate().isBefore(date)) {
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
        removed.forEach(t -> System.out.println("============Removed=Task============\n" + t.getFullData()));
    }

    public static void printExpiredTasks() {
        refresh(LocalDate.now());
        System.out.println("Removed tasks list:");
        expired.forEach(t -> System.out.println("============Expired=Task============\n" + t.getFullData()));
    }

    public static void printTasksOnSpecificDate(int year, int month, int day) {
        Set<Task> temp = new TreeSet<>(Comparator.comparingInt(o -> o.getTime().toSecondOfDay()));
        LocalDate date = LocalDate.of(year, month, day);
        for (Task task : tasks) {
            if (task.getPeriod().equals(ONCE) && task.getDate().equals(date) ||
                    task.getPeriod().equals(DAILY) ||
                    task.getPeriod().equals(WEEKLY) && task.getDate().getDayOfWeek().equals(date.getDayOfWeek()) ||
                    task.getPeriod().equals(MONTHLY) && task.getDate().getDayOfMonth() == date.getDayOfMonth() ||
                    task.getPeriod().equals(YEARLY) && task.getDate().getMonth().equals(date.getMonth()) && task.getDate().getDayOfMonth() == date.getDayOfMonth())
                temp.add(task);
        }
        temp.forEach(t -> System.out.println("============Chosen=Date============\n" + t.getTaskAndTime()));
    }

    public static Long[] getIdList() {
        refresh(LocalDate.now());
        List<Long> t = new ArrayList<>();
        tasks.forEach(n -> t.add(n.getId()));
        return t.toArray(Long[]::new);
    }
}


