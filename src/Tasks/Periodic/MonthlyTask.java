package Tasks.Periodic;

import Tasks.Task;
import enums.Period;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

import static java.time.temporal.ChronoField.HOUR_OF_DAY;
import static java.time.temporal.ChronoField.MINUTE_OF_HOUR;

public class MonthlyTask extends Task {
    public MonthlyTask(long id, boolean isPersonal, String header, String description, LocalDateTime date, Period period) {
        super(id, isPersonal, header, description, date, period);
    }

    @Override
    public boolean isActiveAt(LocalDate date) {
        return date.getDayOfMonth() == getDate().getDayOfMonth();
    }

    @Override
    protected void refreshDate() {
        LocalTime time = getDateTime().toLocalTime();
        LocalDateTime newDate = LocalDateTime.now().with(MINUTE_OF_HOUR, time.getMinute()).with(HOUR_OF_DAY, time.getHour());
        if (newDate.getDayOfMonth() > getDate().getDayOfMonth()) newDate = newDate.plusMonths(1);
        setDate(newDate);
    }

    @Override
    public String toString() {
        return getPeriod() + ": day " + getDate().getDayOfMonth() + " at " +
                getDateTime().format(DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT)) + "\n" + super.toString();
    }
}
