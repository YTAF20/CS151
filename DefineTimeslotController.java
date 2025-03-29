package s25.cs151.application;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

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
        // TODO: Implement save functionality
        Stage stage = (Stage) saveButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void onCancel() {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }
} 