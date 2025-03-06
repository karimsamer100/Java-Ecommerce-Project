package javafxapplication3;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Initialize dummy data (if needed)
        Database.initializeDummyData();

        // Load the initial scene (login or main)
        FXMLLoader loader = new FXMLLoader(getClass().getResource("mainpage.fxml"));
        Scene scene = new Scene(loader.load());

        // Set up the primary stage
        primaryStage.setScene(scene);
        primaryStage.setTitle("E-Commerce Application");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
