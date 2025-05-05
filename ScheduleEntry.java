package s25.cs151.application;

import java.time.LocalDate;
import java.io.Serializable;

public abstract class ScheduleEntry implements Serializable {
    private String studentName;
    private LocalDate scheduleDate;
    private String timeSlot;
    private String course;
    private String reason;
    private String comment;

    public ScheduleEntry(String studentName, LocalDate scheduleDate,
                         String timeSlot, String course,
                         String reason, String comment) {
        this.studentName = studentName;
        this.scheduleDate = scheduleDate;
        this.timeSlot = timeSlot;
        this.course = course;
        this.reason = reason;
        this.comment = comment;
    }

    // Getters
    public String getStudentName() { return studentName; }
    public LocalDate getScheduleDate() { return scheduleDate; }
    public String getTimeSlot() { return timeSlot; }
    public String getCourse() { return course; }
    public String getReason() { return reason; }
    public String getComment() { return comment; }

    // Setters for editing
    public void setStudentName(String studentName) { this.studentName = studentName; }
    public void setScheduleDate(LocalDate scheduleDate) { this.scheduleDate = scheduleDate; }
    public void setTimeSlot(String timeSlot) { this.timeSlot = timeSlot; }
    public void setCourse(String course) { this.course = course; }
    public void setReason(String reason) { this.reason = reason; }
    public void setComment(String comment) { this.comment = comment; }
}