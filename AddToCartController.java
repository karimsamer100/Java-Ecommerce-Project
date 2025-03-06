package javafxapplication3;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class AddToCartController {

    @FXML
    private TableView<Product> productsTable;

    @FXML
    private TableColumn<Product, String> nameColumn;

    @FXML
    private TableColumn<Product, Double> priceColumn;

    @FXML
    private TableColumn<Product, Integer> stockColumn;

    @FXML
    private TableColumn<Product, String> categoryColumn;

    @FXML
    private TextField productNameField;

    @FXML
    private Text notificationText;

    @FXML
    private Button addToCartButton;

    private Cart cart;

    @FXML
    public void initialize() {
        // Initialize the cart
        cart = Database.getCurrentCart();
        if (cart == null) {
            cart = new Cart();
            Database.setCurrentCart(cart);
        }

        // Set up table columns
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        stockColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        categoryColumn.setCellValueFactory(cellData ->
                javafx.beans.binding.Bindings.createStringBinding(
                        () -> cellData.getValue().getCategory().getName()
                )
        );

        // Load products into table
        loadProducts();

        // Add selection listener to table
        productsTable.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                productNameField.setText(newVal.getName());
                addToCartButton.setDisable(newVal.getStock() <= 0);
            }
        });

        // Initially disable add to cart button
        addToCartButton.setDisable(true);
    }

    private void loadProducts() {
        ObservableList<Product> products = FXCollections.observableArrayList(Database.products);
        productsTable.setItems(products);
    }

    @FXML
    private void handleAddToCart(ActionEvent event) {
        Product selectedProduct = productsTable.getSelectionModel().getSelectedItem();

        if (selectedProduct == null) {
            showNotification("Please select a product!", "WARNING");
            return;
        }

        if (selectedProduct.getStock() <= 0) {
            showNotification("Product is out of stock!", "ERROR");
            return;
        }

        // Add to cart
        cart.addProduct(selectedProduct);
        selectedProduct.setStock(selectedProduct.getStock() - 1);

        // Update the database
        Database.setCurrentCart(cart);

        // Refresh the table to show updated stock
        loadProducts();

        // Update button state
        addToCartButton.setDisable(selectedProduct.getStock() <= 0);

        showNotification(selectedProduct.getName() + " added to cart successfully!", "SUCCESS");
    }

    private void showNotification(String message, String type) {
        notificationText.setText(message);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(type.equals("ERROR") ? "Error" : type.equals("SUCCESS") ? "Success" : "Warning");
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    private void handleViewCart(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("viewcart.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) productNameField.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("View Cart");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            showNotification("Error opening cart view!", "ERROR");
        }
    }

    @FXML
    private void handleConfirmButtonClick(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("mainpage.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) productNameField.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Main Page");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            showNotification("Error returning to main page!", "ERROR");
        }
    }
}