package s25.cs151.application;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.cell.PropertyValueFactory;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;

public class ViewScheduleEntriesController {
    @FXML
    private TableView<ScheduleEntry> entriesTable;
    
    @FXML
    private TableColumn<ScheduleEntry, String> studentNameColumn;
    
    @FXML
    private TableColumn<ScheduleEntry, String> dateColumn;
    
    @FXML
    private TableColumn<ScheduleEntry, String> timeSlotColumn;
    
    @FXML
    private TableColumn<ScheduleEntry, String> courseColumn;
    
    @FXML
    private TableColumn<ScheduleEntry, String> reasonColumn;
    
    @FXML
    private TableColumn<ScheduleEntry, String> commentColumn;

    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");

    @FXML
    private void initialize() {
        // Configure table columns
        studentNameColumn.setCellValueFactory(new PropertyValueFactory<>("studentName"));
        dateColumn.setCellValueFactory(cellData -> 
            cellData.getValue().getScheduleDate() != null ? 
            javafx.beans.binding.Bindings.createStringBinding(
                () -> cellData.getValue().getScheduleDate().format(dateFormatter)
            ) : null
        );
        timeSlotColumn.setCellValueFactory(new PropertyValueFactory<>("timeSlot"));
        courseColumn.setCellValueFactory(new PropertyValueFactory<>("course"));
        reasonColumn.setCellValueFactory(new PropertyValueFactory<>("reason"));
        commentColumn.setCellValueFactory(new PropertyValueFactory<>("comment"));

        // Load and display sorted data
        loadSortedEntries();
    }

    private void loadSortedEntries() {
        // Get all entries
        List<ScheduleEntry> entries = DataManager.loadScheduleEntries();
        
        // Sort entries by date and then by timeslot
        entries.sort(Comparator
            .comparing(ScheduleEntry::getScheduleDate) // First sort by date
            .thenComparing(entry -> { // Then sort by timeslot
                String timeSlot = entry.getTimeSlot();
                // Extract start time for comparison
                return timeSlot.split(" - ")[0];
            })
        );
        
        // Update table with sorted entries
        ObservableList<ScheduleEntry> sortedEntries = FXCollections.observableArrayList(entries);
        entriesTable.setItems(sortedEntries);
    }

    @FXML
    private void onClear() {
        Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmAlert.setTitle("Clear All Schedule Entries");
        confirmAlert.setHeaderText("Are you sure?");
        confirmAlert.setContentText("This will permanently delete all saved schedule entries.");

        confirmAlert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                // Clear the data
                DataManager.clearAllScheduleEntries();
                
                // Show success message
                Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                successAlert.setTitle("Success");
                successAlert.setHeaderText(null);
                successAlert.setContentText("All schedule entries have been cleared.");
                successAlert.show();
                
                // Refresh the table
                loadSortedEntries();
            }
        });
    }

    @FXML
    private void onClose() {
        Stage stage = (Stage) entriesTable.getScene().getWindow();
        stage.close();
    }
} 