package javafxapplication3;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class customerloginController {

    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;

    @FXML
    private void handleCustomerLogin(ActionEvent event) {
        String username = usernameField.getText();
        String password = passwordField.getText();

        Customer customer = authenticateCustomer(username, password);

        if (customer != null) {
            Database.setLoggedInCustomer(customer); // Set the logged-in customer
            // Navigate to MainPage.fxml
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("mainpage.fxml"));
                Parent root = loader.load();
                Stage stage = (Stage) usernameField.getScene().getWindow();
                stage.setScene(new Scene(root));
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Invalid credentials");
        }
    }


    private Customer authenticateCustomer(String username, String password) {
        // Replace this with actual logic to authenticate the customer
        for (Customer customer : Database.customers) {
            if (customer.getUsername().equals(username) && customer.getPassword().equals(password)) {
                return customer;
            }
        }
        return null;
    }
}
