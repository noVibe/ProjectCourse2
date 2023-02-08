package Tasks;

import enums.Period;
import exceptions.PastCall;

import java.util.*;

import static enums.Period.*;

public class Task implements Comparable {
    private static long count = 0;
    final private static List<Task> finishedTasks = new LinkedList<>();
    final private long id;
    final private boolean isPersonal;
    private String header;
    private String description;
    private GregorianCalendar date;
    private Period period;


    public Task(String header, String description, int year, int month, int day, int hrs, int mins, Period period, boolean isPersonal) {
        this.id = count++;
        this.header = header;
        this.description = description;
        this.period = period;
        GregorianCalendar temp = new GregorianCalendar(year, month - 1, day, hrs, mins);
        if (new Date().after(temp.getTime())) throw new PastCall();
        this.date = temp;
        this.isPersonal = isPersonal;
    }

    public void setNextTime() {
        if (period.equals(ONCE)) finishedTasks.add(this);
        if (period.equals(DAILY)) date.add(Calendar.DAY_OF_WEEK, 1);
        if (period.equals(WEEKLY)) date.add(Calendar.WEEK_OF_MONTH, 1);
        if (period.equals(MONTHLY)) date.add(Calendar.MONTH, 1);
        if (period.equals(YEARLY)) date.add(Calendar.YEAR, 1);
    }

    @Override
    public int compareTo(Object o) {
        if (date.before(((Task) o).getDate())) return -1;
        else if (date.after(((Task) o).getDate())) return 1;
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

    public String getTaskData() {
        return (isPersonal ? "Personal" : "Work") + " Task: " + header + "\nDescription: " + description +
                ".\nTime and date: " + date.getTime() + ".\nPeriod: " + period + "\nid: " + id;
    }

    public GregorianCalendar getDate() {
        return date;
    }

    public long getId() {
        return id;
    }

    public Period getPeriod() {
        return period;
    }


}
