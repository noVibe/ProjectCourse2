package Tasks;

import enums.Period;
import exceptions.PastCall;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

final public class Task implements Comparable {
    private static long count = 0;
    final private static List<Task> finishedTasks = new LinkedList<>();
    final private long id;
    final private boolean isPersonal;
    private String header;
    private String description;
    final private LocalDateTime date;
    private Period period;


    public Task(String header, String description, int year, int month, int day, int hrs, int mins, Period period, boolean isPersonal) {
        this.id = count++;
        this.header = header;
        this.description = description;
        this.period = period;
        LocalDateTime temp = LocalDateTime.of(year, month, day, hrs, mins);
        if (LocalDateTime.now().isAfter(temp)) throw new PastCall();
        this.date = temp;
        this.isPersonal = isPersonal;
    }

    @Override
    public int compareTo(Object o) {
        if (date.isBefore(((Task) o).getDateTime())) return -1;
        else if (date.isAfter(((Task) o).getDateTime())) return 1;
        else return 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Task)) return false;
        Task task = (Task) o;
        return id == task.id && Objects.equals(header, task.header) && Objects.equals(description, task.description) && Objects.equals(date, task.date) && period == task.period && isPersonal == isPersonal;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {

        return (isPersonal ? "Personal" : "Work") + " Task: " + header + "\nDescription: " + description +
                ".\nPeriod: " + period + "\nid: " + id;
    }

    public String getFullData() {
        return (isPersonal ? "Personal" : "Work") + " Task: " + header + "\nDescription: " + description +
                ".\nTime and date: " + date + ".\nPeriod: " + period + "\nid: " + id;
    }
    public String getTaskAndTime() {
        return (isPersonal ? "Personal" : "Work") + " Task: " + header + "\nDescription: " + description +
                ".\nTime: " + date.toLocalTime() + ".\nPeriod: " + period + "\nid: " + id;
    }

    public LocalDate getDate() {
        return date.toLocalDate();
    }
    public LocalTime getTime() {
        return date.toLocalTime();
    }
    public LocalDateTime getDateTime() {
        return date;
    }

    public long getId() {
        return id;
    }

    public Period getPeriod() {
        return period;
    }


}
