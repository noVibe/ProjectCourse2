import Tasks.TaskHandler;
import enums.Period.*;
import exceptions.PastCallException;

import java.util.GregorianCalendar;

import static enums.Period.*;

public class Main {
    public static void main(String[] args) throws PastCallException {
        TaskHandler.addNewTaskInstance("Test", "Testing it", 2023, 10, 15,12, 20, ONCE, true);
        TaskHandler.addNewTaskInstance("Teat2", "Tdsg", 2023, 2, 22, 21, 22, WEEKLY, false);
        TaskHandler.addNewTaskInstance("Teat3", "Tdsg", 2023, 3, 8, 11, 22, DAILY, true);
        TaskHandler.addNewTaskInstance("Teat4", "Tdsg", 2023, 2, 8, 23, 50, DAILY, false);
//        TaskHandler.printDailyTasks();
        System.out.println("=====!!!!====");
        TaskHandler.removeByID(0);
        TaskHandler.removeByID(1);
//        TaskHandler.printAllTasks();
        TaskHandler.printRemovedTasks();
//        TaskHandler.printArchivedTasks()
//
//        TaskHandler.printTasksOnSpecificDate(2024,4,2);
    }
}