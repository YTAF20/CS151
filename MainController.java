package s25.cs151.application;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class MainController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onButtonClick() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("define-semester-office-hr-view.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) welcomeText.getScene().getWindow();
            Scene scene = new Scene(root, 400, 350);
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
