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
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableCell;
import javafx.stage.Modality;

public class EditOfficeHoursScheduleController {
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
    private TextField searchField;
    @FXML
    private Button searchButton;
    @FXML
    private Button clearSearchButton;
    @FXML
    private Button closeButton;
    @FXML
    private Button editButton;

    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
    private ObservableList<ScheduleEntry> allEntries;

    @FXML
    private void initialize() {
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
        loadSortedEntries();
    }

    private void loadSortedEntries() {
        List<ScheduleEntry> entries = DataManager.loadScheduleEntries();
        entries.sort(java.util.Comparator
                .comparing(ScheduleEntry::getScheduleDate)
                .thenComparing(entry -> {
                    String timeSlot = entry.getTimeSlot();
                    return timeSlot != null ? timeSlot.split(" - ")[0] : "";
                })
        );
        allEntries = FXCollections.observableArrayList(entries);
        entriesTable.setItems(allEntries);
    }

    @FXML
    private void onSearch() {
        String searchText = searchField.getText().trim().toLowerCase();
        if (searchText.isEmpty()) {
            entriesTable.setItems(allEntries);
            return;
        }
        ObservableList<ScheduleEntry> filteredEntries = allEntries.filtered(entry ->
                entry.getStudentName().toLowerCase().contains(searchText)
        );
        entriesTable.setItems(filteredEntries);
    }

    @FXML
    private void onClearSearch() {
        searchField.clear();
        entriesTable.setItems(allEntries);
    }

    @FXML
    private void onEdit() {
        ScheduleEntry selectedEntry = entriesTable.getSelectionModel().getSelectedItem();
        if (selectedEntry == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No Selection");
            alert.setHeaderText(null);
            alert.setContentText("Please select an entry to edit.");
            alert.showAndWait();
            return;
        }
        openEditDialog(selectedEntry);
    }

    private void openEditDialog(ScheduleEntry entry) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("view-schedule-entry.fxml"));
            Parent root = loader.load();
            ScheduleEntryController controller = loader.getController();
            controller.setEditingEntry(entry, null); // No parent refresh needed
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Edit Schedule Entry");
            stage.setScene(new Scene(root, 500, 700));
            stage.showAndWait();
            // After dialog closes, always reload from disk to reflect changes
            loadSortedEntries();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void onClose() {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }
}