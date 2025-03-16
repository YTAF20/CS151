package s25.cs151.application;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

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

            // Get the current window (Stage) from any UI element (using welcomeText)
            Stage stage = (Stage) welcomeText.getScene().getWindow();
            
            // Create a new scene with the loaded FXML content and set dimensions
            Scene scene = new Scene(root, 400, 350);
            
            // Update the window with the new scene
            stage.setScene(scene);
        } catch (IOException e) {
            // Print stack trace if there's an error loading the FXML file
            e.printStackTrace();
        }
    }
}
