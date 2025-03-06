package javafxapplication3;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.control.Label;

public class AdminloginController {

    @FXML private TextField adusernameField;
    @FXML private PasswordField adpasswordField;
    @FXML private Label errorLabel;

    @FXML
    private void handleAdminLogin(ActionEvent event) {
        String username = adusernameField.getText();
        String password = adpasswordField.getText();

        // Check credentials using the Database.login method
        Object user = Database.login(username, password);

        if (user instanceof Admin) {
            Admin admin = (Admin) user;
            // Set the logged in admin in Database
            Database.setLoggedInAdmin(admin);

            try {
                // Navigate to adminactions.fxml if credentials are correct
                FXMLLoader loader = new FXMLLoader(getClass().getResource("adminactions.fxml"));
                Parent root = loader.load();
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.setTitle("Admin Actions");
                stage.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Invalid credentials");
            if (errorLabel != null) {
                errorLabel.setText("Invalid admin credentials");
                errorLabel.setVisible(true);
            }
        }
    }

    @FXML
    private void handleBackClick(ActionEvent event) {
        try {
            Parent mainView = FXMLLoader.load(getClass().getResource("mainpage.fxml"));
            Scene mainScene = new Scene(mainView);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(mainScene);
            window.setTitle("Main Page");
            window.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // You can keep this method for additional validation if needed
    private Admin authenticateAdmin(String username, String password) {
        for (Admin admin : Database.admins) {
            if (admin.getUsername().equals(username) && admin.getPassword().equals(password)) {
                return admin;
            }
        }
        return null;
    }
}