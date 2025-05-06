package s25.cs151.application.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import java.io.IOException;
import java.util.List;
import s25.cs151.application.model.*;

public class DefineCourseController {
    @FXML
    private TextField courseCodeField;

    @FXML
    private TextField courseNameField;

    @FXML
    private TextField sectionNumberField;

    @FXML
    private Button saveButton;

    @FXML
    private Button cancelButton;

    @FXML
    private void onSave() {
        // Validate inputs
        if (courseCodeField.getText().trim().isEmpty() ||
                courseNameField.getText().trim().isEmpty() ||
                sectionNumberField.getText().trim().isEmpty()) {
            showAlert("Please fill in all fields");
            return;
        }

        String courseCode = courseCodeField.getText().trim();
        String courseName = courseNameField.getText().trim();
        String sectionNumber = sectionNumberField.getText().trim();

        // Check for duplicates
        List<Course> existingCourses = DataManager.loadAllCourses();
        for (Course existingCourse : existingCourses) {
            if (existingCourse.getCourseCode().equalsIgnoreCase(courseCode) &&
                    existingCourse.getCourseName().equalsIgnoreCase(courseName) &&
                    existingCourse.getSectionNumber().equalsIgnoreCase(sectionNumber)) {
                showAlert("Error: This course already exists!");
                return;
            }
        }

        // Create and save new course
        Course course = new Course(courseCode, courseName, sectionNumber);
        DataManager.saveCourse(course);
        showAlert("Course saved successfully!");

        // Close the window
        Stage stage = (Stage) saveButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void onCancel() {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void onViewCourses() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("view-courses.fxml"));
            Parent root = loader.load();

            Stage viewStage = new Stage();
            viewStage.initModality(Modality.APPLICATION_MODAL);
            viewStage.setTitle("View Courses");
            viewStage.setScene(new Scene(root, 600, 400));
            viewStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}