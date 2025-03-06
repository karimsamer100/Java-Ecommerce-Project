package javafxapplication3;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

public class ViewCartController {

    @FXML
    private TableView<Product> cartTable;

    @FXML
    private TableColumn<Product, String> productNameColumn;

    @FXML
    private TableColumn<Product, Double> productPriceColumn;

    @FXML
    private TableColumn<Product, String> productDescriptionColumn;

    @FXML
    private Label totalPriceLabel;

    @FXML
    private Button removeButton;

    @FXML
    private Button clearButton;

    @FXML
    private Button checkoutButton;

    private Cart currentCart;

    @FXML
    public void initialize() {
        // Initialize the cart
        currentCart = Database.getCurrentCart();

        // Set up table columns
        productNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        productPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        productDescriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));

        // Load cart data into the table
        loadCartData();

        // Display total price
        updateTotalPrice();

        // Disable buttons if cart is empty
        updateButtonStates();

        // Add listener for selection changes
        cartTable.getSelectionModel().selectedItemProperty().addListener(
                (obs, oldSelection, newSelection) -> removeButton.setDisable(newSelection == null)
        );
    }

    private void loadCartData() {
        ObservableList<Product> cartProducts = FXCollections.observableArrayList(currentCart.getProducts());
        cartTable.setItems(cartProducts);
        updateButtonStates();
    }

    private void updateTotalPrice() {
        totalPriceLabel.setText(String.format("Total: $%.2f", currentCart.calculateTotal()));
    }

    private void updateButtonStates() {
        boolean isEmpty = currentCart.getProducts().isEmpty();
        clearButton.setDisable(isEmpty);
        checkoutButton.setDisable(isEmpty);
        removeButton.setDisable(cartTable.getSelectionModel().getSelectedItem() == null);
    }

    @FXML
    private void handleRemoveItem(ActionEvent event) {
        Product selectedProduct = cartTable.getSelectionModel().getSelectedItem();
        if (selectedProduct != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Remove Item");
            alert.setHeaderText("Remove " + selectedProduct.getName() + " from cart?");
            alert.setContentText("Are you sure you want to remove this item?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                currentCart.removeProduct(selectedProduct);
                selectedProduct.setStock(selectedProduct.getStock() + 1); // Return item to stock
                loadCartData();
                updateTotalPrice();
            }
        }
    }

    @FXML
    private void handleClearCart(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Clear Cart");
        alert.setHeaderText("Clear all items from cart?");
        alert.setContentText("Are you sure you want to clear your cart?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            // Return all items to stock
            for (Product product : currentCart.getProducts()) {
                product.setStock(product.getStock() + 1);
            }
            currentCart.clearCart();
            loadCartData();
            updateTotalPrice();
        }
    }

    @FXML
    private void handleCheckout(ActionEvent event) {
        if (Database.getLoggedInCustomer() == null) {
            showAlert(Alert.AlertType.WARNING, "Login Required",
                    "Please login to proceed with checkout",
                    "You must be logged in to complete your purchase.");
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Checkout");
        alert.setHeaderText("Proceed to Checkout");
        alert.setContentText("Total Amount: $" + String.format("%.2f", currentCart.getTotalAmount()) +
                "\nWould you like to proceed with the purchase?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            // Create new order
            String orderDescription = "Order placed by " + Database.getLoggedInCustomer().getUsername() +
                    " for a total of $" + String.format("%.2f", currentCart.getTotalAmount());
            Order newOrder = new Order(orderDescription);
            Database.orders.add(newOrder);

            // Clear cart after successful order
            currentCart.clearCart();
            loadCartData();
            updateTotalPrice();

            showAlert(Alert.AlertType.INFORMATION, "Order Successful",
                    "Your order has been placed successfully!",
                    "Thank you for your purchase.");
        }
    }

    private void showAlert(Alert.AlertType type, String title, String header, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    @FXML
    private void handleBackToMainPage(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("mainpage.fxml"));
            Scene scene = new Scene(loader.load());
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("E-Commerce Application");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Navigation Error",
                    "Error returning to main page",
                    "An error occurred while trying to return to the main page.");
        }
    }
}