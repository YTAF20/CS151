import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Define Semester's Office Hours");

        // GridPane Layout
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(10);
        grid.setHgap(10);

        // Semester Dropdown
        Label semesterLabel = new Label("Semester:");
        GridPane.setConstraints(semesterLabel, 0, 0);
        ComboBox<String> semesterDropdown = new ComboBox<>();
        semesterDropdown.getItems().addAll("Spring", "Summer", "Fall", "Winter");
        semesterDropdown.setValue("Spring"); // Default value
        GridPane.setConstraints(semesterDropdown, 1, 0);

        // Year Input
        Label yearLabel = new Label("Year:");
        GridPane.setConstraints(yearLabel, 0, 1);
        TextField yearField = new TextField();
        yearField.setPromptText("e.g., 2025");
        GridPane.setConstraints(yearField, 1, 1);

        // Days of the Week Checkboxes
        Label daysLabel = new Label("Office Days:");
        GridPane.setConstraints(daysLabel, 0, 2);
        CheckBox monday = new CheckBox("Monday");
        CheckBox tuesday = new CheckBox("Tuesday");
        CheckBox wednesday = new CheckBox("Wednesday");
        CheckBox thursday = new CheckBox("Thursday");
        CheckBox friday = new CheckBox("Friday");
        GridPane.setConstraints(monday, 1, 2);
        GridPane.setConstraints(tuesday, 1, 3);
        GridPane.setConstraints(wednesday, 1, 4);
        GridPane.setConstraints(thursday, 1, 5);
        GridPane.setConstraints(friday, 1, 6);

        // Save Button
        Button saveButton = new Button("Save");
        GridPane.setConstraints(saveButton, 0, 7);
        saveButton.setOnAction(e -> {
            // Placeholder action
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information");
            alert.setHeaderText(null);
            alert.setContentText("Office hours defined successfully (Not saved in this assignment)");
            alert.showAndWait();
        });

        // Cancel Button
        Button cancelButton = new Button("Cancel");
        GridPane.setConstraints(cancelButton, 1, 7);
        cancelButton.setOnAction(e -> primaryStage.setScene(getHomePageScene(primaryStage)));

        // Add elements to grid
        grid.getChildren().addAll(
                semesterLabel, semesterDropdown, yearLabel, yearField, daysLabel,
                monday, tuesday, wednesday, thursday, friday, saveButton, cancelButton
        );

        // Scene setup
        Scene scene = new Scene(grid, 400, 350);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Home page stub
    private Scene getHomePageScene(Stage primaryStage) {
        Button defineOfficeHoursButton = new Button("Define Office Hours");
        defineOfficeHoursButton.setOnAction(e -> start(primaryStage));
        return new Scene(defineOfficeHoursButton, 300, 200);
    }

    public static void main(String[] args) {
        launch(args);
    }
}