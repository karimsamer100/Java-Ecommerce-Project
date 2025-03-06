package javafxapplication3;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class customerregisterController {

    @FXML
    private TextField usernameField;
    @FXML
    private TextField passwordField;
    @FXML
    private TextField dateOfBirthField;
    @FXML
    private TextField storeCreditField;
    @FXML
    private TextField addressField;
    @FXML
    private TextField interestsField;
    @FXML
    private TextField genderField;

    @FXML
    private void handleRegister(ActionEvent event) {
        try {
            // Collect and validate inputs
            String username = usernameField.getText();
            String password = passwordField.getText();
            String dateOfBirth = dateOfBirthField.getText();
            double storeCredit = Double.parseDouble(storeCreditField.getText());
            String address = addressField.getText();
            Gender gender = Gender.valueOf(genderField.getText().toUpperCase());
            List<String> interests = Arrays.asList(interestsField.getText().split(","));

            // Create a Customer object
            Customer customer = new Customer(username, password, dateOfBirth, storeCredit, address, gender, interests);

            // Add the customer to the database
            Database.customers.add(customer);

            // After successful registration, load main page
            navigateToMainPage();

        } catch (IllegalArgumentException e) {
            System.out.println("Error: Invalid gender input. Please enter 'MALE' or 'FEMALE'.");
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
            Stage stage = (Stage) usernameField.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            System.out.println("Error loading main page: " + e.getMessage());
        }
    }
}
