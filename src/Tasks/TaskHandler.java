package Tasks;

import enums.Period;

import java.util.*;

import static enums.Period.DAILY;
import static enums.Period.ONCE;

public class TaskHandler {
    final private static Set<Task> tasks = new TreeSet<>();
    final private static Map<Task, String> archive = new LinkedHashMap<>();
    final private static Set<Task> removed = new LinkedHashSet<>();

    public static void addNewTaskInstance(String header, String description, int year, int month, int day, int hrs, int mins, Period period, boolean isPersonal) {
        tasks.add(new Task(header, description, year, month, day, hrs, mins, period, isPersonal));
    }

    public static void printAllTasks() {
        tasks.forEach((v) -> System.out.println("==========\n" + v));
    }

    public static void printDailyTasks() {
        GregorianCalendar nextDay = getNextDay();
        GregorianCalendar previousDay = getPreviousDay();
        System.out.println(getPreviousDay().getTime());
        System.out.println(getNextDay().getTime());
        refresh(previousDay);
        for (Task task : tasks) {
            if (task.getDate().before(nextDay)) {
                System.out.println(task);
            }
        }
    }

    public static void printTasksOnDate(int day, int month, int year) {
        Set<Task> temp = new TreeSet<>();
        System.out.printf("Tasks for %02d.%02d.%s\n", day, month, year);
        GregorianCalendar specificDay = new GregorianCalendar(year, month, day, Calendar.AM, 0);
        for (Task task : tasks) {
            if (task.getPeriod().equals(DAILY)) temp.add(task);
        }
    }

    private static void moveToArchive(Task task) {
        archive.merge(task, task.getDate().getTime().toString(), (value, x) -> value + ", " + task.getDate().getTime());
    }

    private static void refresh(GregorianCalendar previousDay) {
        var iterator = tasks.iterator();
        while (iterator.hasNext()) {
            var task = iterator.next();
            if (task.getPeriod().equals(ONCE) && task.getDate().before(previousDay)) {
                moveToArchive(task);
                iterator.remove();
                continue;
            }
            while (!task.getPeriod().equals(ONCE) && task.getDate().before(previousDay)) {
                task.setNextTime();
            }
        }
    }


    public static void removeByID(long id) {
        var iterator = tasks.iterator();
        while (iterator.hasNext()) {
            var temp = iterator.next();
            if (temp.getId() == id) {
                removed.add(temp);
                iterator.remove();
                break;
            }
        }
    }

    public static void printRemovedTasks() {
        System.out.println("Removed tasks list:");
        removed.forEach(t -> System.out.println(t));
    }

    public static void printArchivedTasks() {
        archive.forEach((t, str) -> System.out.println(t + "\nWas actual: " + str));
    }

    private static GregorianCalendar getNextDay() {
        GregorianCalendar nextDay = new GregorianCalendar();
        nextDay.set(Calendar.HOUR, Calendar.AM);
        nextDay.set(Calendar.MINUTE, 0);
        nextDay.set(Calendar.SECOND, 0);
        nextDay.roll(Calendar.DAY_OF_WEEK, 1);
        return nextDay;
    }

    private static GregorianCalendar getPreviousDay() {
        GregorianCalendar previousDay = new GregorianCalendar();
        previousDay.set(Calendar.HOUR, Calendar.AM);
        previousDay.set(Calendar.MINUTE, 0);
        previousDay.set(Calendar.SECOND, 0);
        return previousDay;
    }

}

