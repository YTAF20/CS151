package s25.cs151.application.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.time.LocalDate;
import java.util.List;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import s25.cs151.application.model.DataManager;
import s25.cs151.application.model.OfficeHoursSchedule;
import s25.cs151.application.model.ScheduleEntry;
import s25.cs151.application.model.Timeslot;
import s25.cs151.application.model.*;

public class ScheduleEntryController {
    @FXML
    private TextField studentNameField;

    @FXML
    private DatePicker scheduleDatePicker;

    @FXML
    private ComboBox<String> timeSlotComboBox;

    @FXML
    private ComboBox<String> courseComboBox;

    @FXML
    private TextField reasonField;

    @FXML
    private TextArea commentField;

    @FXML
    private Button saveButton;

    @FXML
    private Button cancelButton;

    private ScheduleEntry editingEntry = null;
    private ViewScheduleEntriesController parentController = null;
    // Store original values for matching
    private String originalStudentName = null;
    private LocalDate originalScheduleDate = null;
    private String originalTimeSlot = null;

    @FXML
    private void initialize() {
        // Set today's date as default for date picker
        scheduleDatePicker.setValue(LocalDate.now());

        // Load and populate timeslots
        List<Timeslot> timeslots = DataManager.loadAllTimeslots();
        ObservableList<String> timeSlotStrings = FXCollections.observableArrayList();
        for (Timeslot slot : timeslots) {
            timeSlotStrings.add(slot.getFormattedStartTime() + " - " + slot.getFormattedEndTime());
        }
        timeSlotComboBox.setItems(timeSlotStrings);

        // Load and populate courses
        List<Course> courses = DataManager.loadAllCourses();
        ObservableList<String> courseStrings = FXCollections.observableArrayList();
        for (Course course : courses) {
            courseStrings.add(course.getCourseCode() + " - " + course.getCourseName() + " (Section " + course.getSectionNumber() + ")");
        }
        courseComboBox.setItems(courseStrings);

        // Set prompt text for optional fields
        reasonField.setPromptText("Enter reason (optional)");
        commentField.setPromptText("Enter comment (optional)");
    }

    @FXML
    private void onSave() {
        if (validateInput()) {
            if (editingEntry != null) {
                // Update the existing entry
                editingEntry.setStudentName(studentNameField.getText().trim());
                editingEntry.setScheduleDate(scheduleDatePicker.getValue());
                editingEntry.setTimeSlot(timeSlotComboBox.getValue());
                editingEntry.setCourse(courseComboBox.getValue());
                editingEntry.setReason(reasonField.getText().trim());
                editingEntry.setComment(commentField.getText().trim());
                saveEditedEntry(editingEntry);
                if (parentController != null) parentController.refreshTable();
            } else {
                // Create schedule entry object
                ScheduleEntry entry = new OfficeHoursSchedule(
                        studentNameField.getText().trim(),
                        scheduleDatePicker.getValue(),
                        timeSlotComboBox.getValue(),
                        courseComboBox.getValue(),
                        reasonField.getText().trim(),
                        commentField.getText().trim()
                );
                saveEntry(entry);
            }
            // Show success message
            showSuccessAlert();
            // Close the window
            closeWindow();
        }
    }

    private boolean validateInput() {
        StringBuilder errorMessage = new StringBuilder();

        // Check required fields
        if (studentNameField.getText().trim().isEmpty()) {
            errorMessage.append("Student name is required.\n");
        }

        if (scheduleDatePicker.getValue() == null) {
            errorMessage.append("Schedule date is required.\n");
        }

        if (timeSlotComboBox.getValue() == null) {
            errorMessage.append("Time slot selection is required.\n");
        }

        if (courseComboBox.getValue() == null) {
            errorMessage.append("Course selection is required.\n");
        }

        if (errorMessage.length() > 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Required Fields");
            alert.setHeaderText("Please fill in all required fields");
            alert.setContentText(errorMessage.toString());
            alert.showAndWait();
            return false;
        }

        return true;
    }

    private void saveEntry(ScheduleEntry entry) {
        try {
            // Get existing entries
            List<ScheduleEntry> entries = DataManager.loadScheduleEntries();

            // Add new entry
            entries.add(entry);

            // Save updated list
            DataManager.saveScheduleEntries(entries);
        } catch (Exception e) {
            showErrorAlert("Failed to save entry: " + e.getMessage());
        }
    }

    private void saveEditedEntry(ScheduleEntry entry) {
        try {
            List<ScheduleEntry> entries = DataManager.loadScheduleEntries();
            for (int i = 0; i < entries.size(); i++) {
                ScheduleEntry e = entries.get(i);
                // Match by original unique fields
                if (e.getStudentName().equals(originalStudentName)
                        && e.getScheduleDate().equals(originalScheduleDate)
                        && e.getTimeSlot().equals(originalTimeSlot)) {
                    entries.set(i, entry);
                    break;
                }
            }
            DataManager.saveScheduleEntries(entries);
        } catch (Exception e) {
            showErrorAlert("Failed to save edited entry: " + e.getMessage());
        }
    }

    private void showSuccessAlert() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText(null);
        alert.setContentText("Schedule entry has been saved successfully.");
        alert.showAndWait();
    }

    private void showErrorAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    private void onCancel() {
        closeWindow();
    }

    private void closeWindow() {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void onViewEntries() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/s25/cs151/application/view/view-schedule-entries.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("View Schedule Entries");
            stage.setScene(new Scene(root, 800, 600));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setEditingEntry(ScheduleEntry entry, ViewScheduleEntriesController parent) {
        this.editingEntry = entry;
        this.parentController = parent;
        // Store original values for matching
        this.originalStudentName = entry.getStudentName();
        this.originalScheduleDate = entry.getScheduleDate();
        this.originalTimeSlot = entry.getTimeSlot();
        // Populate fields with entry data
        studentNameField.setText(entry.getStudentName());
        scheduleDatePicker.setValue(entry.getScheduleDate());
        timeSlotComboBox.setValue(entry.getTimeSlot());
        courseComboBox.setValue(entry.getCourse());
        reasonField.setText(entry.getReason());
        commentField.setText(entry.getComment());
    }
}