package s25.cs151.application;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class DefineOfficeHrController {

    @FXML
    private ComboBox<String> semesterDropdown;

    @FXML
    private TextField yearField;

    @FXML
    private CheckBox mondayCheck, tuesdayCheck, wednesdayCheck, thursdayCheck, fridayCheck;

    @FXML
    private Button saveButton, cancelButton;

    @FXML
    private void initialize() {
        semesterDropdown.getItems().addAll("Spring", "Summer", "Fall", "Winter");
        semesterDropdown.setValue("Spring"); // Default value
    }

    @FXML
    private void onSave() {
        String semester = semesterDropdown.getValue();
        String year = yearField.getText();
        boolean[] daysSelected = {
                mondayCheck.isSelected(),
                tuesdayCheck.isSelected(),
                wednesdayCheck.isSelected(),
                thursdayCheck.isSelected(),
                fridayCheck.isSelected()
        };

        System.out.println("Semester: " + semester);
        System.out.println("Year: " + year);
        System.out.println("Office Days: " +
                (daysSelected[0] ? "Monday " : "") +
                (daysSelected[1] ? "Tuesday " : "") +
                (daysSelected[2] ? "Wednesday " : "") +
                (daysSelected[3] ? "Thursday " : "") +
                (daysSelected[4] ? "Friday " : "")
        );
    }

    @FXML
    private void onCancel() {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close(); // Close the window
    }
}
