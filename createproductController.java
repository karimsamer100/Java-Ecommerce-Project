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
import java.util.ResourceBundle;

public class createproductController implements Initializable {
    @FXML private TextField nameField;
    @FXML private TextField priceField;
    @FXML private TextArea descriptionField;
    @FXML private TextField stockField;
    @FXML private ComboBox<String> categoryComboBox;
    @FXML private Button createButton;
    @FXML private Button backButton;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Populate category combo box with existing categories
        for (Category category : Database.categories) {
            categoryComboBox.getItems().add(category.getName());
        }
    }

    @FXML
    private void handleCreate(ActionEvent event) {
        try {
            // Input validation
            if (nameField.getText().isEmpty() || priceField.getText().isEmpty() ||
                    descriptionField.getText().isEmpty() || stockField.getText().isEmpty() ||
                    categoryComboBox.getValue() == null) {
                showAlert("Error", "All fields are required!");
                return;
            }

            // Parse and validate numeric inputs
            double price;
            int stock;
            try {
                price = Double.parseDouble(priceField.getText());
                stock = Integer.parseInt(stockField.getText());
                if (price < 0 || stock < 0) {
                    showAlert("Error", "Price and stock must be positive numbers!");
                    return;
                }
            } catch (NumberFormatException e) {
                showAlert("Error", "Invalid price or stock value!");
                return;
            }

            // Find selected category
            Category selectedCategory = null;
            for (Category cat : Database.categories) {
                if (cat.getName().equals(categoryComboBox.getValue())) {
                    selectedCategory = cat;
                    break;
                }
            }

            if (selectedCategory == null) {
                showAlert("Error", "Selected category not found!");
                return;
            }

            // Create and add the new product
            Product newProduct = new Product(
                    nameField.getText(),
                    price,
                    descriptionField.getText(),
                    stock,
                    selectedCategory
            );

            Database.products.add(newProduct);
            showAlert("Success", "Product created successfully!");

            // Return to admin actions
            handleBack(event);

        } catch (Exception e) {
            showAlert("Error", "An error occurred while creating the product!");
            e.printStackTrace();
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
        }
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}