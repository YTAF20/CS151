package s25.cs151.application;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.beans.property.SimpleStringProperty;
import java.util.Comparator;

public class ViewTimeslotsController {
    @FXML
    private TableView<Timeslot> timeslotsTable;

    @FXML
    private TableColumn<Timeslot, String> startTimeColumn;

    @FXML
    private TableColumn<Timeslot, String> endTimeColumn;

    @FXML
    private Button closeButton;

    @FXML
    private void initialize() {
        // Configure table columns
        startTimeColumn.setCellValueFactory(data ->
                new SimpleStringProperty(data.getValue().getFormattedStartTime()));
        endTimeColumn.setCellValueFactory(data ->
                new SimpleStringProperty(data.getValue().getFormattedEndTime()));

        // Load and display sorted data
        loadTimeslots();
    }

    private void loadTimeslots() {
        ObservableList<Timeslot> tableData = FXCollections.observableArrayList(
                DataManager.loadAllTimeslots()
        );

        // Sort the timeslots
        tableData.sort((t1, t2) -> {
            // Convert times to comparable values (in minutes since midnight)
            int time1 = convertToMinutes(t1.getStartHour(), t1.getStartMinute(), t1.getStartAmPm());
            int time2 = convertToMinutes(t2.getStartHour(), t2.getStartMinute(), t2.getStartAmPm());
            return Integer.compare(time1, time2);
        });

        timeslotsTable.setItems(tableData);
    }

    private int convertToMinutes(String hour, String minute, String amPm) {
        int h = Integer.parseInt(hour);
        int m = Integer.parseInt(minute);

        // Convert to 24-hour format
        if (amPm.equals("PM") && h != 12) {
            h += 12;
        } else if (amPm.equals("AM") && h == 12) {
            h = 0;
        }

        return h * 60 + m;
    }

    @FXML
    private void onClear() {
        Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmAlert.setTitle("Clear All Timeslots");
        confirmAlert.setHeaderText("Are you sure?");
        confirmAlert.setContentText("This will permanently delete all saved timeslots.");

        confirmAlert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                // Clear the data
                DataManager.clearAllTimeslots();

                // Show success message
                Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                successAlert.setTitle("Success");
                successAlert.setHeaderText(null);
                successAlert.setContentText("All timeslots have been cleared.");
                successAlert.show();

                // Refresh the table
                loadTimeslots();
            }
        });
    }

    @FXML
    private void onClose() {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }
}