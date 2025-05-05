package s25.cs151.application;

import java.io.Serializable;

public class Course implements Serializable {
    private String courseCode;
    private String courseName;
    private String sectionNumber;

    public Course(String courseCode, String courseName, String sectionNumber) {
        this.courseCode = courseCode;
        this.courseName = courseName;
        this.sectionNumber = sectionNumber;
    }

    // Getters
    public String getCourseCode() { return courseCode; }
    public String getCourseName() { return courseName; }
    public String getSectionNumber() { return sectionNumber; }

    @Override
    public String toString() {
        return String.format("%s - %s (Section %s)", courseCode, courseName, sectionNumber);
    }
}
