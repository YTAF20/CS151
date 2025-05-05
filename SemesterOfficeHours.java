package s25.cs151.application;

import java.io.Serializable;

// Class to represent office hours data, implements Serializable for file storage
public class SemesterOfficeHours implements Serializable {
    private String semester;
    private String year;
    private boolean[] daysSelected;

    // Constructor to initialize semester office hours.
    public SemesterOfficeHours(String semester, String year, boolean[] daysSelected) {
        this.semester = semester;
        this.year = year;
        this.daysSelected = daysSelected;
    }

    // Getters and setters
    public String getSemester() { return semester; }
    public String getYear() { return year; }
    public boolean[] getDaysSelected() { return daysSelected; }
}
