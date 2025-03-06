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

public class UpdateProductController implements Initializable {
    @FXML private ComboBox<String> productComboBox;
    @FXML private TextField nameField;
    @FXML private TextField priceField;
    @FXML private TextArea descriptionField;
    @FXML private TextField stockField;
    @FXML private ComboBox<String> categoryComboBox;
    @FXML private Button updateButton;
    @FXML private Button backButton;

    private Product selectedProduct;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Populate product combo box
        for (Product product : Database.products) {
            productComboBox.getItems().add(product.getName());
        }

        // Populate category combo box
        for (Category category : Database.categories) {
            categoryComboBox.getItems().add(category.getName());
        }

        // Disable fields until a product is selected
        setFieldsEnabled(false);
    }

    @FXML
    private void handleProductSelection(ActionEvent event) {
        String selectedName = productComboBox.getValue();
        if (selectedName != null) {
            // Find the selected product
            for (Product product : Database.products) {
                if (product.getName().equals(selectedName)) {
                    selectedProduct = product;
                    populateFields(product);
                    setFieldsEnabled(true);
                    break;
                }
            }
        }
    }

    private void populateFields(Product product) {
        nameField.setText(product.getName());
        priceField.setText(String.valueOf(product.getPrice()));
        descriptionField.setText(product.getDescription());
        stockField.setText(String.valueOf(product.getStock()));
        categoryComboBox.setValue(product.getCategory().getName());
    }

    private void setFieldsEnabled(boolean enabled) {
        nameField.setDisable(!enabled);
        priceField.setDisable(!enabled);
        descriptionField.setDisable(!enabled);
        stockField.setDisable(!enabled);
        categoryComboBox.setDisable(!enabled);
        updateButton.setDisable(!enabled);
    }

    @FXML
    private void handleUpdate(ActionEvent event) {
        if (selectedProduct == null) {
            showAlert("Error", "Please select a product to update!");
            return;
        }

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

            // Update the product
            selectedProduct.setName(nameField.getText());
            selectedProduct.setPrice(price);
            selectedProduct.setDescription(descriptionField.getText());
            selectedProduct.setStock(stock);
            selectedProduct.setCategory(selectedCategory);

            showAlert("Success", "Product updated successfully!");

            // Return to admin actions
            handleBack(event);

        } catch (Exception e) {
            showAlert("Error", "An error occurred while updating the product!");
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