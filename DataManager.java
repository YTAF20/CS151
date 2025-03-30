package s25.cs151.application;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class DataManager {
    private static final String DATA_FILE = "office_hours.dat"; // File to store office hours data
    private static final String TIMESLOTS_FILE = "timeslots.dat";
    private static final String COURSES_FILE = "courses.dat";

    // Save office hours data to file
    public static void saveOfficeHours(SemesterOfficeHours data) {
        List<SemesterOfficeHours> allData = loadAllOfficeHours(); // Retrieve existing data
        allData.add(data); // Add new data to the list
        
        try (ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream(DATA_FILE))) {
            oos.writeObject(allData); //Serialize and write data to file
        } catch (IOException e) {
            e.printStackTrace(); //Print error details if writing fails
        }
    }

    // Load all saved office hours data from the file, if file is not found--empty list returned.
    @SuppressWarnings("unchecked")
    public static List<SemesterOfficeHours> loadAllOfficeHours() {
        try (ObjectInputStream ois = new ObjectInputStream(
                new FileInputStream(DATA_FILE))) {
            return (List<SemesterOfficeHours>) ois.readObject(); // Deserialize and return data
        } catch (FileNotFoundException e) {
            return new ArrayList<>(); // Return an empty list if no file exists
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace(); // Print error details if reading fails
            return new ArrayList<>();
        }
    }

    public static void saveTimeslot(Timeslot timeslot) {
        List<Timeslot> allTimeslots = loadAllTimeslots();
        allTimeslots.add(timeslot);
        
        try (ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream(TIMESLOTS_FILE))) {
            oos.writeObject(allTimeslots);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    public static List<Timeslot> loadAllTimeslots() {
        try (ObjectInputStream ois = new ObjectInputStream(
                new FileInputStream(TIMESLOTS_FILE))) {
            return (List<Timeslot>) ois.readObject();
        } catch (FileNotFoundException e) {
            return new ArrayList<>();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    /**
     * C@onverts a boolean array representing selected days into a formatted string.
     * Example: {true, false, true, false, true} -> "Monday, Wednesday, Friday"
     * 
     * @param daysSelected A boolean array where each index represents a day (Monday-Friday).
     * @return A formatted string listing the selected days.
     */
    private String convertDaysToString(boolean[] daysSelected) {
        String[] dayNames = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday"};
        StringBuilder days = new StringBuilder();
        
        for (int i = 0; i < daysSelected.length; i++) {
            if (daysSelected[i]) {
                if (days.length() > 0) {
                    days.append(", "); // Add a comma seperator if multiple days are selected
                }
                days.append(dayNames[i]); //Append the selected day
            }
        }
        
        return days.toString(); // Return the formatted String
    }

    public static void saveCourse(Course course) {
        List<Course> allCourses = loadAllCourses();
        allCourses.add(course);
        
        try (ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream(COURSES_FILE))) {
            oos.writeObject(allCourses);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    public static List<Course> loadAllCourses() {
        try (ObjectInputStream ois = new ObjectInputStream(
                new FileInputStream(COURSES_FILE))) {
            return (List<Course>) ois.readObject();
        } catch (FileNotFoundException e) {
            return new ArrayList<>();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
} 
