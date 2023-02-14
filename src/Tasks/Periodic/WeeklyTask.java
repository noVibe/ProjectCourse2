package Tasks.Periodic;

import Tasks.Task;
import enums.Period;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import static java.time.temporal.ChronoField.*;

public class WeeklyTask extends Task {
    public WeeklyTask(long id, boolean isPersonal, String header, String description, LocalDateTime date, Period period) {
        super(id, isPersonal, header, description, date, period);
    }

    @Override
    public boolean isActiveAt(LocalDate date) {
        return date.getDayOfWeek().equals(this.getDate().getDayOfWeek());
    }

    @Override
    protected void refreshDate() {
        LocalTime time = this.getDateTime().toLocalTime();
        LocalDateTime newDate = LocalDateTime.now().with(MINUTE_OF_HOUR, time.getMinute()).with(HOUR_OF_DAY, time.getHour());
        while (!newDate.getDayOfWeek().equals(this.getDate().getDayOfWeek())) newDate = newDate.plusDays(1);
        this.setDate(newDate);
    }
    @Override
    public String toString() {
        return getPeriod() + ": " + getDateTime().format(DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT)) +
                " each " + getDate().getDayOfWeek() + "\n" + super.toString();
    }
}

