package s25.cs151.application.model;

import java.io.Serializable;
import java.time.LocalDate;

public class OfficeHoursSchedule extends ScheduleEntry {
    public OfficeHoursSchedule(String studentName, LocalDate scheduleDate,
                               String timeSlot, String course,
                               String reason, String comment) {
        super(studentName, scheduleDate, timeSlot, course, reason, comment);
    }
}