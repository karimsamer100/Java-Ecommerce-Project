package javafxapplication3;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class DeleteProductController implements Initializable {

    @FXML
    private ComboBox<String> productComboBox;

    @FXML
    private TextArea productDetailsArea;

    @FXML
    private Button deleteButton;

    @FXML
    private Button backButton;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Populate the combo box with product names
        for (Product product : Database.products) {
            productComboBox.getItems().add(product.getName());
        }

        // Add listener to show product details when selected
        productComboBox.setOnAction(event -> {
            String selectedProduct = productComboBox.getValue();
            updateProductDetails(selectedProduct);
        });
    }

    private void updateProductDetails(String productName) {
        if (productName == null) {
            productDetailsArea.clear();
            return;
        }

        // Find the selected product
        Product product = Database.products.stream()
                .filter(p -> p.getName().equals(productName))
                .findFirst()
                .orElse(null);

        if (product != null) {
            String details = String.format("""
                Name: %s
                Price: $%.2f
                Description: %s
                Stock: %d
                Category: %s""",
                    product.getName(),
                    product.getPrice(),
                    product.getDescription(),
                    product.getStock(),
                    product.getCategory().getName());

            productDetailsArea.setText(details);
        }
    }

    @FXML
    private void handleDelete(ActionEvent event) {
        String selectedProduct = productComboBox.getValue();
        if (selectedProduct == null) {
            showAlert(Alert.AlertType.WARNING, "Please select a product to delete.");
            return;
        }

        // Confirm deletion
        Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmAlert.setTitle("Confirm Deletion");
        confirmAlert.setHeaderText("Delete Product");
        confirmAlert.setContentText("Are you sure you want to delete " + selectedProduct + "?");

        Optional<ButtonType> result = confirmAlert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            // Remove the product
            Database.products.removeIf(p -> p.getName().equals(selectedProduct));

            showAlert(Alert.AlertType.INFORMATION, "Product deleted successfully!");

            // Refresh the combo box
            productComboBox.getItems().clear();
            for (Product product : Database.products) {
                productComboBox.getItems().add(product.getName());
            }

            productDetailsArea.clear();
            productComboBox.setValue(null);
        }
    }

    @FXML
    private void handleBack(ActionEvent event) {
        try {
            Parent adminView = FXMLLoader.load(getClass().getResource("adminactions.fxml"));
            Scene adminScene = new Scene(adminView);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(adminScene);
            window.show();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error returning to admin panel.");
        }
    }

    private void showAlert(Alert.AlertType type, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(type.toString());
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}