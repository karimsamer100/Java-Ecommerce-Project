package javafxapplication3; // Ensure correct package name

import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Node;

public class MainPageController { // Ensure the class name is "MainPageController"
    private void loadScene(ActionEvent event, String fxmlFileName, String title) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFileName));
        Parent root = loader.load();

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.setTitle(title);
        stage.show();
    }

        @FXML
        private void handleLoginClick(ActionEvent event) throws Exception {
            loadScene(event, "LoginChoices.fxml", "Login Choices");
        }

        @FXML
        private void handleRegisterClick(ActionEvent event) throws Exception {
            loadScene(event, "RegisterChoices.fxml", "Registration Choices");
        }

        @FXML
        private void handleViewproductClick(ActionEvent event) throws Exception {
            loadScene(event, "Viewproducts.fxml", "Available Products");
        }

        @FXML
        private void handleADDproductrocartClick(ActionEvent event) throws Exception {
            if (Database.getLoggedInCustomer() == null) {
                // Redirect to login if no customer is logged in
                FXMLLoader loader = new FXMLLoader(getClass().getResource("customerlogin.fxml"));
                Parent root = loader.load();
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.setTitle("Customer Login");
                stage.show();
                System.out.println("You must log in as a customer to access this feature.");
            } else {
                // Proceed to Add to Cart if logged in
                loadScene(event, "Addtocart.fxml", "Add to Cart");
            }
        }
    @FXML
    private void handleViewCartClick(ActionEvent event) throws Exception {
        if (Database.getLoggedInCustomer() == null) {
            // Redirect to login if no customer is logged in
            FXMLLoader loader = new FXMLLoader(getClass().getResource("customerlogin.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Customer Login");
            stage.show();
            System.out.println("You must log in as a customer to access this feature.");
        } else {
            // Proceed to Add to Cart if logged in
            loadScene(event, "viewcart.fxml", "View Cart");
        }
    }
    @FXML
    private void handlePlaceOrderClick(ActionEvent event) throws Exception {
        if (Database.getLoggedInCustomer() == null) {
            // Redirect to login if no customer is logged in
            FXMLLoader loader = new FXMLLoader(getClass().getResource("customerlogin.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Customer Login");
            stage.show();
            System.out.println("You must log in as a customer to access this feature.");
        } else {
            // Proceed to Add to Cart if logged in
            loadScene(event, "placeorder.fxml", "Order");
        }
    }

    @FXML
    private void handleAdminActionClick(ActionEvent event) throws Exception {
        Admin loggedInAdmin = Database.getLoggedInAdmin();
        if (loggedInAdmin == null) {
            // Redirect to login if no admin is logged in
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Adminlogin.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Admin Login");
            stage.show();
            System.out.println("You must log in as an admin to access this feature.");
        } else {
            // Proceed to Admin Actions if logged in
            loadScene(event, "adminactions.fxml", "Admin Actions");
        }
    }
}


