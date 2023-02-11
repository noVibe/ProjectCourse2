package console_handling;

import Tasks.Task;
import Tasks.TaskHandler;
import enums.Period;
import exceptions.PastCallException;

import java.io.IOException;
import java.time.*;
import java.util.*;

import static enums.Period.*;

final public class ConsoleHandle {

    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws InterruptedException {
        HashMap<Integer, Period> periods = new HashMap<>();
        periods.put(1, ONCE);
        periods.put(2, DAILY);
        periods.put(3, WEEKLY);
        periods.put(4, MONTHLY);
        periods.put(5, YEARLY);

        /*
         1 - task operations
         add, +
         modify + header + description +
         remove +
         2 - info print +
         daily tasks
         all tasks
         removed tasks
         expired tasks

         */
        LocalDateTime date;
        Task task;
        String header;
        String description;
        int year = -1, month = -1, weekday = -1, day = -1, hour, minute, period, isPersonal, modify;
        long id;

        while (true) {
            LocalDate serviceDate = LocalDate.now();

            int general = validateIntInput("Task operations: 1. Watch info: 2", 1, 2);
            if (general == 1) {

                int taskOperations = validateIntInput("Add: 1. Modify: 2. Remove: 3", 1, 2, 3);

                if (taskOperations == 1) {
                    isPersonal = validateIntInput("Choose status. Personal: 1. Work: 2.", 1, 2);
                    header = validateStringInput("Create the header:");
                    description = validateStringInput("Write a description:");
                    period = validateIntInput("Set the period:\nOnce: 1. Daily: 2. Weekly: 3. Monthly: 4. Yearly: 5", 1, 2, 3, 4, 5);
                    while (true) try {
                        hour = validateRangeIntInput("Set hours: ", 0, 23);
                        minute = validateRangeIntInput("Set minutes: ", 0, 59);
                        if (period == 3) {
                            weekday = validateRangeIntInput("Monday: 1. Tuesday: 2. Wednesday: 3. Thursday: 4. Friday: 5. Saturday: 6, Sunday: 7.\nSet the day of week: ", 1, 7);
                            while (!serviceDate.getDayOfWeek().equals(DayOfWeek.of(weekday))) serviceDate = serviceDate.plusDays(1);
                            day = serviceDate.getDayOfMonth();
                        } else {
                            if (period == 4 || period == 1)
                                day = validateRangeIntInput("Set the day: ", 1, LocalDateTime.MAX.getDayOfMonth());
                            if (period == 5 || period == 1)
                                month = validateRangeIntInput("Set the month: ", 1, 12);
                            if (period == 1)
                                year = validateRangeIntInput("Set the year: ", 0, LocalDate.MAX.getYear());
                        }
                        TaskHandler.addNewTaskInstance(header, description, year, weekday == -1 ? month : serviceDate.getMonthValue(), day, hour, minute, periods.get(period), isPersonal == 1);
                        System.out.println("( +++++ Added successfully! +++++ )");
                        break;
                    } catch (PastCallException | DateTimeException e) {
                        System.err.println(e.getMessage());
                        Thread.sleep(100);
                    }

                } else if (taskOperations == 2) {
                    id = validateLongInput("Put id of task to modify: ", TaskHandler.getIdList());
                    task = TaskHandler.findByID(id);
                    System.out.printf("Chosen task: %s", task);
                    modify = validateIntInput("Modify Header: 1. Description: 2. ", 1, 2);
                    if (modify == 1) {
                        header = validateStringInput("New Header:");
                        task.setHeader(header);
                    }
                    if (modify == 2) {
                        description = ("New description:");
                        task.setDescription(description);
                    }
                    System.out.println("( ~~~~~ Modified successfully! ~~~~~ )");

                } else if (taskOperations == 3) {
                    id = validateLongInput("Put id of task to remove: ", TaskHandler.getIdList());
                    TaskHandler.removeByID(id);
                    System.out.println("( ----- Removed successfully! ----- )");
                }
            }
            if (general == 2) {
                int info = validateIntInput("Daily tasks: 1. Active tasks: 2. Expired tasks: 3. Removed tasks: 4", 1, 2, 3, 4);
                if (info == 1) TaskHandler.printDailyTasks();
                if (info == 2) TaskHandler.printAllTasks();
                if (info == 3) TaskHandler.printExpiredTasks();
                if (info == 4) TaskHandler.printRemovedTasks();
            }
        }
    }

    private static long validateLongInput(String message, Long... args) {
        System.out.println(message);
        while (true) {
            try {
                long n;
                String s = scanner.nextLine();
                if (s.matches("\\d+")) n = Long.parseLong(s);
                else throw new IOException();
                for (long arg : args) {
                    if (arg == n) return arg;
                }
                throw new IOException();
            } catch (IOException | InputMismatchException e) {
                System.err.println("Incorrect input!\n" + message);
            }
        }
    }

    private static int validateIntInput(String message, int... args) {
        System.out.println(message);
        while (true) {
            try {
                int n;
                String s = scanner.nextLine();
                if (s.matches("\\d+")) n = Integer.parseInt(s);
                else throw new IOException();
                for (int arg : args) {
                    if (arg == n) return arg;
                }
                throw new IOException();
            } catch (IOException | InputMismatchException e) {
                System.err.println("Incorrect input!\n" + message);
            }
        }
    }

    private static int validateRangeIntInput(String message, int min, int max) {
        System.out.print(message);
        while (true) {
            try {
                int n;
                String s = scanner.nextLine();
                if (s.matches("\\d+")) n = Integer.parseInt(s);
                else throw new IOException();
                if (n < min || n > max) throw new IOException();
                else return n;
            } catch (IOException | InputMismatchException e) {
                System.err.printf("Incorrect input! Allowed range: from %s to %s\n%s", min, max, message);
            }
        }
    }

    private static String validateStringInput(String message) {
        System.out.println(message);
        while (true) {
            try {
                String temp = scanner.nextLine();
                if (temp.isBlank()) throw new IOException();
                return temp;
            } catch (IOException e) {
                System.err.println("Empty input!\n" + message);
            }
        }
    }
}
