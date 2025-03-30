package s25.cs151.application;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.cell.PropertyValueFactory;

public class ViewCoursesController {
    @FXML
    private TableView<Course> coursesTable;
    
    @FXML
    private TableColumn<Course, String> codeColumn;
    
    @FXML
    private TableColumn<Course, String> nameColumn;
    
    @FXML
    private TableColumn<Course, String> sectionColumn;
    
    @FXML
    private Button closeButton;

    @FXML
    private void initialize() {
        // Configure table columns
        codeColumn.setCellValueFactory(new PropertyValueFactory<>("courseCode"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("courseName"));
        sectionColumn.setCellValueFactory(new PropertyValueFactory<>("sectionNumber"));

        // Load and display data
        loadCourses();
    }

    private void loadCourses() {
        ObservableList<Course> tableData = FXCollections.observableArrayList(
            DataManager.loadAllCourses()
        );
        
        // Sort by course code (descending) and then by section number (descending)
        tableData.sort((c1, c2) -> {
            // First compare course codes in descending order
            int codeCompare = c2.getCourseCode().compareTo(c1.getCourseCode());
            if (codeCompare != 0) return codeCompare;
            
            // If course codes are equal, compare section numbers in descending order
            return c2.getSectionNumber().compareTo(c1.getSectionNumber());
        });
        
        coursesTable.setItems(tableData);
    }

    @FXML
    private void onClose() {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }
} 