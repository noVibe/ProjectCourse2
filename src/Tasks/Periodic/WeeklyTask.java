package Tasks.Periodic;

import Tasks.Task;
import enums.Period;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

public class WeeklyTask extends Task {
    public WeeklyTask(long id, boolean isPersonal, String header, String description, LocalDateTime date, Period period) {
        super(id, isPersonal, header, description, date, period);
    }

    @Override
    public boolean isActiveAt(LocalDate date) {
        return date.getDayOfWeek().equals(getDate().getDayOfWeek());
    }

    @Override
    protected void refreshDate() {
        while (!getDate().getDayOfWeek().equals(LocalDate.now().getDayOfWeek())) {
            setDateTime(getDateTime()
                    .withYear(LocalDate.now().getYear())
                    .withMonth(LocalDate.now().getMonthValue())
                    .plusDays(1));
        }
    }
    @Override
    public String toString() {
        return getPeriod() + ": " + getDateTime().format(DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT)) +
                " each " + getDate().getDayOfWeek() + "\n" + super.toString();
    }
}

