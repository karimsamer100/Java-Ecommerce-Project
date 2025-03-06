
package javafxapplication3;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class LoginChoicesController {
   
    @FXML
    private void handleCustomerLogin(ActionEvent event) throws Exception {
        // Load Customer Login UI
        FXMLLoader loader = new FXMLLoader(getClass().getResource("customerlogin.fxml"));
        Parent root = loader.load();

        // Switch scene
        Stage stage = (Stage) ((javafx.scene.Node) (event.getSource())).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.setTitle("Customer Login");
    }

    @FXML
    private void handleAdminLogin(ActionEvent event) throws Exception {
        // Load Admin Login UI
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Adminlogin.fxml"));
        Parent root = loader.load();

        // Switch scene
        Stage stage = (Stage) ((javafx.scene.Node) (event.getSource())).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.setTitle("Admin Login");
    }
}
    

