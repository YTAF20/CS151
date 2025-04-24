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

    @FXML
    private Button deleteButton;

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

    @FXML
    private void onDelete() {
        ScheduleEntry selectedEntry = entriesTable.getSelectionModel().getSelectedItem();
        
        if (selectedEntry == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No Selection");
            alert.setHeaderText(null);
            alert.setContentText("Please select an entry to delete.");
            alert.showAndWait();
            return;
        }

        Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmAlert.setTitle("Delete Schedule Entry");
        confirmAlert.setHeaderText("Are you sure?");
        confirmAlert.setContentText("This will permanently delete the selected schedule entry.");

        confirmAlert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                try {
                    // Get all entries
                    List<ScheduleEntry> entries = DataManager.loadScheduleEntries();
                    
                    // Remove the selected entry
                    entries.remove(selectedEntry);
                    
                    // Save the updated list
                    DataManager.saveScheduleEntries(entries);
                    
                    // Show success message
                    Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                    successAlert.setTitle("Success");
                    successAlert.setHeaderText(null);
                    successAlert.setContentText("Schedule entry has been deleted successfully.");
                    successAlert.show();
                    
                    // Refresh the table
                    loadSortedEntries();
                } catch (Exception e) {
                    Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                    errorAlert.setTitle("Error");
                    errorAlert.setHeaderText(null);
                    errorAlert.setContentText("Failed to delete entry: " + e.getMessage());
                    errorAlert.show();
                }
            }
        });
    }
} 