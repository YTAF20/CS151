package s25.cs151.application;

import java.io.Serializable;
import java.time.LocalDate;

public class OfficeHoursSchedule implements Serializable {
    private String studentName;
    private LocalDate scheduleDate;
    private String timeSlot;
    private String course;
    private String reason;
    private String comment;

    // Constructor
    public OfficeHoursSchedule(String studentName, LocalDate scheduleDate, 
                             String timeSlot, String course, 
                             String reason, String comment) {
        this.studentName = studentName;
        this.scheduleDate = scheduleDate;
        this.timeSlot = timeSlot;
        this.course = course;
        this.reason = reason;
        this.comment = comment;
    }

    // Getters and setters
    public String getStudentName() { return studentName; }
    public void setStudentName(String studentName) { this.studentName = studentName; }
    
    public LocalDate getScheduleDate() { return scheduleDate; }
    public void setScheduleDate(LocalDate scheduleDate) { this.scheduleDate = scheduleDate; }
    
    public String getTimeSlot() { return timeSlot; }
    public void setTimeSlot(String timeSlot) { this.timeSlot = timeSlot; }
    
    public String getCourse() { return course; }
    public void setCourse(String course) { this.course = course; }
    
    public String getReason() { return reason; }
    public void setReason(String reason) { this.reason = reason; }
    public String getComment() { return comment; }
    public void setComment(String comment) { this.comment = comment; }
} 