package javafxapplication3;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;
import javafx.scene.text.Font;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.control.ScrollPane;
import javafx.stage.Stage;

import java.io.IOException;

public class ViewUsersController {

    @FXML
    private VBox customersVBox;

    @FXML
    private VBox adminsVBox;

    @FXML
    public void initialize() {
        // Display Customers
        for (Customer customer : Database.customers) {
            HBox customerBox = createCustomerBox(customer);
            customersVBox.getChildren().add(customerBox);
        }

        // Display Admins
        for (Admin admin : Database.admins) {
            HBox adminBox = createAdminBox(admin);
            adminsVBox.getChildren().add(adminBox);
        }
    }

    private HBox createCustomerBox(Customer customer) {
        HBox box = new HBox(10); // 10 pixels spacing
        box.setStyle("-fx-padding: 10; -fx-background-color: #ffffff22; -fx-background-radius: 5;");

        VBox detailsBox = new VBox(5);
        Label usernameLabel = new Label("Username: " + customer.getUsername());
        Label addressLabel = new Label("Address: " + customer.getAddress());
        Label genderLabel = new Label("Gender: " + customer.getGender());
        Label interestsLabel = new Label("Interests: " + String.join(", ", customer.getInterests()));

        usernameLabel.setFont(new Font("Arial", 14));

        detailsBox.getChildren().addAll(usernameLabel, addressLabel, genderLabel, interestsLabel);
        box.getChildren().add(detailsBox);

        return box;
    }

    private HBox createAdminBox(Admin admin) {
        HBox box = new HBox(10);
        box.setStyle("-fx-padding: 10; -fx-background-color: #ffffff22; -fx-background-radius: 5;");

        VBox detailsBox = new VBox(5);
        Label usernameLabel = new Label("Username: " + admin.getUsername());
        Label roleLabel = new Label("Role: " + admin.getRole());
            Label hoursLabel = new Label("Working Hours: " + admin.getWorkingHours());

        usernameLabel.setFont(new Font("Arial", 14));

        detailsBox.getChildren().addAll(usernameLabel, roleLabel, hoursLabel);
        box.getChildren().add(detailsBox);

        return box;
    }
    @FXML
    private void gotomain(javafx.event.ActionEvent event) {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("mainpage.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }

}