package s25.cs151.application;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.stage.Modality;

import java.io.IOException;

// Controller class that handles the home view functionality
public class MainController {
    // FXML injection for the welcome text label in the UI
    @FXML
    private Label welcomeText;

    // Event handler method for when the "Define Semester's Office Hours" button is clicked
    @FXML
    protected void onButtonClick() {
        try {
            // Create a new FXML loader and load the office hours definition view
            FXMLLoader loader = new FXMLLoader(getClass().getResource("define-semester-office-hr-view.fxml"));
            Parent root = loader.load();

            // Create a new stage (window) for the form
            Stage formStage = new Stage();
            formStage.initModality(Modality.APPLICATION_MODAL); // Make it modal
            formStage.setTitle("Define Semester's Office Hours");
            formStage.setScene(new Scene(root, 400, 350));
            formStage.show();
        } catch (IOException e) {
            // Print stack trace if there's an error loading the FXML file
            e.printStackTrace();
        }
    }

    @FXML
    protected void onViewButtonClick() {
        try {
            // Load the view office hours FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("view-office-hours.fxml"));
            Parent root = loader.load();

            // Create new stage for the view
            Stage viewStage = new Stage();
            viewStage.initModality(Modality.APPLICATION_MODAL); // Make it modal
            viewStage.setTitle("View Office Hours");
            viewStage.setScene(new Scene(root, 600, 400));
            viewStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
