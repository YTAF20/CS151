package s25.cs151.application;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import java.io.IOException;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Modality;

public class DefineTimeslotController {
    @FXML
    private ComboBox<String> startHourCombo;
    @FXML
    private ComboBox<String> startMinuteCombo;
    @FXML
    private ComboBox<String> startAmPmCombo;
    @FXML
    private ComboBox<String> endHourCombo;
    @FXML
    private ComboBox<String> endMinuteCombo;
    @FXML
    private ComboBox<String> endAmPmCombo;
    @FXML
    private Button saveButton;
    @FXML
    private Button cancelButton;

    @FXML
    private void initialize() {
        // Initialize hours (1-12)
        for (int i = 1; i <= 12; i++) {
            String hour = String.format("%d", i);
            startHourCombo.getItems().add(hour);
            endHourCombo.getItems().add(hour);
        }

        // Initialize minutes (00-55, increment by 5)
        for (int i = 0; i < 60; i += 5) {
            String minute = String.format("%02d", i);
            startMinuteCombo.getItems().add(minute);
            endMinuteCombo.getItems().add(minute);
        }

        // Initialize AM/PM
        startAmPmCombo.getItems().addAll("AM", "PM");
        endAmPmCombo.getItems().addAll("AM", "PM");
    }

    @FXML
    private void onSave() {
        // Validate inputs
        if (startHourCombo.getValue() == null || startMinuteCombo.getValue() == null || 
            startAmPmCombo.getValue() == null || endHourCombo.getValue() == null || 
            endMinuteCombo.getValue() == null || endAmPmCombo.getValue() == null) {
            showAlert("Please fill in all time fields");
            return;
        }

        // Create and save new timeslot
        Timeslot timeslot = new Timeslot(
            startHourCombo.getValue(),
            startMinuteCombo.getValue(),
            startAmPmCombo.getValue(),
            endHourCombo.getValue(),
            endMinuteCombo.getValue(),
            endAmPmCombo.getValue()
        );
        
        DataManager.saveTimeslot(timeslot);
        showAlert("Timeslot saved successfully!");
        
        Stage stage = (Stage) saveButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void onCancel() {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void onViewTimeslots() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("view-timeslots.fxml"));
            Parent root = loader.load();

            Stage viewStage = new Stage();
            viewStage.initModality(Modality.APPLICATION_MODAL);
            viewStage.setTitle("View Timeslots");
            viewStage.setScene(new Scene(root, 400, 300));
            viewStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showAlert(String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
} 