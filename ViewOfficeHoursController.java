package s25.cs151.application;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.cell.PropertyValueFactory;
import java.util.*;

public class ViewOfficeHoursController {
    @FXML
    private TableView<OfficeHourRow> officeHoursTable;
    
    @FXML
    private TableColumn<OfficeHourRow, String> semesterColumn;
    
    @FXML
    private TableColumn<OfficeHourRow, String> yearColumn;
    
    @FXML
    private TableColumn<OfficeHourRow, String> daysColumn;
    
    @FXML
    private Button closeButton;

    // Define semester order
    private static final Map<String, Integer> SEMESTER_ORDER = new HashMap<>() {{
        put("Spring", 0);
        put("Summer", 1);
        put("Fall", 2);
        put("Winter", 3);
    }};

    @FXML
    private void initialize() {
        // Configure table columns
        semesterColumn.setCellValueFactory(new PropertyValueFactory<>("semester"));
        yearColumn.setCellValueFactory(new PropertyValueFactory<>("year"));
        daysColumn.setCellValueFactory(new PropertyValueFactory<>("days"));

        // Load and display sorted data
        loadOfficeHours();
    }

    private void loadOfficeHours() {
        ObservableList<OfficeHourRow> tableData = FXCollections.observableArrayList();
        
        // Load saved office hours
        List<SemesterOfficeHours> savedHours = DataManager.loadAllOfficeHours();
        
        // Convert each SemesterOfficeHours to a display row
        for (SemesterOfficeHours hour : savedHours) {
            tableData.add(new OfficeHourRow(hour));
        }

        // Sort the data
        tableData.sort((row1, row2) -> {
            // First, compare by year (descending)
            int yearCompare = row2.getYear().compareTo(row1.getYear());
            if (yearCompare != 0) return yearCompare;

            // Then, compare by semester
            return Integer.compare(
                SEMESTER_ORDER.get(row1.getSemester()),
                SEMESTER_ORDER.get(row2.getSemester())
            );
        });
        
        // Set the sorted data to the table
        officeHoursTable.setItems(tableData);
    }

    @FXML
    private void onClose() {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }

    // Helper class for table display
    public static class OfficeHourRow {
        private final String semester;
        private final String year;
        private final String days;

        public OfficeHourRow(SemesterOfficeHours hours) {
            this.semester = hours.getSemester();
            this.year = hours.getYear();
            this.days = convertDaysToString(hours.getDaysSelected());
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

        // Getters required for TableView
        public String getSemester() { return semester; }
        public String getYear() { return year; }
        public String getDays() { return days; }
    }
} 