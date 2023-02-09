package console_handling;

import Tasks.Task;
import Tasks.TaskHandler;
import enums.Period;
import exceptions.PastCall;

import java.io.IOException;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

import static enums.Period.*;

final public class ConsoleHandle {

    static Scanner scanner = new Scanner(System.in);
    static LocalDateTime date;

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
        Task task;
        String header;
        String description;
        int year, month, day, hour, minute, period, isPersonal, modify;
        long id;

        while (true) {
            Thread.sleep(100);
            int general = validateIntInput("Task operations: 1. Watch info: 2", 1, 2);
            if (general == 1) {

                int taskOperations = validateIntInput("Add: 1. Modify: 2. Remove: 3", 1, 2, 3);

                if (taskOperations == 1) {
                    isPersonal = validateIntInput("Choose status. Personal: 1. Work: any other number.", 1);
                    header = validateStringInput("Create the header:");
                    description = validateStringInput("Write a description:");
                    period = validateIntInput("Set the period:\nOnce: 1. Daily: 2. Weekly: 3. Monthly: 4. Yearly: 5", 1, 2, 3, 4, 5);
                    while (true) try {
                        year = validateRangeIntInput("Set the year: ", LocalDate.now().getYear(), LocalDate.MAX.getYear());
                        month = validateRangeIntInput("Set the month: ", 1, 12);
                        day = validateRangeIntInput("Set the day: ", 1, 31);
                        hour = validateRangeIntInput("Set hours: ", 0, 23);
                        minute = validateRangeIntInput("Set minutes: ", 0, 23);
                        TaskHandler.addNewTaskInstance(header, description, year, month, day, hour, minute, periods.get(period), isPersonal == 1);
                        System.out.println("Added successfully.");
                        break;
                    } catch (PastCall e) {
                        System.err.println(e.getMessage());
                    }

                } else if (taskOperations == 2) {
                    id = validateIntInput("Put id of task to modify: ", TaskHandler.getIdList());
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
                    System.out.println("Modified successfully");

                } else if (taskOperations == 3) {
                    id = validateIntInput("Put id of task to remove: ", TaskHandler.getIdList());
                    TaskHandler.removeByID(id);
                    System.out.println("Removed successfully");
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

    private static <T extends Number> T validateIntInput(String message, T... args) {
        System.out.println(message);
        while (true) {
            try {
                long temp = scanner.nextLong();
                scanner.nextLine();
                for (T arg : args) {
                    if (arg.equals(temp)) return arg;
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
                int temp = scanner.nextInt();
                scanner.nextLine();
                if (temp < min || temp > max) throw new IOException();
                else return temp;
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
