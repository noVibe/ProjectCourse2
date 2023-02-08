package Tasks;

import enums.Period;

import java.time.LocalDate;
import java.util.*;

import static enums.Period.*;

final public class TaskHandler {
    final private static Set<Task> tasks = new TreeSet<>();
    final private static List<Task> expired = new LinkedList<>();
    final private static List<Task> removed = new LinkedList<>();

    public static void addNewTaskInstance(String header, String description, int year, int month, int day, int hrs, int mins, Period period, boolean isPersonal) {
        tasks.add(new Task(header, description, year, month, day, hrs, mins, period, isPersonal));
    }

    public static void printAllTasks() {
        tasks.forEach((v) -> System.out.println("==========\n" + v));
    }

    public static void printDailyTasks() {
        LocalDate nextDay = LocalDate.now().plusDays(1);
        refresh(LocalDate.now());
        for (Task task : tasks) {
            if (task.getDate().isBefore(nextDay)) {
                System.out.println(task);
            }
        }
    }

    private static void refresh(LocalDate date) {
        var iterator = tasks.iterator();
        while (iterator.hasNext()) {
            var task = iterator.next();
            if (task.getPeriod().equals(ONCE) && task.getDate().isBefore(date)) {
                expired.add(0, task);
                iterator.remove();
                continue;
            }
            while (!task.getPeriod().equals(ONCE) && task.getDate().isBefore(date)) {
            }
        }
    }


    public static void removeByID(long id) {
        var iterator = tasks.iterator();
        while (iterator.hasNext()) {
            var temp = iterator.next();
            if (temp.getId() == id) {
                removed.add(0, temp);
                iterator.remove();
                break;
            }
        }
    }

    public static void printRemovedTasks() {
        System.out.println("Removed tasks list:");
        removed.forEach(t -> System.out.println(t));
    }

    public static void printExpiredTasks() {
        expired.forEach(t -> System.out.println(t));
    }

    public static void printTasksOnSpecificDate(int year, int month, int day) {
        Comparator<Task> timeComparator = (o1, o2) -> {
            if (o1.getTime().isBefore(o2.getTime())) return 1;
            else if (o1.getTime().isAfter(o2.getTime())) return -1;
            else return 0;
        };
        Set<Task> temp = new TreeSet<>(timeComparator);
        LocalDate date = LocalDate.of(year, month, day);
        for (Task task : tasks) {
            if (task.getPeriod().equals(ONCE) && task.getDate().equals(date) ||
                    task.getPeriod().equals(DAILY) ||
                    task.getPeriod().equals(WEEKLY) && task.getDate().getDayOfWeek().equals(date.getDayOfWeek()) ||
                    task.getPeriod().equals(MONTHLY) && task.getDate().getDayOfMonth() == date.getDayOfMonth() ||
                    task.getPeriod().equals(YEARLY) && task.getDate().getDayOfYear() == date.getDayOfYear())
                temp.add(task);
        }
    }
}


