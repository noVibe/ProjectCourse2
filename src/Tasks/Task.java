package Tasks;

import static enums.Period.*;

import enums.Period;
import exceptions.PastCallException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

final public class Task implements Comparable {
    private static long count = 0;
    final private long id;
    final private boolean isPersonal;
    private String header;
    private String description;
    final private LocalDateTime date;
    private Period period;


    public Task(String header, String description, int year, int month, int day, int hrs, int mins, Period period, boolean isPersonal) throws PastCallException {
        this.header = header;
        this.description = description;
        this.period = period;
        LocalDateTime date = LocalDateTime.of(year, month, day, hrs, mins);
        if (date.isBefore(LocalDateTime.now()) && period.equals(ONCE)) throw new PastCallException();
        this.date = date;
        this.isPersonal = isPersonal;
        this.id = count++;
    }

    public Task(boolean isPersonal, String header, String description, LocalDateTime date, Period period) throws PastCallException {
        this.id = count++;
        this.isPersonal = isPersonal;
        this.header = header;
        this.description = description;
        if (date.isBefore(LocalDateTime.now()) && period.equals(ONCE)) throw new PastCallException();
        this.date = date;
        this.period = period;
    }

    @Override
    public int compareTo(Object o) {
        if (date.isAfter(((Task) o).date)) return 1;
        else return -1;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Task)) return false;
        Task task = (Task) o;
        return id == task.id && Objects.equals(header, task.header) && Objects.equals(description, task.description) && Objects.equals(date, task.date) && period == task.period && isPersonal == task.isPersonal;
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

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public boolean isEqualPeriod(Period p) {
        return period.equals(p);
    }
}
