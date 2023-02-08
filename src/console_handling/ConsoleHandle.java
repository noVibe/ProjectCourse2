package console_handling;

import Tasks.TaskHandler;
import enums.Period;
import exceptions.PastCall;

import java.io.IOException;
import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Scanner;

import static enums.Period.*;

final public class ConsoleHandle {
    public static void main(String[] args) throws InterruptedException{
        HashMap<Integer, Period> periods = new HashMap<>();
        periods.put(1, ONCE);
        periods.put(2, DAILY);
        periods.put(3, WEEKLY);
        periods.put(4, MONTHLY);
        periods.put(5, YEARLY);

        /*
         1 - task operations
         add,
         modify - header, description
         remove
         2 - info print
         all tasks
         removed tasks
         expired tasks

         */
        LocalDateTime date;
        String header;
        String description;
        int year, month, day, hour, minute, period, isPersonal;

        Scanner scanner = new Scanner(System.in);

        while (true) try {
            Thread.sleep(100);
            System.out.println("Task operations: 1. Watch info: 2");
            int general = scanner.nextInt();
            if (general == 1) {
                System.out.println("Add: 1. Modify: 2. Remove: 3");
                int taskOper = scanner.nextInt();
                scanner.nextLine();
                if (taskOper == 1) {
                    System.out.println("Choose status. Personal: 1. Work: any other number.");
                    isPersonal = scanner.nextInt();
                    scanner.nextLine();
                    System.out.println("Create the header:");
                    header = scanner.nextLine();
                    System.out.println("Write a description:");
                    description = scanner.nextLine();
                    System.out.println("Set the period:\nOnce: 1. Daily: 2. Weekly: 3. Monthly: 4. Yearly: 5");
                    period = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Set the year: ");
                    year = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Set the month: ");
                    month = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Set the day: ");
                    day = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Set the hour: ");
                    hour = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Set the minute: ");
                    minute = scanner.nextInt();
                    scanner.nextLine();
                    TaskHandler.addNewTaskInstance(header, description, year, month, day, hour, minute, periods.get(period), isPersonal == 1);
                    System.out.println("Task added successfully.");
                    continue;
                }


            }
            if (general == 2) {
                System.out.println("All tasks: 1. Expired tasks: 2. Removed tasks: 3");
                int info = scanner.nextInt();


            } else throw new IOException();

        } catch (IOException | InputMismatchException e) {
            System.err.println("Incorrect input!");
            scanner.nextLine();
        } catch (DateTimeException | PastCall e) {
            System.err.println(e.getMessage());

        }

    }
}
