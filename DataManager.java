package s25.cs151.application;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class DataManager {
    private static final String DATA_FILE = "office_hours.dat";

    // Save office hours data to file
    public static void saveOfficeHours(SemesterOfficeHours data) {
        List<SemesterOfficeHours> allData = loadAllOfficeHours();
        allData.add(data);
        
        try (ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream(DATA_FILE))) {
            oos.writeObject(allData);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Load all saved office hours data
    @SuppressWarnings("unchecked")
    public static List<SemesterOfficeHours> loadAllOfficeHours() {
        try (ObjectInputStream ois = new ObjectInputStream(
                new FileInputStream(DATA_FILE))) {
            return (List<SemesterOfficeHours>) ois.readObject();
        } catch (FileNotFoundException e) {
            return new ArrayList<>();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    private String convertDaysToString(boolean[] daysSelected) {
        String[] dayNames = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday"};
        StringBuilder days = new StringBuilder();
        
        for (int i = 0; i < daysSelected.length; i++) {
            if (daysSelected[i]) {
                if (days.length() > 0) {
                    days.append(", ");
                }
                days.append(dayNames[i]);
            }
        }
        
        return days.toString();
    }
} 