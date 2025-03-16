package s25.cs151.application;

// Import necessary JavaFX libraries for UI components
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

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
        
        // Create array of boolean values representing selected days
        boolean[] daysSelected = {
                mondayCheck.isSelected(),
                tuesdayCheck.isSelected(),
                wednesdayCheck.isSelected(),
                thursdayCheck.isSelected(),
                fridayCheck.isSelected()
        };

        // Print the selected values to console (for debugging/demonstration)
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

    // Handler for cancel button click
    @FXML
    private void onCancel() {
        // Get the current window and close it
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close(); 
    }
}
