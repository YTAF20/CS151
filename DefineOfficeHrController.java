package s25.cs151.application.controller;

// Import necessary JavaFX libraries for UI components
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import s25.cs151.application.model.DataManager;
import s25.cs151.application.model.SemesterOfficeHours;

import java.io.IOException;

// Controller class for defining office hours interface
public class DefineOfficeHrController {

    // UI Components annotated with @FXML to link with the FXML file
    @FXML
    private ComboBox<String> semesterDropdown;    // Dropdown menu for selecting semester

    @FXML
    private TextField yearField;    // Text field for entering the year

    @FXML
    private CheckBox mondayCheck, tuesdayCheck, wednesdayCheck, thursdayCheck, fridayCheck;    // Checkboxes for selecting days of the week

    @FXML
    private Button saveButton, cancelButton;    // Buttons for saving or canceling the form

    // Initialize method called automatically when the FXML loads
    @FXML
    private void initialize() {
        // Populate the semester dropdown with options
        semesterDropdown.getItems().addAll("Spring", "Summer", "Fall", "Winter");
        semesterDropdown.setValue("Spring"); // Set default semester to Spring
    }

    // Handler for save button click
    @FXML
    private void onSave() {
        // Get selected semester and year from form
        String semester = semesterDropdown.getValue();
        String year = yearField.getText();

        // Validate input
        if (year == null || year.trim().isEmpty()) {
            showAlert("Please enter a year");
            return;
        }

        // Create array of boolean values representing selected days
        boolean[] daysSelected = {
                mondayCheck.isSelected(),
                tuesdayCheck.isSelected(),
                wednesdayCheck.isSelected(),
                thursdayCheck.isSelected(),
                fridayCheck.isSelected()
        };

        // Create new SemesterOfficeHours object
        SemesterOfficeHours officeHours = new SemesterOfficeHours(semester, year, daysSelected);

        // Save the data
        DataManager.saveOfficeHours(officeHours);

        // Show success message
        showAlert("Office hours saved successfully!");

        // Close the window
        Stage stage = (Stage) saveButton.getScene().getWindow();
        stage.close();
    }

    private void showAlert(String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Handler for cancel button click
    @FXML
    private void onCancel() {
        // Get the current window and close it
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void onViewButtonClick() {
        try {
            // Load the view office hours FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/s25/cs151/application/view/view-office-hours.fxml"));
            Parent root = loader.load();

            // Create new stage for the view
            Stage viewStage = new Stage();
            viewStage.initModality(Modality.APPLICATION_MODAL); // Make it modal
            viewStage.setTitle("View Office Hours");
            viewStage.setScene(new Scene(root, 600, 400));
            viewStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
