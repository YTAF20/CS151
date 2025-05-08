package s25.cs151.application;

// Import statements for required JavaFX classes
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

// Main class that serves as the entry point for the JavaFX application
// Extends Application to use JavaFX functionality
public class Main extends Application {
    // Required override of the start method from Application class
    // This method is called when the application is launched
    @Override
    public void start(Stage stage) throws Exception {
        // Create a new FXMLLoader to load the UI layout from the FXML file
        // getResource finds the FXML file in the same package as this class
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/s25/cs151/application/view/home-view.fxml"));

        // Create a new Scene with the loaded FXML content
        // Sets the initial window size to 400x350 pixels
        Scene scene = new Scene(fxmlLoader.load(), 400, 350);

        // Configure the primary stage (window)
        stage.setTitle("Define Semester's Office Hours");  // Set window title
        stage.setScene(scene);                            // Set the scene content
        stage.show();                                     // Display the window
    }

    // Main method - entry point of the application
    // Calls launch() to start the JavaFX application lifecycle
    public static void main(String[] args) {
        launch(args);
    }
}
