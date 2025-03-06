package javafxapplication3;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class AdminRegistrationController {

    @FXML
    private TextField adusernameField;
    @FXML
    private TextField adpasswordField;
    @FXML
    private TextField dateOfBirthField;
    @FXML
    private TextField adRoleField;
    @FXML
    private TextField workinghoursField;

    @FXML
    private void handleRegister(ActionEvent event) {
        try {
            // Collect and validate inputs
            String username = adusernameField.getText();
            String password = adpasswordField.getText();
            String dateOfBirth = dateOfBirthField.getText();
            String role = adRoleField.getText();
            int workingHours = Integer.parseInt(workinghoursField.getText());

            // Create an Admin object
            Admin admin = new Admin(username, password, dateOfBirth, role, workingHours);

            // Add the admin to the database
            Database.admins.add(admin);

            // After successful registration, load main page
            navigateToMainPage();

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // Method to navigate to the main page
    private void navigateToMainPage() {
        try {
            // Load the mainpage.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("mainpage.fxml"));
            Scene scene = new Scene(loader.load());
            Stage stage = (Stage) adusernameField.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            System.out.println("Error loading main page: " + e.getMessage());
        }
    }
}
